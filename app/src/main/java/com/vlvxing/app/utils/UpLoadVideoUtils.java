package com.vlvxing.app.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.storage.OnObbStateChangeListener;
import android.util.Log;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.google.gson.Gson;
import com.handongkeji.widget.MyProcessDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 上传视频到阿里云
 * Created by Administrator on 2017/5/23 0023.
 */

public class UpLoadVideoUtils {

    private Context context;
    private VODUploadClient uploader;
    private Handler handler; //用于处理上传的结果

    //点播上传需要访问服务器获取的数据
    private String uploadAuth; //上传凭证
    private String uploadAddress ; //上传地址

    //OSS上传需要访问服务器获取到的数据
    private String accessKeyId = "LTAIOPiP0tr3lTrz";
    private String accessKeySecret = "hKxxx7KhYh8uUz6tUX6CDO8ehGfUUJ";
//    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com"; //服务端设置的回源网站(参考：https://help.aliyun.com/knowledge_detail/39585.html)
//    private String secretToken;
//    private String expireTime;

    //传递进来的
    private String filePath ; //视频文件的本地路径
    private String coverUrl; //视频封面图上传后返回的url

    private long fileSize;
    private MyProcessDialog progressDialog;

//    public String videoID ;

    private String fileName;
    public boolean isSuccess = false;
    public String successUrl ; //上传成功后返回的视频地址

    public UpLoadVideoUtils(Context context, String filePath,String coverUrl,MyProcessDialog progressDialog) {

        this.context = context;
        this.filePath = filePath;
        this.coverUrl = coverUrl;
        this.progressDialog = progressDialog;

        uploader = new VODUploadClientImpl(context.getApplicationContext());

        //获取文件大小
        fileSize = (long) FileSizeUtil.getFileOrFilesSize(filePath, 1);

        //判断文件是否有名称、文件的大小是否为0
        String[] split = filePath.split("/");
        fileName = split[split.length-1];

        if (fileName == null || fileSize == 0)
        {
            ToastUtils.show(context,"文件错误");
            return;
        }

        addFile(); //添加文件
        initCallBack();//设置上传的回调
        startUpLoad();

        //调用接口，获取上传的凭证(使用点播方式上传时调用这个方法)
//        getUpLoadAuth();
    }

//    private void getUpLoadAuth() {
//
//        Log.i("test","getUpLoadAuth");
//
//        String[] split = filePath.split("/");
//        String fileName = split[split.length-1];
//
//        if (fileName == null || fileSize == 0)
//        {
//            ToastUtils.show(context,"文件错误");
//            return;
//        }
//        final HashMap<String, String> params = new HashMap<>();
//        params.put("fileName", fileName);
//        params.put("fileSize", fileSize+"");
//
////        Log.i("test","params："+params.toString());
//
//
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setMsg("正在创建上传凭证");
//        dialog.setCancelable(false);
//        dialog.show();
//
//        RemoteDataHandler.asyncPost(Constants.URL_GET_VIDEO_UPLOAD_AUTH, params, context, false, new RemoteDataHandler.Callback() {
//            @Override
//            public void dataLoaded(ResponseData data) {
//                String json = data.getJson();
//                dialog.dismiss();
//                Log.i("test", "上传凭证的json:" + json);
//                if (json != null) {
//                    Gson gson = new Gson();
//                    UpLoadAuthBean uploadAuthBean = gson.fromJson(json, UpLoadAuthBean.class);
//                    int status = uploadAuthBean.getStatus();
//                    if (status == 1) {
//                        UpLoadAuthBean.DataEntity data1 = uploadAuthBean.getData();
//                        uploadAddress = data1.getAddress();
//                        uploadAuth = data1.getUploadAuth();
//                        videoID = data1.getVideoId();
//
//                        addFile(); //添加文件
//                        initCallBack();//设置上传的回调
//                        startUpLoad();
//                    } else {
//                        ToastUtils.show(context, "服务器错误");
//                    }
//
//                } else {
//                    ToastUtils.show(context, "服务器错误");
//                }
//            }
//        });
//
//    }

    private void addFile() {
//        if (isVodMode()) {
//            uploader.addFile(filePath, getVodInfo()); //文件大小<=4G
//            Log.i("test","添加文件成功");
//        }
//        else {
//        String vodPath = "mp4MultibitrateIn36/"; //上传路径的一部分，有服务器提供
        String vodPath ="videoin/"; //上传路径的一部分，有服务器提供
        //文件名中不能有中文
        char[] chars = fileName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 0x4E00 && chars[i] <= 0x9FA5) {
                fileName.replace(chars[i], 'a'); //如果这个字符串中有汉字，就将汉字替换成a
            }
        }

        String name1 = System.currentTimeMillis() + "android" + fileName;
        String ossName = vodPath + name1; //videoinput/1497491541221androidvideo_20170615_095022.mp4
        //http://vlxingout.oss-cn-hangzhou.aliyuncs.com/videoout/
        successUrl = "http://vlxingout.oss-cn-hangzhou.aliyuncs.com/videoout/" + name1;  //上传成功后的视频地址
        // uploader.addFile(fineName, endpoint, bucket, ossName, getVodInfo());
        uploader.addFile(filePath, endpoint, "vlxingin", ossName, getVodInfo());//文件大小<=4G
        Log.i("test1","添加文件："+filePath);
//    }
    }


    private void startUpLoad()
    {
        uploader.start();
    }

    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle("标题" + "：我的标题");
        vodInfo.setDesc("描述." + ":我的描述");
        vodInfo.setCateId(0); //视频的角标，列表的话就传该视频在列表中的位置，不是列表的话就传0
        vodInfo.setIsProcess(true);
        vodInfo.setCoverUrl(coverUrl); //视频封面图的地址
        List<String> tags = new ArrayList<>();
        tags.add("标签" + 0);
        vodInfo.setTags(tags);
//        if (isVodMode()) {
//            vodInfo.setIsShowWaterMark(false); //是否启用水印
//            vodInfo.setPriority(7);  //定义优先级
//        } else {
            vodInfo.setUserData("我是自定义数据");
//        }
        return vodInfo;
    }

    private void initCallBack() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1)
                {
                    case 1:  //上传成功
                        isSuccess = true;
                        progressDialog.dismiss();
                        ToastUtils.show(context,"上传成功");
                        break;
                    case 2:  //上传失败
                        isSuccess = false;
                        successUrl = null;
                        ToastUtils.show(context,"上传失败");
                        progressDialog.dismiss();
//                        videoID = null;
                        break;
                    case 3:  //上传进度
                        break;
                }
            }
        };

        VODUploadCallback callback = new VODUploadCallback() {
            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                Log.i("test1","info.getFilePath() ------------------" + info.getFilePath());
                Log.i("test1","onsucceed ------------------" + info.getVodInfo().toString());
                Message msg = new Message();
                msg.arg1 = 1;
                handler.sendMessage(msg);
            }

            /**
             * 上传失败
             */
            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                Log.i("test1","onfailed ------------------ " + info.getFilePath() + " " + code + " " + message);
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);
            }

            /**
             * 回调上传进度
             * @param uploadedSize 已上传字节数
             * @param totalSize 总共需要上传字节数
             */
            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
//                Log.i("test1","onProgress ------------------ " + info.getFilePath() + " " + uploadedSize + " " + totalSize);
                Message msg = new Message();
                msg.arg1 = 3;
                handler.sendMessage(msg);
            }

            /**
             * 上传凭证过期后，会回调这个接口
             * 可在这个回调中获取新的上传，然后调用resumeUploadWithAuth继续上传
             */
            @Override
            public void onUploadTokenExpired() {
                Log.i("test1","onExpired -------凭证过期------ ");
//                if (isVodMode()) {
//                    // 实现时，从新获取UploadAuth
//                    uploader.resumeWithAuth(uploadAuth);
////                    uploader.resumeWithToken(uploadAuth);
//                }


//               if (isSTSMode()) {
//                    // 实现时，从新获取STS临时账号用于恢复上传
//                    uploader.resumeWithToken(accessKeyId, accessKeySecret, secretToken, expireTime);
//                }
            }

            /**
             * 上传过程中，状态由正常切换为异常时触发
             */
            @Override
            public void onUploadRetry(String code, String message) {
                Log.i("test1","onUploadRetry ------------- ");
            }

            /**
             * 上传过程中，从异常中恢复时触发
             */
            @Override
            public void onUploadRetryResume() {
                Log.i("test1","onUploadRetryResume ------------- ");
            }

            /**
             * 上传开始回调
             */
            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                Log.i("test1","onUploadStarted ------------- ");

                //设置上传地址和上传凭证
//                if (isVodMode()) {
//                    uploader.setUploadAuthAndAddress(uploadFileInfo, uploadAuth, uploadAddress);
//                }
                Log.i("test1","file path:" + uploadFileInfo.getFilePath() +
                        ", endpoint: " + uploadFileInfo.getEndpoint() +
                        ", bucket:" + uploadFileInfo.getBucket() +
                        ", object:" + uploadFileInfo.getObject() +
                        ", status:" + uploadFileInfo.getStatus());
            }
        };


        //三种上传方式
//        if (isVodMode()) {
//            Log.i("test","isVodMode");
//
//            // 点播上传。每次上传都是独立的鉴权，所以初始化时，不需要设置鉴权
//            uploader.init(callback);
//        }
//        else
//      if (isSTSMode()) {
            // OSS直接上传:STS方式，安全但是较为复杂，建议生产环境下使用。
            // 临时账号过期时，在onUploadTokenExpired事件中，用resumeWithToken更新临时账号，上传会续传。
//            uploader.init(accessKeyId, accessKeySecret, secretToken, expireTime, callback);
            uploader.init(accessKeyId, accessKeySecret, callback);
//        }
// else {
//            // OSS直接上传:AK方式，简单但是不够安全，建议测试环境下使用。
//            uploader.init(accessKeyId, accessKeySecret, callback);
//        }

    }



    private boolean isVodMode() {
        return (null != uploadAuth && uploadAuth.length() > 0);
    }

    private boolean isSTSMode() {
//        if (!isVodMode()) {
//            return (null != secretToken && secretToken.length() > 0 && null != expireTime && expireTime.length() > 0);
//        }
        return false;
    }


}
