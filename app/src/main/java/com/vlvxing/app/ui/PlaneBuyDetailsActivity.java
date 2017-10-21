package com.vlvxing.app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.vlvxing.app.utils.AnimationUtil;
import com.vlvxing.app.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.fromYDelta;

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
    @Bind(R.id.pay_lin)
    LinearLayout payLin;//支付
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
    private PopupWindow mPopupWindow;
    private boolean isPopWindowShowing = false;
    private View mGrayLayout;
    private int fromYDelta;

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
        mGrayLayout = (View)findViewById(R.id.gray_layout) ;
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
    private void showPopupWindow(){
        final View contentView= LayoutInflater.from(mcontext).inflate(R.layout.act_plane_buy_price_popupwindow_,null);
//        TextView t1= (TextView) contentView.findViewById(R.id.text1);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mGrayLayout.setVisibility(View.VISIBLE);

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
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);

        View contentView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
        bottomDialog.setContentView(contentView);

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels);
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
//        bottomDialog.getWindow().getAttributes().y = 80;
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        LinearLayout bottomLeftLin = (LinearLayout)contentView.findViewById(R.id.bottom_left_lin);
        //设置点击外部空白处可以关闭Activity

        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.setCancelable(true);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        bottomLeftLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

    }
    private void showPopwindow(){
        // 用于PopupWindow的View
//        View popupView  = LayoutInflater.from(mcontext).inflate(R.layout.act_plane_buy_price_popupwindow_, null, false);
//        PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

//        View view = LayoutInflater.from(context).inflate(R.layout.order_price_detail, null);
//
//        // 设置可以获得焦点
//        mPopupWindow.setFocusable(true);
//        // 设置弹窗内可点击
//        mPopupWindow.setTouchable(true);
//        // 设置弹窗外可点击
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
////        mPopupWindow.setAnimationStyle(R.style.popup_animation);
//        setContentView(popupView);
//        //获取自身的长宽高
//        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupHeight = view.getMeasuredHeight();
//        popupWidth = view.getMeasuredWidth();















//        int width = getResources().getDisplayMetrics().widthPixels;
////        int height = getResources().getDisplayMetrics().heightPixels;
//        // 创建PopupWindow对象，其中：
//        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
//        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
//        PopupWindow window=new PopupWindow(contentView, width, 300);
//        // 设置PopupWindow的背景
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        window.setBackgroundDrawable(new BitmapDrawable());
//        window.setFocusable(true);// 点击空白处时，隐藏掉pop窗口
//        // 设置PopupWindow是否能响应外部点击事件
//        window.setOutsideTouchable(true);
//        // 设置PopupWindow是否能响应点击事件
//        window.setTouchable(true);
//        // 显示PopupWindow，其中：
//        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(payLin, 0, 0);
//        // 或者也可以调用此方法显示PopupWindow，其中：
//        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
//        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//        // window.showAtLocation(parent, gravity, x, y);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
//                showPopupWindow();
                showDialog();
//                showPopwindow();
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
