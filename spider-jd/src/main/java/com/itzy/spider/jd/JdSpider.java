package com.itzy.spider.jd;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: ZY
 * @Date: 2019/7/30 10:32
 * @Version 1.0
 */
public class JdSpider {
    public static void main(String[] args) throws Exception {
        // 第一个事情：解析首页的信息，得到商品列表
        parserIndex();
        // 第二个事情：解析分页的信息，得到商品列表
        dopaging();
    }

    private static void dopaging() throws Exception {
        int page = 1;
        while (page <= 100) {
            String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&page="
                    + (2 * page - 1);
            System.out.println(url);
            String pagingResult = getHtml(url);
            getSearchResultInfo(pagingResult);
            page++;

        }
    }

    private static void parserIndex() throws Exception {
        // 1.指定url
        String indexUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&pvid=4462d633d7774a1dafc55419260fae59";
        String indexHtml = getHtml(indexUrl);
        getSearchResultInfo(indexHtml);
    }

    private static void getSearchResultInfo(String indexHtml) {
        if (indexHtml != null) {
            Document indexDoc = Jsoup.parse(indexHtml);
            // 6.定位到商品列表
            Elements liList = indexDoc.select("#J_goodsList li[data-pid]");
            for (Element li : liList) {
                // 7.依次每个商品的详情页，并解析出数据
                try {
                    parserProductDetail(li.attr("data-pid"));
                } catch (Exception e) {
                    System.out.println("商品url访问失败！   " + li.attr("data-pid") + e);
                }
            }
        }
    }

    private static String getHtml(String indexUrl) throws IOException {
        // 2.将url对象封装成httpget对象
        HttpGet indexHttpGet = new HttpGet(indexUrl);
        // 3.使用httpclient发起一个请求
        CloseableHttpClient indexHttpClient = HttpClients.createDefault();
        CloseableHttpResponse indexRes = indexHttpClient.execute(indexHttpGet);
        // 4.从响应结果中，获得首页的html文档
        if (200 == indexRes.getStatusLine().getStatusCode()) {
            // 5.获得首页的信息，从首页中找出商品的列表
            return EntityUtils.toString(indexRes.getEntity(), Charset.forName("utf-8"));
        }
        return null;
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
            String priceUrl = "https://p.3.cn/prices/mgets?skuIds=J_" + pId;
            HttpGet priceHttpGet = new HttpGet(priceUrl);
            CloseableHttpClient priceHttpClient = HttpClients.createDefault();
            CloseableHttpResponse priceRes = priceHttpClient.execute(priceHttpGet);
            if (200 == priceRes.getStatusLine().getStatusCode()) {
                String priceJson = EntityUtils.toString(priceRes.getEntity(), Charset.forName("utf-8"));
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
            System.out.println(product);
        }

    }

    private static Product getProductInfo(Document detailDoc) {
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
