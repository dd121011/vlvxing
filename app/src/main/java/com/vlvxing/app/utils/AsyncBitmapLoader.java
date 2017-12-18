package com.vlvxing.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by mingchao on 2017/12/18.
 */

public class AsyncBitmapLoader {
    private static String TAG="AsyncBitmapLoader";
    private HashMap<String, SoftReference<Bitmap>> imageCache = null;//用Map集合缓存软引用的Bitmap对象
    public AsyncBitmapLoader(){
        imageCache= new HashMap<>();
    }

    public  Bitmap loadBitMap(final ImageView imageView, final String imageUrl, final ImageCallBack imageCallback){
        //如果在内存缓存中，则返回Bitmap对象
        if(imageCache.containsKey(imageUrl)){
            SoftReference<Bitmap> sr=imageCache.get(imageUrl);////软引用的Bitmap对象
            Bitmap bitmap=sr.get();///取出Bitmap对象，如果由于内存不足Bitmap被回收，将取得空

            if (bitmap!=null){
                System.out.println("节日启动页  bitmap  内存缓存中 bitmap!=null");
                return bitmap;
            }
        }else {//本地缓存的查找
            String bitmapName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            File file = new File(Contant.IMAGEURL);
            if (file != null) {
                    if (bitmapName.equals(file.getName())) {
                        System.out.println("节日启动页  bitmap  本地缓存 bitmap!=null");
                       return BitmapFactory.decodeFile(Contant.IMAGEURL + bitmapName);
                     }
            }
        }

        final Handler handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                imageCallback.imageLoad(imageView, (Bitmap) msg.obj);
            }
        };


        /**
         * 如果上面的软内存缓存和本地缓存都不存在，那么就要开启线程进行下载咯
         */
        new  Thread(){
            @Override
            public void run() {
                super.run();
                InputStream is = getStreamFromURL(imageUrl);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageCache.put(imageUrl, new SoftReference<>(bitmap));
                Message msg = handler.obtainMessage(0, bitmap);
                handler.sendMessage(msg);//向Handler发送消息，让Handler进行处理任务

                File dir = new File(Contant.IMAGEURL);
                if(!dir.exists())
                {
                    System.out.println("节日启动页   下载线程   dir 不存在 创建文件:Contant.IMAGEURL:"+Contant.IMAGEURL);
                    dir.mkdirs();
                }
                File bitmapFile = new File(Contant.IMAGEURL+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
                if(!bitmapFile.exists())
                {
                    try
                    {
                        System.out.println("节日启动页  下载线程   bitmapFile 不存在 创建文件:Contant.IMAGEURL:"+Contant.IMAGEURL);
                        bitmapFile.createNewFile();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                FileOutputStream os;
                try
                {
                    os = new FileOutputStream(bitmapFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG,
                            100, os);//100 meaning compress for max quality
                    System.out.println("节日启动页  下载线程   bitmapFile 写入图片");
                    os.close();
                    System.out.println("节日启动页  下载线程  bitmapFile 写入图片完成");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
        return null;
    }

    public static InputStream getStreamFromURL(String imageURL){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet();

        if(TextUtils.isEmpty(imageURL)){
            Log.i(TAG, "节日启动页 getStreamFromURL imageUrl访问的地址为空");
            return null;
        }
        System.out.println("节日启动页   getStreamFromURL imageURL:"+imageURL);
        get.setURI(URI.create(imageURL));
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inputStream = response.getEntity().getContent();
                System.out.println("节日启动页   getStreamFromURL inputStream:"+inputStream);
                return inputStream;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ImageCallBack
    {
        public void imageLoad(ImageView imageView, Bitmap bitmap);
    }


}
