package com.vlvxing.app.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.common.ProgressDialog;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 视频播放类
 *
 * @ClassName:VideoPlayActivity
 * @PackageName:com.newtonapple.zhangyiyan.zhangyiyan.activity
 * @Create On 2017/5/23 0023   15:54
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2017/5/23 0023 handongkeji All rights reserved.
 */


public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, OnErrorListener, OnInfoListener,
        OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

    @Bind(R.id.su)
    SurfaceView surfaceView;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.return_lin)
    LinearLayout return_lin;
    @Bind(R.id.activity_video)
    LinearLayout activityVideo;
    private Display currDisplay;
    private SurfaceHolder holder;
    private MediaPlayer player;
    private int vWidth, vHeight;
    private String path;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headTitle.setText("视频");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMsg("正在加载");
        progressDialog.show();

        return_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //给SurfaceView添加CallBack监听
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //下面开始实例化MediaPlayer对象
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);

//        Log.i("test", "Begin:::surfaceDestroyed called");
        //然后指定需要播放文件的路径，初始化MediaPlayer
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        Log.i("test", "videoPlay:path:" + path);

        try {
            player.setDataSource(path);
//            Log.i("test", "Next:::surfaceDestroyed called");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //然后，我们取得当前Display对象
        currDisplay = this.getWindowManager().getDefaultDisplay();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // 当Surface尺寸等参数改变时触发
//        Log.i("test", "Surface Change:::____surfaceChanged called");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

//        Log.i("test", "surfaceCreated");

        // 当SurfaceView中的Surface被创建的时候被调用
        //在这里我们指定MediaPlayer在当前的Surface中进行播放
        player.setDisplay(holder);
        //在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
        player.prepareAsync();
//        Log.i("test", "异步准备");


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.i("test", "Surface Destory:::____surfaceDestroyed called");
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
//        Log.i("test", "Video Size Change____onVideoSizeChanged called");

    }

    @Override
    public void onSeekComplete(MediaPlayer arg0) {
        // seek操作完成时触发
//        Log.i("test", "Seek Completion____onSeekComplete called");

    }

    @Override
    public void onPrepared(MediaPlayer player) {
//        Log.i("test", "准备完成");

        // 当prepare完成后，该方法触发，在这里我们播放视频

        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();

        if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
//            Log.i("test", "宽高超出界限，进行调整");

            //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
            float wRatio = (float) vWidth / (float) currDisplay.getWidth();
            float hRatio = (float) vHeight / (float) currDisplay.getHeight();

            //选择大的一个进行缩放
            float ratio = Math.max(wRatio, hRatio);

            vWidth = (int) Math.ceil((float) vWidth / ratio);
            vHeight = (int) Math.ceil((float) vHeight / ratio);

            //设置surfaceView的布局参数
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));
        }
        progressDialog.dismiss();
        //然后开始播放视频
        player.start();
//        Log.i("test", "开始播放");

    }

    @Override
    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
        // 当一些特定信息出现或者警告时触发
        switch (whatInfo) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                break;
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer player, int whatError, int extra) {
//        Log.v("Play Error:::", "onError called");
        switch (whatError) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
//                Log.i("test", "Play Error:::____MEDIA_ERROR_SERVER_DIED,服务死了");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
//                Log.i("test", "Play Error:::____MEDIA_ERROR_UNKNOWN，未知");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        // 当MediaPlayer播放完成后触发
//        Log.i("test", "Play Over:::____onComletion called,播放完成");
        this.finish();
    }
}
