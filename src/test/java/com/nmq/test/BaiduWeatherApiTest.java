package com.nmq.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by niemengquan on 2016/8/24.
 */
public class BaiduWeatherApiTest {
    //根据城市获取天气信息的java代码
    //cityName 是你要取得天气信息的城市的中文名字，如“北京”，“深圳”
    public static String getWeatherInform(String cityName) {

        //百度天气API
        String baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";
        StringBuffer strBuf;

        try {
            //通过浏览器直接访问http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ
            //5slgyqGDENN7Sy7pw29IUvrZ 是我自己申请的一个AK(许可码)，如果访问不了，可以自己去申请一个新的ak
            //百度ak申请地址：http://lbsyun.baidu.com/apiconsole/key
            //要访问的地址URL，通过URLEncoder.encode()函数对于中文进行转码
            baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location=" + URLEncoder.encode(cityName, "utf-8") + "&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        strBuf = new StringBuffer();

        try {
            URL url = new URL(baiduUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strBuf.toString();
    }

    public static void main(String[] args) {
        System.out.println(getWeatherInform("新乡"));
    }
}
