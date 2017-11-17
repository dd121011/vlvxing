package com.vlvxing.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.widget.MyProcessDialog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.ui.OrderDetailActivity;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	private MyProcessDialog dialog;
	private int getCount = 0;
	private int flg = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_weixin_entry_pay);

		api = WXAPIFactory.createWXAPI(this, Constants.WX_ID,false);
		api.handleIntent(getIntent(), this);

		dialog = new MyProcessDialog(this);
		dialog.show();
		dialog.setMsg("正在完成支付");

	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	// 回调
	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			if (resp.errCode == 0) { //支付成功
				ToastUtils.show(WXPayEntryActivity.this,"支付成功!");
				finish();
//				GetServerStatus();
				EventBus.getDefault().post(0, "changeMyorders"); //跳转到我的订单
				EventBus.getDefault().post(0, "changeMyorder"); //跳转到我的订单

			} else if(resp.errCode == -1){// 支付失败
				flg = 1;
				finish();
			}else{ // -2  用户取消
				ToastUtils.show(WXPayEntryActivity.this,"支付已取消!");
				finish();
			}
		}
	}

}