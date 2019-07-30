package com.itzy.spiderJsoup;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: ZY
 * @Date: 2019/7/29 14:13
 * @Version 1.0
 */
public class QiDianSpider {

    public static void main(String[] args) throws IOException {
        String url = "https://read.qidian.com/chapter/_h17RCSkeXScikCo3ZPkrg2/_LrKPdZirvtMs5iq0oQwLQ2";

        String filePath = "d:\\test.txt";
        FileWriter fwriter = null;
        while (true) {
            //发起请求
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //获取结果
            CloseableHttpResponse res = httpClient.execute(httpGet);
            HttpEntity httpEntity = res.getEntity();
            String html = EntityUtils.toString(httpEntity, Charset.forName("utf-8"));
            //解析html
            Document document = Jsoup.parse(html);
            String name = document.select(".j_chapterName").get(0).text();
            System.out.println(name);
            System.out.println("---------------------------------------------------------");
            Elements elements = document.select("div[class=read-content j_readContent] p");
            System.out.println(elements.text());
            StringBuffer sb = new StringBuffer();
            elements.forEach(a -> {
                sb.append(a.text());
                sb.append("\r\n");
            });
            String content = sb.toString();
            try {
                // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
                fwriter = new FileWriter(filePath, true);
                fwriter.write(name);
                fwriter.write("\r\n");
                fwriter.write("------------------------------------------------------");
                fwriter.write("\r\n");
                fwriter.write(content);
                fwriter.write("\r\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fwriter.flush();
                    fwriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //获取下一个章节的url
            url = "https:" + document.select("#j_chapterNext").get(0).attr("href");
        }
    }
}
