package com.itzy.spiderhc;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: ZY
 * @Date: 2019/7/27 15:03
 * @Version 1.0
 */
public class HttpClientGet {
    public static void main(String[] args) throws IOException {
        // 1.拿到一个httpclient的对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2.设置请求方式和请求信息
        HttpGet httpGet = new HttpGet("https://news.163.com/");
        // 3.执行请求
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            // 4.获取返回值
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String html = EntityUtils.toString(response1.getEntity(), Charset.forName("utf-8"));
            // 5.打印
            System.out.println(html);
        } finally {
            response1.close();
        }
    }
}
