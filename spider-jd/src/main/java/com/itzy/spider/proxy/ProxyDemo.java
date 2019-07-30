package com.itzy.spider.proxy;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: ZY
 * @Date: 2019/7/30 15:52
 * @Version 1.0
 */
public class ProxyDemo {
    /**
     * 1） 访问一个请求，得到所有的代理ip
     * 2）发起http请求，并制定使用代理ip进行访问
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {
        // 1.制定代理ip地址 淘宝
        // 2.发起请求
        CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(new DefaultProxyRoutePlanner(new HttpHost("13.78.125.167",8080))).build();
        CloseableHttpResponse res = httpClient.execute(new HttpGet("http://item.jd.com/16587067003.html"));
        System.out.println(EntityUtils.toString(res.getEntity(), Charset.forName("utf-8")));

    }
}
