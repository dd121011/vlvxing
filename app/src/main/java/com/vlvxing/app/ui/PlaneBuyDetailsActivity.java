package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handongkeji.common.ValidateHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.NoScrollListView;
import com.qunar.bean.BookingResponseParam;
import com.qunar.bean.ExpressInfo;
import com.qunar.bean.ExtInfo;
import com.qunar.bean.FlightInfo1;
import com.qunar.bean.PriceInfo;
import com.qunar.bean.TgqPointCharge;
import com.qunar.bean.TgqShowData;
import com.qunar.bean.Vendor;
import com.qunar.model.PlaneBookResult;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.PlaneUserInfo;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.fromYDelta;
import static com.vlvxing.app.R.id.com_code;

/**
 * 购买详情 添加用户身份信息 提交订单
 */

public class PlaneBuyDetailsActivity extends BaseActivity {

    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
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
    @Bind(R.id.date_txt)
    TextView date_txt;//日期
    @Bind(R.id.month_txt)
    TextView month_txt;//一周中的第几天
    @Bind(R.id.ticket_number)
    TextView ticketNumber;//飞机票剩余票数
    @Bind(R.id.pay_lin)
    LinearLayout payLin;//支付
    @Bind(R.id.total_txt)
    TextView totalTxt;//总金额
    @Bind(R.id.editText)
    EditText phoneEdt;
    @Bind(R.id.check1)
    CheckBox check1;//支付宝
    @Bind(R.id.check2)
    CheckBox check2;//微信
    @Bind(R.id.h_price)
    TextView hPrice;//价格(航意险)
    @Bind(R.id.h_number)
    TextView hNumber;//份数(航意险)
    @Bind(R.id.price)
    TextView price_txt;//票价
    @Bind(R.id.fuel_price)
    TextView fuelPrice;//机建+燃油
    @Bind(R.id.baoxiaopingzheng)
    CheckBox baoxiaopingzheng;//报销凭证
    @Bind(R.id.baoxiao_name_edit)
    EditText baoxiao_name_edit;//报销人
    @Bind(R.id.baoxiao_phone_edit)
    EditText baoxiao_phone_edit;//报销人
    @Bind(R.id.baoxiao_adress_edit)
    EditText baoxiao_adress_edit;//报销人
    @Bind(R.id.baoxiao_lin_body)
    LinearLayout baoxiao_lin_body;//报销linearlayout
    @Bind(R.id.body_lin)
    LinearLayout body_lin;

    //    @Bind(R.id.details_withdrawal_txt)
//    TextView detailsWithDrawal;//退改详情
    private Context mcontext;
    private String goCity;
    private String arriveCity;
    private String date;
    private int type;//支付方式
    private int payWay = 1;
    private Dialog bottomDialog;
    private UserInfoListAdapter adapter;
    private ArrayList<PlaneUserInfo> userInfoList;
    private PopupWindow mPopupWindow;
    private boolean isPopWindowShowing = false;
    private View mGrayLayout;
    private int fromYDelta;
    private String price, pname, imgUrl, id;
    private String tradeNo, orderId, totalPrice = "0.01", commodityName = "V旅行", commodityMessage = "支付";
    private int p = 0;//机票单价+机建+燃油费,从上个页面传递过来的(购票必选)
    private int j = 0;//机建+燃油费
    private int num = 1;//购票数量,从用户添加的乘客来统计
    private int h = 0;//航意险单价
    private int y = 20;//报销需要的邮费
    private Vendor vendor;
    private String com;//航班号
    private String code;//航司
    private String bTime;//起飞时间
    private String vendorStr;
    private String depCode;
    private String arrCode;
    private String carrier;
    private String dateResult;
    private String weekResult;

    //生单接口传值
    //必传
    private String bookingResult;//预定结果
    private String contact = "";//联系人姓名
    private String cardNo = "'";//证件号
    private String contactMob;//电话
    //选传 选择报销则需传以下字段
    private String attnName;//收件人姓名
    private String attnPhone;//收件人电话
    private String attnAddress;//收件人地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_buy_details);
        ButterKnife.bind(this);
        mcontext = this;
        EventBus.getDefault().register(this);
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        vendor = (Vendor) getIntent().getSerializableExtra("vendor");
        com = getIntent().getStringExtra("com");//航班号
        code = getIntent().getStringExtra("code");//航司
        bTime = getIntent().getStringExtra("bTime");
        vendorStr = getIntent().getStringExtra("vendorStr");
        depCode = getIntent().getStringExtra("depCode");
        arrCode = getIntent().getStringExtra("arrCode");
        carrier = getIntent().getStringExtra("carrier");
        dateResult = getIntent().getStringExtra("dateResult");
        weekResult = getIntent().getStringExtra("weekResult");
        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);

        //设置第一次进入界面不弹出软键盘
        editLin.setFocusable(true);
        editLin.setFocusableInTouchMode(true);
        editLin.requestFocus();
        mGrayLayout = (View) findViewById(R.id.gray_layout);
        userInfoList = new ArrayList<PlaneUserInfo>();
        PlaneUserInfo model = new PlaneUserInfo(1, "", "");
        userInfoList.add(model);
        adapter = new UserInfoListAdapter(mcontext);
        userList.setAdapter(adapter);
        //监听锂电池乘机须知 监听
        setOnCheckChange();
        setOnEditTextChangeLinstener();
        setHangyixianOnCheckChange();
        initData();
    }

    private void setOnEditTextChangeLinstener() {
        //报销人
        baoxiao_name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                attnName = baoxiao_name_edit.getText().toString();
            }
        });
        //报销手机号
        baoxiao_phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                attnPhone = baoxiao_phone_edit.getText().toString();

            }
        });
        //报销手机号
        baoxiao_adress_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                attnAddress = baoxiao_adress_edit.getText().toString();
            }
        });
    }

    private void setOnCheckChange() {
        //协议条款
        xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    quicklypay.setClickable(true);
                    quicklypay.setBackgroundColor(Color.parseColor("#ea5413"));
                } else {
                    quicklypay.setBackgroundColor(Color.parseColor("#666666"));
                    quicklypay.setClickable(false);

                }
            }
        });
        //报销凭证
        baoxiaopingzheng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                totalTxt.getText();
                if (b) {
                    baoxiao_lin_body.setVisibility(View.VISIBLE);
                    int price = (p + j + h) * num + y;
                    totalTxt.setText(String.valueOf(price));
                } else {
                    baoxiao_lin_body.setVisibility(View.GONE);
                    totalTxt.setText(String.valueOf((p + j + h) * num));
                }

            }
        });
    }

    private void setHangyixianOnCheckChange() {
        hangyixian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    hNumber.setText(num + "");
                    totalTxt.setText((p + j + h) * num + "");
                } else {
                    hNumber.setText("0");
                    totalTxt.setText((p + j) * num + "");
                }
            }
        });

    }


    private void showDialog() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
        bottomDialog.setContentView(contentView);

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView firstPrice = (TextView) contentView.findViewById(R.id.b_first_price);//票价
        TextView secondPrice = (TextView) contentView.findViewById(R.id.b_second_price);//燃油
        TextView threePrice = (TextView) contentView.findViewById(R.id.b_three_price);//航意险

        TextView firstNumber = (TextView) contentView.findViewById(R.id.b_first_number);
        TextView secondNumber = (TextView) contentView.findViewById(R.id.b_second_number);
        TextView threeNumber = (TextView) contentView.findViewById(R.id.b_three_number);

        LinearLayout baoxiao_lin = (LinearLayout) contentView.findViewById(R.id.baoxiao_lin);
        if (baoxiaopingzheng.isChecked()) {
            baoxiao_lin.setVisibility(View.VISIBLE);
        } else {
            baoxiao_lin.setVisibility(View.GONE);
        }

        TextView total_txt = (TextView) contentView.findViewById(R.id.total_txt);//总金额
        firstPrice.setText(p + "");
        firstNumber.setText(num + "");
        secondPrice.setText(j + "");
        secondNumber.setText(num + "");
        if (hangyixian.isChecked()) {
            threeNumber.setText(num + "");
            threePrice.setText(h + "");
        } else {
            threeNumber.setText("0");
            threePrice.setText(h + "");
        }
        total_txt.setText(totalTxt.getText().toString());
        LinearLayout bottomLeftLin = (LinearLayout) contentView.findViewById(R.id.bottom_left_lin);
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

    private boolean checkParams() {
        contactMob = phoneEdt.getText().toString().trim();
//        contactMob = "18734679105";
        if (StringUtils.isStringNull(contactMob)) {
            ToastUtils.show(this, "请输入联系人电话!");
            return false;
        }
        if (!ValidateHelper.isPhoneNumberValid(contactMob)) {
            ToastUtils.show(this, "请输入正确的手机号!");
            return false;
        }
        if (baoxiaopingzheng.isChecked()) {
            if (attnName.equals("")) {
                ToastUtils.show(this, "请填写收件人姓名!");
                return false;
            }
            if (attnPhone.equals("")) {
                ToastUtils.show(this, "请填写收件人手机号码!");
                return false;
            }
            if (attnAddress.equals("")) {
                //报销人信息填写完整
                ToastUtils.show(this, "请填收件人地址!");
                return false;
            }
        }

        //把填写的用户信息保存到本地
//        List<UserInfo> list = CacheUserData.getRecentSubList();
//      if (list.size() <= 0) {
//        saveUserInfo(name,phones,address,num);
//      }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.return_lin, R.id.right_txt, R.id.bottom_left_lin, R.id.pay_rel, R.id.wxpay_rel, R.id.add_btn, R.id.quicklypay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_rel: //支付宝
                type = 1;
                payWay = 1;
                check1.setChecked(true);
                check2.setChecked(false);
                break;
            case R.id.wxpay_rel:  //微信
                type = 2;
                payWay = 2;
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
                //显示总价明细
                showDialog();
                break;
            case R.id.add_btn:
                //新增乘客
//                int result = userInfoList.get(userInfoList.size() - 1).getNumber() + 1;
                int result = userInfoList.size() + 1;
                PlaneUserInfo info = new PlaneUserInfo(result, "", "");
                userInfoList.add(info);
                adapter.notifyDataSetChanged();
                num++;
//                Toast.makeText(mcontext, "num" + num, Toast.LENGTH_SHORT).show();
                addNum(num);
                break;
            case R.id.quicklypay_btn:
                //提交订单
                //需要遍历userInfoList 取得每个乘客的信息
                contact = "";
                cardNo = "";
                for (int i = 0; i < userInfoList.size(); i++) {
                    String name = userInfoList.get(i).getName().toString().trim();
                    String card = userInfoList.get(i).getCard().toString().trim();
                    if (!name.equals("") && !card.equals("")) {
                        contact += name + ",";
                        cardNo += card + ",";
                    }
                }
                //验证
                if (!contact.equals("") && !cardNo.equals("") && checkParams()) {
                    createOrderData();
                } else {
                    ToastUtils.show(mcontext, "请您确认旅客信息录入是否正确");
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
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.act_plane_user_info_listview_item, null);
                holder.name = (EditText) view.findViewById(R.id.name);
                holder.idCard = (EditText) view.findViewById(R.id.id_card);
                holder.delete = (LinearLayout) view.findViewById(R.id.delete_lin);
                holder.number = (TextView) view.findViewById(R.id.item_number);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

//            holder.number.setText(userInfoList.get(position).getNumber().toString());
            holder.number.setText(position + 1 + "");

            //删除按钮(乘客信息)
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        Toast.makeText(context, "至少有一位乘客的身份信息才可购票哟", Toast.LENGTH_SHORT).show();
                    } else {
                        userInfoList.remove(position);
                        num--;
//                        Toast.makeText(context, "num" + num, Toast.LENGTH_SHORT).show();
                        lessNum(num);
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
            if (!userInfoList.get(position).getName().equals("") && !userInfoList.get(position).getCard().equals("")) {
                holder.name.setText(userInfoList.get(position).getName());
                holder.idCard.setText(userInfoList.get(position).getCard());
            } else {
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
                    if (s.toString().equals("")) {
                        //验证不通过，返回""空字符串
                        userInfoList.get(position).setName("");
                    } else {
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
                    if (!StringUtils.isStringNull(s.toString())) {
                        if (ValidateHelper.isIDCard(s.toString())) {
//                           //验证通过
                            userInfoList.get(position).setCard(s.toString()); //用户身份证
                        } else {
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

    //传机票数量,减去一个乘客的总金额
    private void lessNum(int num) {
        int s = 0;
        if (hangyixian.isChecked()) {
            s = p + j + h;
            hNumber.setText(num + "");
            int t = s * num;
            totalTxt.setText(t + "");
        } else {
            s = p + j;
            int t = s * num;
            totalTxt.setText(t + "");
        }
    }

    //传机票数量,加上一个乘客的总金额
    private void addNum(int num) {
        int s = 0;
        if (hangyixian.isChecked()) {
            s = p + j + h;
            hNumber.setText(num + "");
            int t = s * num;
            totalTxt.setText(t + "");
        } else {
            s = p + j;
            int t = s * num;
            totalTxt.setText(t + "");
        }
    }

    public void payMoney() {
        double price = Double.parseDouble(totalPrice);
//        Toast.makeText(this, "price" + price, Toast.LENGTH_SHORT).show();
        DecimalFormat decimalFormat = new DecimalFormat(
                "###################.###########");
        String totalMoney = decimalFormat.format(price);//变成整数类型
//        Toast.makeText(this, "totalMoney" + totalMoney, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "payWay" + payWay, Toast.LENGTH_SHORT).show();
//        int payMethod = dialog.getPayMethod();
        switch (payWay) {
            case 1:   //  支付宝支付
                Alipay alipay = new Alipay(this);
                alipay.getPlaneOrderInfo(tradeNo, totalMoney, orderId, commodityName, commodityMessage);
                break;
            case 2:          //  微信支付
                if (!IsInstallApp.isInstall(this, "com.tencent.mm")) {
                    Toast.makeText(this, "您还没有安装微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                WxPay wxPay = new WxPay(this);
                wxPay.getOrderInfo(tradeNo, totalMoney, orderId); //生成微信支付参数
                break;
        }
    }

    private void initData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String, Object> params = new HashMap<>();
        params.put("depCode", depCode);
        params.put("arrCode", arrCode);
        params.put("date", date);
        params.put("code", code);//航司
        params.put("carrier", carrier);//航班号
        params.put("btime", bTime);//起飞时间
        params.put("vendorStr", URLEncoder.encode(vendorStr));

        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url + "booking", params, mcontext, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
//                System.out.println("booking接口 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
//                System.out.println("booking接口 json:"+json);
                Gson gson = new Gson();
                PlaneBookResult model = gson.fromJson(json, PlaneBookResult.class);
                int status = model.getStatus();
                if (status == 1) {
                    BookingResponseParam response = model.getData();
                    //-1是空  -2是错误的
//                    if (model.getData() instanceof BookingResponseParam){
//                        response = (BookingResponseParam)model.getData();
//                    }
                    BookingResponseParam.Result result = response.getResult();
                    if (result == null) {
                        ToastUtils.show(mcontext, "查不到该航班");
                        dismissDialog();
                        return;
                    }
                    bookingResult = response.getBookingResult();
                    List<FlightInfo1> flightInfos = result.getFlightInfo();//航班信息
                    PriceInfo priceInfo = result.getPriceInfo();//报价信息
                    ExtInfo extInfo = result.getExtInfo();//扩展信息
                    if (extInfo == null) {
                        ToastUtils.show(mcontext, "该航班价格信息获取失败");
                        dismissDialog();
                        return;
                    }
                    ExpressInfo expressInfo = result.getExpressInfo();//报销凭证信息
                    TgqShowData tgqShowData = result.getTgqShowData();//退改签展示信息
                    p = Integer.parseInt(extInfo.getPrice());

                    int tof = 0;
                    int arf = 0;
                    if (flightInfos.size() > 0) {
                        FlightInfo1 flightInfo = flightInfos.get(0);
                        tof = flightInfo.getTof();//燃油
                        arf = flightInfo.getArf();//机建
                        j = tof + arf;
                    }

                    //退改签信息展示
                    List<TgqPointCharge> tgqPointCharges = tgqShowData.getTgqPointCharges();
                    if (tgqPointCharges.size() > 0) {
                        TgqPointCharge tgqPointCharge = tgqPointCharges.get(0);
                        int time = tgqPointCharge.getTime();//起飞前 hour 小时之前
                        String timeText = tgqPointCharge.getTimeText();//起飞前 hour 小时之前
                        int changeFee = tgqPointCharge.getChangeFee();//退票
                        int returnFee = tgqPointCharge.getReturnFee();//改期费

                        date_txt.setText(dateResult);//月份
                        month_txt.setText(weekResult);//周几?
                        price_txt.setText(p + "");//裸票价
                        fuelPrice.setText(j + "");//机建燃油
                        totalTxt.setText(p + j + h + "");//票价+(机建+燃油费)+航意险
                        body_lin.setVisibility(View.VISIBLE);
                    }

                } else {
                    ToastUtils.show(mcontext, model.getMessage());
                }
                dismissDialog();

            }
        });
    }

    private void createOrderData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String, String> params = new HashMap<>();
        params.put("contact", contact);
        params.put("contactMob", contactMob);
        params.put("cardNo", cardNo);
        params.put("bookingResult", bookingResult);
        params.put("userid", MyApp.getInstance().getUserId());
        if (baoxiaopingzheng.isChecked()) {
            params.put("attnName", attnName);//收件人姓名
            params.put("attnPhone", attnPhone);//收件人电话
            params.put("attnAddress", attnAddress);//收件人地址
        }
        showDialog("加载中...");
        RemoteDataHandler.asyncPlanePost(url + "createOrder", params, mcontext, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
//                System.out.println("生单接口 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
//                System.out.println("生单接口 json:"+json);
                JSONObject object = new JSONObject(json);
                int status = object.getInt("status");
                if (status == 1) {
                    JSONObject jsonData = object.getJSONObject("data");
                    int orderStatus = jsonData.getInt("code");//为0是成功
                    if (orderStatus == 0) {
                        JSONObject result = jsonData.getJSONObject("result");
                        tradeNo = result.getString("orderNo");
                        totalPrice = String.valueOf(result.getInt("noPayAmount"));//待支付金额
                        orderId = result.getInt("id") + "";
                        payMoney();
                    }
                } else {
                    ToastUtils.showT(mcontext, object.getString("message"));
                }
                dismissDialog();
            }
        });
    }

    @Subscriber(tag = "changeMyPlaneOrders")
    public void changeMyPlaneOrders(int t) {
        this.finish();
        startActivity(new Intent(this, PlaneOrderActivity.class));
    }
}
