package com.vlvxing.app.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.ui.GenderSexDialogActivity;
import com.vlvxing.app.widget.ImagePickerHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author lvning
 * @version create time:2014-10-29_上午9:56:45
 * @Description 预订日选择
 */
public class CalendarSelectorActivity extends BaseActivity {

	/**
	 * 可选天数
	 */
	public static final String DAYS_OF_SELECT = "days_of_select";
	/**
	 * 上次预订日
	 */
	public static final String ORDER_DAY = "order_day";

	private int daysOfSelect;
	private String orderDay;

	private ListView listView;
	@Bind(R.id.head_title)
	TextView headTitle;
	@Bind(R.id.right_txt)
	TextView rightTxt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_selector);
		daysOfSelect = getIntent().getIntExtra(DAYS_OF_SELECT, 30);
		orderDay = getIntent().getStringExtra(ORDER_DAY);
		listView = (ListView) findViewById(R.id.lv_calendar);
		ButterKnife.bind(this);
		headTitle.setText("选择日期");
		rightTxt.setVisibility(View.VISIBLE);
		rightTxt.setText("确定");
		rightTxt.setTextColor(getResources().getColor(R.color.color_ea5413));
		CalendarListAdapter adapter = new CalendarListAdapter(this, daysOfSelect, orderDay);
		listView.setAdapter(adapter);

		adapter.setOnCalendarOrderListener(new CalendarListAdapter.OnCalendarOrderListener() {

			@Override
			public void onOrder(String orderInfo,String days) {
				Intent result = new Intent();
				result.putExtra(ORDER_DAY, orderInfo);
				if (days!=null){
					result.putExtra("days", days);
				}
				setResult(RESULT_OK, result);
				finish();
			}
		});
	}
	@OnClick({R.id.return_lin, R.id.right_txt})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.return_lin:
				finish();
				break;
			case R.id.right_txt:
//				clickSave();
				break;
		}
	}
}
