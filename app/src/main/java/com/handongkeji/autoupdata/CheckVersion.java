package com.handongkeji.autoupdata;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;


import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * app更新
 * Created by sangbo on 16-5-19.
 */
public class CheckVersion {

    private static final String TAG = "CheckVersion";
    private static int mAppVersionCode = 0;
    private static Context mContext;
    private static boolean mIsEnforceCheck = false;
    public static String checkUrl = Constants.URL_VERSIONUPDATE;
    public static UpdateEntity mUpdateEntity;

    public static void update(Context context) {
        update(context, mIsEnforceCheck);
    }

    public static void update(Context context, final boolean isEnforceCheck) {
        mContext = context;
        mIsEnforceCheck = isEnforceCheck;
        mAppVersionCode = getVersionCode(mContext);

        if (TextUtils.isEmpty(checkUrl)) {
            Log.d(TAG, "url不能为空，请设置url");
            return;
        }

        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, checkUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d(TAG, "onSuccess: " + responseInfo.result);
                loadOnlineData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
//                System.out.println("更新失败，请检查网络"+error.getMessage());
//                System.out.println("更新失败，请检查网络"+msg);
                if (mIsEnforceCheck)
                    Toast.makeText(mContext, "更新失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static void loadOnlineData(String json) {


        try {
            UpdateEntity updateEntity = new UpdateEntity(json);
            if (updateEntity == null) {
                if (mIsEnforceCheck)
                    Toast.makeText(mContext, "网络信息获取失败，请重试", Toast.LENGTH_SHORT).show();
                return;
            }
            mUpdateEntity = updateEntity;

            if (mAppVersionCode < mUpdateEntity.versionCode) {
                //启动更新
                AlertUpdate();
            } else {
                if (mIsEnforceCheck) {
                    Toast.makeText(mContext, "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
//            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("", e.getMessage());
        }

    }


    private static void AlertUpdate() {

        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        window.setLayout((int) (MyApp.getScreenWidth() * 0.8), LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        dialog.setContentView(R.layout.umeng_update_dialog);
        TextView tv = (TextView) dialog.findViewById(R.id.umeng_update_content);
        tv.setText("新版本:" + mUpdateEntity.versionName + "\n"
                + "更新内容\r\n" + mUpdateEntity.updateLog
        );
        dialog.findViewById(R.id.umeng_update_id_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.umeng_update_id_ok).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                updateApp();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public static void updateApp() {

        updateApp(false);

    }

    private static void updateApp(boolean isEnforceDown) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = getPackgeName(mContext) + mUpdateEntity.versionName + ".apk";

//        if(!isEnforceDown){
//            File file = new File(filePath+"/"+fileName);
//            if(file.exists()){
//                install(file);
//                return;
//            }
//        }
        File file = new File(filePath + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }

        Intent intent = new Intent(mContext, UpdataService.class);
        intent.putExtra("downUrl", mUpdateEntity.downUrl);
        intent.putExtra("targetSDPath", filePath + "/" + fileName);
        mContext.startService(intent);

    }


//    public static void install(File file) {
//
////        if(!checkMD5(file)){
////            md5Alert();
////            return;
////        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//
//    }

    //    private static void md5Alert() {
//
//        final Dialog dialog = new Dialog(mContext);
//        Window window = dialog.getWindow();
//        window.setLayout((int) (MyApp.getScreenWidth()*0.8), LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
//        dialog.setContentView(R.layout.dialog_downapk_fail);
//        TextView tv = (TextView) dialog.findViewById(R.id.content);
//        tv.setText("md5不一致，是否重新下载");
//        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//        dialog.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				updateApp(true);
//			}
//		});
//        dialog.show();
//
//    }
    public static void resterAlert() {
        if (mContext == null) {
            return;
        }
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        window.setLayout((int) (MyApp.getScreenWidth() * 0.8), LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        dialog.setContentView(R.layout.dialog_downapk_fail);
        dialog.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateApp();
            }
        });
        dialog.show();
    }

//    private static boolean checkMD5(File file) {
//
//        String md5Value;
//        try {
//            md5Value = getMd5ByFile(file);
//        } catch (FileNotFoundException e) {
//            md5Value = "-1";
//        }
//        Log.d("md5:",md5Value);
//        return md5Value.equals(mUpdateEntity.md5);
//
//
//    }

    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 获得apkPackgeName
     *
     * @param context
     * @return
     */
    public static String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

}
