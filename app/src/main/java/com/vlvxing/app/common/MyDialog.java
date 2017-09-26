package com.vlvxing.app.common;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vlvxing.app.R;


public class MyDialog implements OnClickListener {

	Context activity;
	Dialog dialog;
	String callNumber;
	private TextView phone_number;
	private TextView cancel;
	private TextView phone;

	public MyDialog(Context activity, String callNumber) {
		this.activity = activity;
		this.callNumber = callNumber;
		init();
	}

	private void init() {
		dialog = new Dialog(activity);
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		ViewGroup decorView = (ViewGroup) dialog.getWindow().getDecorView();
		decorView.removeAllViews();
		decorView.setBackgroundColor(Color.TRANSPARENT);
		LayoutInflater.from(activity).inflate(R.layout.dialog_layout,
				decorView, true);
		phone_number = (TextView) decorView
				.findViewById(R.id.content);
		cancel = (TextView) decorView.findViewById(R.id.cancel);
		phone = (TextView) decorView.findViewById(R.id.ok);
		phone_number.setText(callNumber);
		cancel.setOnClickListener(this);
		phone.setOnClickListener(this);
	}

	public void showDialog(){
		dialog.show();
	}
	
	public void dismissDialog(){
		dialog.dismiss();
	}

	
	public void setTitle(CharSequence text){
		phone_number.setText(text);
	}
	
	public void setCancelText(CharSequence text){
		cancel.setText(text);
	}
	public void setOkText(CharSequence text){
		phone.setText(text);
	}
	
	public void setNegativeButtonListener(OnClickListener l){
		phone.setOnClickListener(l); //点击确定
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel:
				dismissDialog();
				break;
		}
	}
}
