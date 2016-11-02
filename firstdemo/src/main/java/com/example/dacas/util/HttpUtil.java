package com.example.dacas.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by qingf on 2016/9/20.
 */
public class HttpUtil
{
    // 创建HttpClient对象
    static HttpClient httpClient = new DefaultHttpClient();
 /*   public static final String BASE_URL =
            "http://192.168.191.1:8080/testapp6/login";*/
    /**
     *
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String getRequest(final String url)
            throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        // 创建HttpGet对象。
                        HttpGet get = new HttpGet(url);
                        // 发送GET请求
                        HttpResponse httpResponse = httpClient.execute(get);
                        // 如果服务器成功地返回响应
                        if (httpResponse.getStatusLine()
                                .getStatusCode() == HttpStatus.SC_OK)
                        {
                            // 获取服务器响应字符串
                            String result = EntityUtils
                                    .toString(httpResponse.getEntity(), HTTP.UTF_8);
                            return result;
                        }
                        return null;
                    }
                });
        new Thread(task).start();
        return task.get();
    }

    /**
     * @param url 发送请求的URL
     * @param raw 请求参数
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String postRequest(final String url, final String raw)throws Exception
    {
        FutureTask<String> task = new FutureTask<>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        // 创建HttpPost对象。
                        HttpPost httpRequest = new HttpPost(url);

                        StringEntity sEntity = new StringEntity(raw);
                        // 设置请求参数
                        httpRequest.setEntity(sEntity);
                        HttpClient httpClient = new DefaultHttpClient();
                        // 发送POST请求
                        HttpResponse httpResponse = httpClient.execute(httpRequest);
                        final int ret = httpResponse.getStatusLine().getStatusCode();
                        if(ret == HttpStatus.SC_OK){
                            // 获取服务器响应字符串
                            String responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                            Log.d("PostID",responseStr);
                            return responseStr;
                        }else{
                            return null;
                        }
                    }
                });
        new Thread(task).start();
        return task.get();
    }
}
