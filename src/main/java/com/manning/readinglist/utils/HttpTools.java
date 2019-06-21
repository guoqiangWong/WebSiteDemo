package com.manning.readinglist.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;


/**
 * @author ExpanseWong
 */
public class HttpTools {
    public static String get(String url) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    private static volatile boolean flag = true;

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(2, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(new Runnable() {
            @Override
            public  void run() {
                int count = 0;
                while(count <10){
                    if (flag){
                        System.out.println(flag);
                        count++;
                    }
                    if (count%2==1){
                        flag =false;
                    }
                System.out.println(count+"/br");
                }
            }
        });

        singleThreadPool.execute(new Runnable() {
            @Override
            public  void run() {
                String str = "ABCDEFGHIHKLMNOPQRSTUVWXYZ";
                int count = 0;
               while (count <10){
                   if (!flag){
                       System.out.println(count+"::"+flag);
                       count++;
                   }
                   flag = true;
               }
            }
        });
        singleThreadPool.shutdown();
    }
}
