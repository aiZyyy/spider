package com.itzy.spiderhc;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @Author: ZY
 * @Date: 2019/7/27 15:12
 * @Version 1.0
 */
public class HttpClientPost {

    public static void main(String[] args) throws IOException {

        String url = "https://news.163.com/";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("username", "************"));
        parameters.add(new BasicNameValuePair("password", "************"));
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String html = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        System.out.println(html);
    }
}
