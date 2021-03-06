package com.itzy.spiderBase;

/**
 * @Author: ZY
 * @Date: 2019/7/27 13:54
 * @Version 1.0
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 使用JDK的api进行get请求
 * <p>
 * 1.在使用httpurlconnection时，默认就是get请求。如何改成post请求？
 * 2.http协议中，可以指定header，想添加user-agent
 * 3.
 * 4.
 */
public class BasicHttpGet {
    public static void main(String[] args) throws IOException {
        //指定一个url
        URL url = new URL("https://www.baidu.com");

        //发起一个请求
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //添加请求放试
        conn.setRequestMethod("GET");

        //添加请求头------如果编写爬虫，真实浏览器发送的header都拷贝
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        conn.setRequestProperty("Accept-Encoding", "utf-8");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cookie", "BAIDUID=87B18D194D3477D5F1DD731CA816F3B7:FG=1; BIDUPSID=0BD67B4CE9328698C9F2E06B18D29214; PSTM=1556515960; BD_UPN=12314753; MCITY=-%3A; BDUSS=FFXdlBhZlh6ZTR4NmpxaG9RbklEbURteTI1QkhzN2swMjFhVVJTWFRtRU52RE5kSVFBQUFBJCQAAAAAAAAAAAEAAACl1ss6xMW6sGFhOTY0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA0vDF0NLwxdV; COOKIE_SESSION=2_0_8_8_5_1_0_0_8_1_0_0_8233_0_0_0_1564110485_0_1564110484%7C9%23172080_50_1563411019%7C8; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BD_HOME=1; H_PS_PSSID=1449_21092_18560_20880_29519_28519_29099_29567_28830_29221_22158");
        conn.setRequestProperty("Host", "www.baidu.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        //3.获取返回值
        InputStream inputStream = conn.getInputStream();
        //3.1 将输入流转换字符串
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //3.2 一次读取bufferReader的数据
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        //4.关闭流
        inputStream.close();
    }

}
