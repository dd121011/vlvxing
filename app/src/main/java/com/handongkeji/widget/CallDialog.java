package com.handongkeji.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vlvxing.app.R;
import com.vlvxing.app.ui.LoginActivity;


public class CallDialog implements OnClickListener {

	Context activity;
	Dialog dialog;
	String callNumber;
	private TextView phone_number;
	private TextView cancel;
	private TextView phone;
	
	public CallDialog(Context activity, String callNumber) {
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
		LayoutInflater.from(activity).inflate(R.layout.call_dialog_layout,
				decorView, true);
		phone_number = (TextView) decorView
				.findViewById(R.id.content);
		cancel = (TextView) decorView.findViewById(R.id.cancel);
		phone = (TextView) decorView.findViewById(R.id.ok);

//		if (callNumber.length() >= 11) {
//			String first = callNumber.substring(0, 3);
//			String second = callNumber.substring(3, 7);
//			String third = callNumber.substring(7, callNumber.length());
//			phone_number.setText(first + "-" + second + "-" + third);
//		}else{
//			phone_number.setText(callNumber);
//		}
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
//				activity.startActivity(new Intent(activity, LoginActivity.class));
				break;
		}
	}
}
