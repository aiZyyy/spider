package com.itzy.spider.jd.v2;

import com.google.gson.Gson;
import com.itzy.spider.jd.v1.Product;
import com.itzy.spider.jd.v1.ProductDao;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import redis.clients.jedis.Jedis;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZY
 * @Date: 2019/7/30 14:52
 * @Version 1.0
 */
public class Slave {
    private final static ProductDao productDao = new ProductDao();

    public static void main(String[] args) {

        while (true) {
            // 1) 获取外部的url--------Redis list中获取、阻塞的
            // 原生JDK中，queue会有重复消费的问题。 blockqueue.take，避免重复消费。
            Jedis jedis = new Jedis("node01", 6379);
            // final int timeout, final String... keys) 多了一个参数timeout
            // brpop的返回值，有两个第一个参数是 用户请求的key，第二个参数是返回的值
            List<String> result = jedis.brpop(0, "bigdata:spider:jd:urls");
            String pId = result.get(1);
            // 2) 根据url获取数据
            try {
                parserProductDetail(pId);
//				Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("pid处理失败：" + pId);
                System.out.println(e);
                // 再将PID存放到对立中，等待下次爬取。
            }
        }
    }

    private static void parserProductDetail(String pId) throws Exception {
        // 1.指定url
        // https://item.jd.com/3367822.html
        String pUrl = "https://item.jd.com/" + pId + ".html";
        // 2.封装成一个get请求
        HttpGet httpGet = new HttpGet(pUrl);
        // 3.使用httpclient发起请求
        CloseableHttpClient detailHttpClient = HttpClients.createDefault();
        CloseableHttpResponse detailRes = detailHttpClient.execute(httpGet);
        // 4.得到响应结果
        if (200 == detailRes.getStatusLine().getStatusCode()) {
            String detailHtml = EntityUtils.toString(detailRes.getEntity(), Charset.forName("utf-8"));
            // 5.解析文档
            Document detailDoc = Jsoup.parse(detailHtml);
            // 6.一次解析出我们想要的数据
            Product product = getProductInfo(detailDoc);
            product.setId(pId);
            product.setUrl(pUrl);
            // 7. 补全价格信息
            // 1）指定url，2）封装httpget请求 3）发起期请求 4） 得到值
            String priceUrl = "http://p.3.cn/prices/mgets?&type=1&area=1_72_2799_0&pdtk=&pduid=2098855974&pdpin=&pin=null&pdbp=0&ext=11000000&source=item-pc&skuIds=J_" + pId;
            HttpGet priceHttpGet = new HttpGet(priceUrl);
            CloseableHttpClient priceHttpClient = HttpClients.createDefault();
            CloseableHttpResponse priceRes = priceHttpClient.execute(priceHttpGet);
            if (200 == priceRes.getStatusLine().getStatusCode()) {
                String priceJson = EntityUtils.toString(priceRes.getEntity(), Charset.forName("utf-8"));
                System.out.println(priceJson);
                // [{"op":"1999.00","m":"3000.00","id":"J_3367822","p":"1999.00"}]
                // Gson 谷歌提欧专用于解析json，将json串转化成一个对象。
                // 使用必须导入pom依赖。
                Gson gson = new Gson();
                ArrayList<Map> resultList = gson.fromJson(priceJson, ArrayList.class);
                Map<String, String> map = (Map<String, String>) resultList.get(0);
                // 获取价格数据
                String price = map.get("op");
                product.setPrice(price);
            }

            // 将解析出来的数据，保存到数据库。
            productDao.saveProduct(product);
        }

    }

    private static Product getProductInfo(Document detailDoc) {
        /**
         * private String name; private String title; private String price;
         * private String maidian; private String pinpai; private String
         * xinghao;
         */
        Product product = new Product();
        // 获取商品名称
        String name = detailDoc.select("[class=parameter2 p-parameter-list] li").get(0).text();
        product.setName(name);
        // 获取标题
        String title = detailDoc.select(".sku-name").get(0).text();
        product.setTitle(title);
        // // // 获取卖点信息-----------------拿不到卖点信息？？？？？？？
        // String maidian = detailDoc.select("#p-ad").get(0).attr("title");
        // product.setMaidian(maidian);
        // 补全其它信息…………………………………………………………………………………………
        return product;
    }
}
