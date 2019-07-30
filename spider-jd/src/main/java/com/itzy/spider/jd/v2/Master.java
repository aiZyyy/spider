package cn.itcast.spider.jd.v2;

import java.io.IOException;
import java.nio.charset.Charset;

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

import redis.clients.jedis.Jedis;

/**
 * 获取首页和分页的信息，并解析出很多很多的商品详情页url 将这个url，存放到外部存储中（redis list）
 *
 */
public class Master {

    private static Jedis jedis = new Jedis("node01",6379);

    public static void main(String[] args) throws Exception {
        parserIndex(); //由于解析首页，只有一次
        dopaging(); //只管分页，不管爬取

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
//					arrayBlockingQueue.put(li.attr("data-pid"));
                    //变化：将data-pid的值存放到外部存储中去  redis
                    jedis.lpush("bigdata:spider:jd:urls", li.attr("data-pid"));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getHtml(String indexUrl) throws IOException, ClientProtocolException {
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

}
