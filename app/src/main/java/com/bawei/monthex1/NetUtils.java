package com.bawei.monthex1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 功能:  页面
 * 作者:  胡锦涛
 * 时间:  2019/11/20 0020 下午 3:04
 */
public class NetUtils {
    //单例
    private static NetUtils netUtils=new NetUtils();
    private NetUtils(){}

    public static NetUtils getInstance() {
        return netUtils;
    }
    //是否有网
    public boolean hasNet(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()) {
            return true;
        }else {
            return false;
        }
    }
    //是否是Wifi
    public boolean isWifi(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()&&activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
            return true;
        }else {
            return false;
        }
    }
    //是否是Mobile
    public boolean isMobile(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()&&activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
            return true;
        }else {
            return false;
        }
    }
    //获取字符串
    @SuppressLint("StaticFieldLeak")
    public void getJson(final String urlPath, final MyCallBack myCallBack){
        new AsyncTask<Void, Void, String>() {

            private String s;
            private InputStream inputStream;

            @Override
            protected void onPostExecute(String value) {
                myCallBack.onGetJson(value);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置连接读取超时
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);
                    //开启连接
                    httpURLConnection.connect();
                    //判断是否请求成功
                    if (httpURLConnection.getResponseCode()==200){
                        //获取输入流
                        inputStream = httpURLConnection.getInputStream();
                        //流转字符串
                        s = io2String(inputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            //关流
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return s;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private String io2String(InputStream inputStream) throws IOException {
        //三件套
        int len=-1;
        byte[]bytes=new byte[1024];
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            b.write(bytes,0,len);
        }
        byte[] bytes1 = b.toByteArray();
        String s = new String(bytes1);
        return s;
    }

    public interface MyCallBack{
        void onGetJson(String json);

    }
    @SuppressLint("StaticFieldLeak")
    public void getBitmap(final String bitPath, final ImageView img){
        new AsyncTask<Void, Void, Bitmap>() {

            private Bitmap s;
            private InputStream inputStream;

            @Override
            protected void onPostExecute(Bitmap value) {
                img.setImageBitmap(value);
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    URL url = new URL(bitPath);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置连接读取超时
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);
                    //开启连接
                    httpURLConnection.connect();
                    //判断是否请求成功
                    if (httpURLConnection.getResponseCode()==200){
                        //获取输入流
                        inputStream = httpURLConnection.getInputStream();
                        //流转图片
                        s = io2Bitmap(inputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            //关流
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return s;
            }
        }.execute();
    }
    private Bitmap io2Bitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }
}
