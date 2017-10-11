package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.common.ValidateHelper;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.NoScrollListView;
import com.lidroid.xutils.db.annotation.Check;
import com.vlvxing.app.R;
import com.vlvxing.app.model.PlaneUserInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买详情 添加用户身份信息 提交订单
 */

public class PlaneBuyDetailsActivity extends BaseActivity{

    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.check1)
    CheckBox check1;
    @Bind(R.id.check2)
    CheckBox check2;
    @Bind(R.id.bottom_left_lin)
    LinearLayout bottomLeftLin;//总价linearlayout
    @Bind(R.id.edit_lin)
    LinearLayout editLin;
    @Bind(R.id.user_info)
    NoScrollListView userList;
    @Bind(R.id.hangyixian)
    CheckBox hangyixian;//航意险
    @Bind(R.id.xieyi)
    CheckBox xieyi;//底部锂电池及危险品乘机须知
    @Bind(R.id.quicklypay_btn)
    Button quicklypay;//提交订单
    @Bind(R.id.ticket_number)
    TextView ticketNumber;//飞机票剩余票数

//    @Bind(R.id.details_withdrawal_txt)
//    TextView detailsWithDrawal;//退改详情
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int type=1; //支付方式
    private Dialog bottomDialog;
    private UserInfoListAdapter adapter;
    private ArrayList<PlaneUserInfo> userInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_buy_details);
        ButterKnife.bind(this);
        mcontext = this;
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);
        //设置第一次进入界面不弹出软键盘
        editLin.setFocusable(true);
        editLin.setFocusableInTouchMode(true);
        editLin.requestFocus();

        userInfoList = new ArrayList<PlaneUserInfo>();
        PlaneUserInfo model = new PlaneUserInfo(1,"测试","4107281499402281033");
        userInfoList.add(model);
        adapter = new UserInfoListAdapter(mcontext);
        userList.setAdapter(adapter);
        xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    quicklypay.setClickable(true);
                    quicklypay.setBackgroundColor(Color.parseColor("#ea5413"));
                }else{
                    quicklypay.setBackgroundColor(Color.parseColor("#666666"));
                    quicklypay.setClickable(false);
                }
            }
        });
    }


    private void showPop(){
        View popupView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
        PopupWindow window = new PopupWindow(popupView, 400, 600);
        // TODO: 2016/5/17 设置背景颜色
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(ban_back, 0, 20);
    }

    private void showDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
//        ListView listview = (ListView) contentView.findViewById(R.id.listview);//展示UI容器 body中listview
//        ImageView close = (ImageView) contentView.findViewById(R.id.close);//关闭

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();

    }


    @OnClick({R.id.return_lin, R.id.right_txt,R.id.bottom_left_lin,R.id.pay_rel,R.id.wxpay_rel,R.id.add_btn,R.id.quicklypay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_rel: //支付宝
                type=1;
                check1.setChecked(true);
                check2.setChecked(false);
                break;
            case R.id.wxpay_rel:  //微信
                type=2;
                check2.setChecked(true);
                check1.setChecked(false);
                break;
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.bottom_left_lin:
//                Toast.makeText(mcontext, "点击了", Toast.LENGTH_SHORT).show();
                showDialog();
                //预订
//                showPop();
//                if(bottomDialog.isShowing()){
//                    bottomDialog.dismiss();
//                }else{
//                    showDialog();
//                }
                break;
            case R.id.add_btn:
                //新增乘客
               int result =  userInfoList.get(userInfoList.size()-1).getNumber()+1;
                PlaneUserInfo info = new PlaneUserInfo(result,"","");
                userInfoList.add(info);
                adapter.notifyDataSetChanged();
                break;
            case R.id.quicklypay_btn:
                //提交
                System.out.println("乘客信息:"+userInfoList.size());
                for(int i = 0;i<userInfoList.size();i++){
                    System.out.println("乘客信息:"+i+userInfoList.get(i).getName());
                    System.out.println("乘客信息:"+i+userInfoList.get(i).getCard());
                }
                break;



        }
    }
    /**
     * 动态添加乘客信息
     */

    private class UserInfoListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
//        private ArrayList<PlaneUserInfo> userInfoList;
        public UserInfoListAdapter(Context context) {
            super();
            this.inflater = LayoutInflater.from(context);
            this.context = context;
//            this.userInfoList = userInfoList;
        }

        @Override
        public int getCount() {
            return userInfoList.size();
        }
        @Override
        public Object getItem(int arg0) {
            return userInfoList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup arg2) {
            ViewHolder holder = null;
            if(view == null){
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.act_plane_user_info_listview_item, null);
                holder.name = (EditText) view.findViewById(R.id.name);
                holder.idCard = (EditText) view.findViewById(R.id.id_card);
                holder.delete = (LinearLayout) view.findViewById(R.id.delete_lin);
                holder.number = (TextView)view.findViewById(R.id.item_number);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }

            holder.number.setText(userInfoList.get(position).getNumber().toString());


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0){
                        Toast.makeText(context, "至少有一位乘客的身份信息才可购票哟", Toast.LENGTH_SHORT).show();
                    }else{
                        userInfoList.remove(position);
                        adapter.notifyDataSetChanged();
                    }

                }
            });

            if (holder.name.getTag() instanceof TextWatcher) {
                holder.name.removeTextChangedListener((TextWatcher) holder.name.getTag());
            }
            if (holder.idCard.getTag() instanceof TextWatcher) {
                holder.idCard.removeTextChangedListener((TextWatcher) holder.idCard.getTag());
            }
            //在重构adapter的时候不至于数据错乱
            if (!userInfoList.get(position).getName().equals("")  &&  !userInfoList.get(position).getCard().equals("")){
                holder.name.setText(userInfoList.get(position).getName());
                holder.idCard.setText(userInfoList.get(position).getCard());
            }else{
                holder.name.setText("");
                holder.idCard.setText("");
            }


            //名字验证并存储
            TextWatcher nameWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().equals("")){
                        //验证不通过，返回""空字符串
                        userInfoList.get(position).setName("");
                    }else{
                        userInfoList.get(position).setName(s.toString());
                    }

                }
            };
            //身份证号码验证并存储
            TextWatcher idCardWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!StringUtils.isStringNull(s.toString())){
                        if (ValidateHelper.isIDCard(s.toString())) {
//                           //验证通过
                            userInfoList.get(position).setCard(s.toString()); //用户身份证
                        }else{
                            //身份证号码不合法
                            userInfoList.get(position).setCard("");
                        }
                    }

                }
            };
            holder.name.addTextChangedListener(nameWatcher);
            holder.name.setTag(nameWatcher);
            holder.idCard.addTextChangedListener(idCardWatcher);
            holder.idCard.setTag(idCardWatcher);

            return view;
        }
    }


    public final class ViewHolder {
        public EditText name;
        public EditText idCard;
        public LinearLayout delete;
        public TextView number;
    }

}
