package com.vlvxing.app.utils;

import android.app.Activity;
import android.util.Log;

import com.handongkeji.utils.StringUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;


/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class UmAddAliasAndTag {

    public static void addUmengAlias(String uid, String tag, final Activity activity) {
        PushAgent mPushAgent = PushAgent.getInstance(activity);
        String alias =  uid + "vlvxing";
        String alias_type = "vlvxing_type";
        mPushAgent.addExclusiveAlias(alias, alias_type, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                if (b){
                    // 设置alias成功
                    Log.d("aaa", "onMessage: addExclusiveAlias "+b);
                }
            }
        });

        if (StringUtils.isStringNull(tag))
            tag="";
        final String finalTag = tag;
        mPushAgent.getTagManager().reset(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean isSuccess, ITagManager.Result result) {
                if (isSuccess){
                    // 设置alias成功
                    Log.d("aaa", "onMessage: reset "+isSuccess);
                    addTag(activity, finalTag);
                }
            }
        });
    }


    private static void addTag(Activity activity,String tag) {
        PushAgent mPushAgent = PushAgent.getInstance(activity);
        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess){
                    // 设置alias成功
                    Log.d("Tag", "onMessage: "+isSuccess);
                }
            }
        }, tag);

    }

    public static void RemoveAlias(String uid, String tag,Activity activity){
        PushAgent mPushAgent = PushAgent.getInstance(activity);
        String alias = uid+"vlvxing";
        String alias_type = "vlvxing_type";
        mPushAgent.removeAlias(alias,alias_type, new UTrack.ICallBack(){
            @Override
            public void onMessage(boolean isSuccess, String message) {
                if (isSuccess){
                    Log.e("isSuccess",message);
                }
            }
        });
    }


}
