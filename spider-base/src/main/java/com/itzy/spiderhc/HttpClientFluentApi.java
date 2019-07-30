package com.itzy.spiderhc;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: ZY
 * @Date: 2019/7/27 15:45
 * @Version 1.0
 */
public class HttpClientFluentApi {

    public static void main(String[] args) throws IOException {
        String html = Request.Get("https://news.163.com/").execute().returnContent().asString(Charset.forName("utf-8"));
        System.out.println(html);

        Request.Post("https://news.163.com/")
                .bodyForm(Form.form().add("username", "vip").add("password", "secret").build()).execute().returnContent();
    }
}
