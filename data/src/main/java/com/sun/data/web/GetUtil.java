package com.sun.data.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

public class GetUtil {
    public static String sendGet(String url) {

        //创建 builder
        HttpClient.Builder builder = HttpClient.newBuilder();

        //链式调用
        HttpClient client = builder

                //http 协议版本 1.1 或者 2
                .version(HttpClient.Version.HTTP_2) //.version(HttpClient.Version.HTTP_1_1)

                //连接超时时间，单位为毫秒
                .connectTimeout(Duration.ofMillis(5000)) //.connectTimeout(Duration.ofMinutes(1))

                //连接完成之后的转发策略
                .followRedirects(HttpClient.Redirect.NEVER) //.followRedirects(HttpClient.Redirect.ALWAYS)

                //指定线程池
                .executor(Executors.newFixedThreadPool(5))

                //认证，默认情况下 Authenticator.getDefault() 是 null 值，会报错
                //.authenticator(Authenticator.getDefault())

                //代理地址
                //.proxy(ProxySelector.of(new InetSocketAddress("http://www.baidu.com", 8080)))

                //缓存，默认情况下 CookieHandler.getDefault() 是 null 值，会报错
                //.cookieHandler(CookieHandler.getDefault())

                //创建完成
                .build();

        HttpRequest.Builder reBuilder = HttpRequest.newBuilder();

        //链式调用
        HttpRequest request = reBuilder
                //存入消息头
                //消息头是保存在一张 TreeMap 里的
                .header("Content-Type", "application/json")

                //http 协议版本
                .version(HttpClient.Version.HTTP_2)

                //url 地址
                // 股票代码要添加小写的交易所代码，如sse600000、szse000001
                // 时间定位点是YYYYMMDDhhmmss表示的，如果取日数据，hhmmss就不重要
                // 采样数量和方向用±整数表示，正数表示从时间定位点开始，沿时间轴取若干组数据，负数表示沿时间轴的反方向取若干组数据
                // 采样频率代码可取5（日K）、0（2分钟）、1（5分钟）、2（15分钟）、3（30分钟）、4（60分钟）、6（周）、9（月）、12（季度）、15（年）（其实可以取0-18，注意没有1分钟）
                .uri(URI.create(url))

                //超时时间
                .timeout(Duration.ofMillis(5009))

                //发起一个 post 消息，需要存入一个消息体
                //                .POST(HttpRequest.BodyPublishers.ofString("hello"))

                //发起一个 get 消息，get 不需要消息体
                .GET()

                //method(...) 方法是 POST(...) 和 GET(...) 方法的底层，效果一样
                //.method("POST",HttpRequest.BodyPublishers.ofString("hello"))

                //创建完成
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
