package com.handongkeji.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vlvxing.app.R;


public class MyTitleLayout extends RelativeLayout {

	public ImageView mLeftBtn;
	private TextView mRightText,mleftText;
	private Button mRightBtn;
	public RelativeLayout rlBack;
	public RelativeLayout mLyt;
	
	public MyTitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.ui_mytitle, this);
		mLyt = (RelativeLayout) findViewById(R.id.rlBj);
		mLeftBtn = (ImageView) findViewById(R.id.aci_left_btn);
		mRightText = (TextView) findViewById(R.id.aci_right_tv);
		rlBack = (RelativeLayout) findViewById(R.id.rlBack);
		mRightBtn = (Button) findViewById(R.id.aci_right_btn);
		mleftText = (TextView) findViewById(R.id.aci_left_tv);
		
		mLeftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();
			}
		});
		
		rlBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((Activity) getContext()).finish();
			}
		});
		mRightText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(getContext(), "You clicked Edit button",
				// Toast.LENGTH_SHORT).show();
			}
		});
		mRightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();
			}
		});
	}

	public void setLisener(OnClickListener backListener, OnClickListener editListener) {
		mLeftBtn = (ImageView) findViewById(R.id.aci_left_btn);
		mRightText = (TextView) findViewById(R.id.aci_right_tv);
		if (backListener == null) {
			mLeftBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((Activity) getContext()).finish();
				}
			});
		} else {
			mLeftBtn.setOnClickListener(backListener);
		}
		if (editListener == null) {
			mRightText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getContext(), "You clicked Edit button", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			mRightText.setOnClickListener(editListener);
		}
	}

	public void setRightTextVisible(boolean flag) {
		mRightText = (TextView) findViewById(R.id.aci_right_tv);
		if (flag) {
			mRightText.setVisibility(View.VISIBLE);
		} else {
			mRightText.setVisibility(View.INVISIBLE);
		}
	}

	public void setLefttTextVisible(boolean flag) {
		if (flag) {
			mLeftBtn.setVisibility(View.GONE);
			mleftText.setVisibility(View.VISIBLE);
		} else {
			mLeftBtn.setVisibility(View.VISIBLE);
			mleftText.setVisibility(View.GONE);
		}
	}

	public void setRightText(String text) {
		mRightText = (TextView) findViewById(R.id.aci_right_tv);
		mRightText.setText(text);
	}

	public void setLefttText(String text) {
		mleftText.setText(text);
	}
	public void setLefttTextListener(OnClickListener editListener) {
		mleftText.setOnClickListener(editListener);
	}

	public void setRightTextListener(OnClickListener editListener) {
		mRightText = (TextView) findViewById(R.id.aci_right_tv);
		mRightText.setOnClickListener(editListener);
	}
	// public void setLeftText(String text) {
	// txtvLeft = (TextView) findViewById(R.id.aci_right_tv);
	// txtvLeft.setText(text);
	// }

	public void setTitle(String title) {
		TextView titleText = (TextView) findViewById(R.id.aci_title_tv);
		titleText.setText(title);
	}

	public void setSubTitle(String subtitle) {
		TextView subtitleText = (TextView) findViewById(R.id.aci_subtitle_tv);
		subtitleText.setVisibility(View.VISIBLE);
		subtitleText.setText(subtitle);
	}

	public void setBackBtnVisible(boolean flag) {
		mLeftBtn = (ImageView) findViewById(R.id.aci_left_btn);
		if (flag) {
			mLeftBtn.setVisibility(View.VISIBLE);
			rlBack.setVisibility(View.VISIBLE);
		} else {
			mLeftBtn.setVisibility(View.INVISIBLE);
			rlBack.setVisibility(View.INVISIBLE);
		}
	}

	public void setRightTextBackground(int resId) {
		mRightText.setVisibility(View.VISIBLE);
		mRightText.setBackgroundResource(resId);
		mRightBtn.setVisibility(View.GONE);
	}

	public void setRightBtnBackground(int resId) {
		mRightText.setVisibility(View.GONE);
		mRightBtn.setVisibility(View.VISIBLE);
		mRightBtn.setBackgroundResource(resId);
	}

	public void setBackground(int resId){
		mLyt.setBackgroundResource(resId);
	}
}
