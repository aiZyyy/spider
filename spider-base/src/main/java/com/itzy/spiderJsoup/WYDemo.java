package com.itzy.spiderJsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * @Author: ZY
 * @Date: 2019/7/27 16:52
 * @Version 1.0
 */
public class WYDemo {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.parse(new URL("https://news.163.com/"), 2000);

        Elements elements = doc.select("div[class=N-nav-channel JS_NTES_LOG_FE] a[href^=http]");

        elements.forEach(element -> {
            System.out.println(element.text());
            System.out.println(element.attr("href"));
            System.out.println("******************************************************************************************");
        });


    }
}
