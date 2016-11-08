package com.example.dacas.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by qingf on 2016/9/20.
 */
public class HttpUtil
{
    // 创建HttpClient对象
    static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL =
            "http://login.servicesecurity.cn";
    private static String cookie_sn = "0";
    /**
     *
     * @param url 发送请求的URL
     * @return 服务器响应图片yue1
     * @throws Exception
     */
    public static Bitmap getRequestBitmap(final String url)
            throws Exception
    {
        FutureTask<Bitmap> task = new FutureTask<Bitmap>(
                new Callable<Bitmap>()
                {
                    @Override
                    public Bitmap call() throws Exception
                    {
                        // 创建HttpGet对象。
                        HttpGet get = new HttpGet(url);
                        // 发送GET请求
                        HttpResponse httpResponse = httpClient.execute(get);
                        Bitmap bitmap;
                        // 如果服务器成功地返回响应
                        if (httpResponse.getStatusLine()
                                .getStatusCode() == HttpStatus.SC_OK)
                        {
                            HttpEntity httpEntity = httpResponse.getEntity();
                            InputStream is = httpEntity.getContent();
                            //Log.d("PostID", String.valueOf(is.available()));
                            // 获取服务器响应的校验码图片
                            bitmap = BitmapFactory.decodeStream(is);
                            is.close();
                            return bitmap;
                        }
                        return null;
                    }
                });
        new Thread(task).start();
        return task.get();
    }
    /**
     *
     * @param url 发送请求的URL
     * @return 服务器响应字符串yue1
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
                        int httpStatus = httpResponse.getStatusLine().getStatusCode();
                        if (httpStatus == HttpStatus.SC_OK)
                        {
                            HttpEntity httpEntity = httpResponse.getEntity();
                            String responseStr = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                            return responseStr;
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

    /**
     *
     */
    private void removeAllCookie() {

    }
}
