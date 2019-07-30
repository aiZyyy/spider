package com.itzy.spiderJsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * @Author: ZY
 * @Date: 2019/7/27 16:43
 * @Version 1.0
 */
public class JsoupDemo {

    public static void main(String[] args) throws IOException {
        // jsoup 访问一个url 得到一个url对象
        Document doc = Jsoup.parse(new URL("https://news.163.com/"), 2000);
        // 获取所有的a 标签
        Elements aTags = doc.select("a");
        // 获取id等于webim的数据
        Elements webims = doc.select("#webim");
        // 通过class获取数据 footer
        Elements footers = doc.select(".footer");
        // 获取具备某个属性的标签
        Elements cls = doc.select("[src]");
        // 获取某个字符开头的属性
        Elements slist = doc.select("[^s]");
        // 获取某个属性值等于什么的
        Elements jslist = doc.select("[src=/js/index.js]");
        // [attr^=value] 开始，[attr$=value] 结束，[attr*=value] 包含

        // 获取div下有个id等于webim的元素
        Elements divs = doc.select("div#webim");
        // 获取div有id属性的元素
        Elements ids = doc.select("div[id]");
        // 获取div有id属性的元素 ,并且有ul标签
        Elements uls = doc.select("div[id] ul");

        // 获取div有id属性的元素 ,并且有ul标签
        Elements hrefs = doc.select("div[id] ul [href]");

        // 获取div有id属性的元素 ,并且有ul标签
        Elements yuns = doc.select("div[id] ul [href^=http://yun]");

        for (Element element : aTags) {
            System.out.println(element);
        }
    }

}
