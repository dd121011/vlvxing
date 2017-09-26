package com.handongkeji.ui;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vlvxing.app.R;


/**
 * ClassName:BrowseActivity.java
 *
 * PackageName:com.handongkeji.Bama.ui
 *
 * Create On 2015-12-8上午10:46:42
 *
 * Site:http://www.handongkeji.com
 *
 * author:wmm
 *
 * Copyrights 2015-10-18 handongkeji All rights reserved.
 */
public class BrowseActivity extends BaseActivity {

	private ProgressDialog progressDialog = null;
	TextView title;
	LinearLayout returnLin; //返回
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		final String bUrl = intent.getStringExtra("url");
		setContentView(R.layout.activity_browse);
		title = (TextView)findViewById(R.id.TextView02);
		title.setText("加载中...");
		returnLin=(LinearLayout)findViewById(R.id.linearLayoutBack);

		returnLin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		progressDialog = ProgressDialog.show(BrowseActivity.this, null, "请稍后...", true);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		final WebView wbShow = (WebView)findViewById(R.id.wbShow);
		wbShow.getSettings().setAllowFileAccess(true);          
		wbShow.getSettings().setJavaScriptEnabled(true);   
		
		wbShow.getSettings().setSupportZoom(true); 
		// 设置出现缩放工具 
		wbShow.getSettings().setBuiltInZoomControls(true);
		//扩大比例的缩放
		wbShow.getSettings().setUseWideViewPort(true);
	    //	自适应屏幕
		wbShow.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wbShow.getSettings().setLoadWithOverviewMode(true);
		wbShow.loadUrl(bUrl);
		wbShow.setWebViewClient(new WebViewClient(){
			@Override                        
			public void onPageFinished(WebView view, String url) {                                
				// TODO Auto-generated method stubs
				super.onPageFinished(view, url);                                
				//页面下载完毕,却不代表页面渲染完毕显示出来                                
				//WebChromeClient中progress==100时也是一样                                
				if (wbShow.getContentHeight() != 0) {                                        
					//这个时候网页才显示                         
					
					}      
				if(progressDialog!=null)
				{
					progressDialog.dismiss();
				}
				}                       
			@Override                        
			public boolean shouldOverrideUrlLoading(WebView view, String url) {   
				// TODO Auto-generated method stub           
				//自身加载新链接,不做外部跳转                
				view.loadUrl(url);         
				return true;                
				}                             
			});                  
		wbShow.setWebChromeClient(new WebChromeClient(){                
			@Override                
			public void onProgressChanged(WebView view, int newProgress) {       
				// TODO Auto-generated method stub      
				super.onProgressChanged(view, newProgress);  
				//这里将textView换成你的progress来设置进度//          
				if (newProgress == 0) {//                  
					
					//progressBar.setVisibility(View.VISIBLE);//                               
					}  
			}   
//					pr}ogressBar.setProgress(newProgress);       
//					progressBar.postInvalidate();//               
//					if (newProgress == 100) {//                 
//						progressBar.setVisibility(View.GONE);//       
//						}              
//					}       

			/**
			 * 
			 * 
			 * 设置标题
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				BrowseActivity.this.title.setText(title);
				super.onReceivedTitle(view, title);
				
			}
				});
	}
}
