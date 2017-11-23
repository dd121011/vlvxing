package com.qunar.bean.changeSearch;

import java.io.Serializable;
import java.util.List;

/**
 * 改签查询
 * @author Zophar
 */
public class PlaneChangeSerachBean implements Serializable{


  /**
   * code : 0
   * message : SUCCESS
   * createTime : 1511247873233
   * result : [{"refundSearchResult":null,"refundApplyResult":null,"changeSearchResult":{"reason":null,"baseOrderInfo":{"status":2,"statusDesc":"出票完成","showNotWork":false,"distributeType":1},"flightSegmentList":[{"flightNo":"ZH1117","flightCo":"中国深圳航空公司","flightShortCo":"深圳航空","flightLogoUrl":"http://source.qunar.com/site/images/airlines/small/ZH.gif","flightPhone":"075595361","dptCity":"北京","arrCity":"呼和浩特","dptPort":"首都机场","arrPort":"白塔机场","dptAirport":"PEK","arrAirport":"HET","dptTerminal":null,"arrTerminal":null,"dptDate":"2017-12-15","dptTime":"09:00","arrDate":"2017-12-15","arrTime":"10:25","stopCity":null,"stopAirport":null,"isShared":true,"realFlightNum":"CA1117","stop":false}],"changeRuleInfo":{"viewType":1,"hasTime":true,"tgqText":"同舱更改条件：航班起飞前,改期手续费为0元;航班起飞后,改期手续费为72元<br />退票条件：航班起飞前,退票手续费为72元;航班起飞后,退票手续费为108元<br />签转条件：不可签转","signText":"不可签转","childTgqMsg":"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）","timePointCharges":"[{\"time\":0,\"timeText\":\"起飞前\",\"returnFee\":72,\"changeFee\":0},{\"time\":-2147483648,\"timeText\":\"起飞后\",\"returnFee\":108,\"changeFee\":72}]","refundDescription":null},"contactInfo":{"contactName":"李海光","phone":"152****0254"},"tgqViewInfoJson":"{\"orderNo\":\"xep171117211816765\",\"orderId\":19009218,\"depTime\":1513299600000,\"haveChild\":false,\"tgqType\":\"change\",\"serverTime\":1511247870282,\"nowChangeHour\":\"0\",\"nowChangeText\":\"起飞前\",\"nowChangeTextTwo\":\"起飞\",\"nowChangeTime\":1513299600000,\"nextChangeHour\":\"-1\",\"nextChangeText\":\"起飞后\",\"nextChangeTextTwo\":\"起飞后\",\"nextChangeTime\":-1,\"nowChildChangeHour\":null,\"nowChildChangeText\":null,\"nowChildChangeTextTwo\":null,\"nowChildChangeTime\":0,\"nextChildChangeHour\":null,\"nextChildChangeText\":null,\"nextChildChangeTextTwo\":null,\"nextChildChangeTime\":0,\"volunteers\":[1,2],\"viewType\":1,\"childTgqMsg\":\"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）\",\"contact\":\"李海光\",\"contactMob\":\"152****0254\",\"orderHaveBx\":false,\"orderHaveDelay\":false,\"orderHaveRefundTicket\":false,\"validBalance\":0,\"workTime\":true,\"urgent\":false}","tgqReasons":[{"code":1,"msg":"我要改变行程计划、我要改航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":2,"msg":"选错日期、选错航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":3,"msg":"航班延误或取消、航班时刻变更","will":false,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]}],"canChange":true},"changeApplyResult":null,"id":29320613,"name":"李海光","cardType":"NI","cardNum":"4102kPbps9Mo7I=539","ticketNum":"999-5908314020","birthday":"1988-02-04","gender":1}]
   */

  private int code;
  private String message;
  private long createTime;
  private List<ResultBean> result;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public List<ResultBean> getResult() {
    return result;
  }

  public void setResult(List<ResultBean> result) {
    this.result = result;
  }

  public static class ResultBean implements Serializable{
    /**
     * refundSearchResult : null
     * refundApplyResult : null
     * changeSearchResult : {"reason":null,"baseOrderInfo":{"status":2,"statusDesc":"出票完成","showNotWork":false,"distributeType":1},"flightSegmentList":[{"flightNo":"ZH1117","flightCo":"中国深圳航空公司","flightShortCo":"深圳航空","flightLogoUrl":"http://source.qunar.com/site/images/airlines/small/ZH.gif","flightPhone":"075595361","dptCity":"北京","arrCity":"呼和浩特","dptPort":"首都机场","arrPort":"白塔机场","dptAirport":"PEK","arrAirport":"HET","dptTerminal":null,"arrTerminal":null,"dptDate":"2017-12-15","dptTime":"09:00","arrDate":"2017-12-15","arrTime":"10:25","stopCity":null,"stopAirport":null,"isShared":true,"realFlightNum":"CA1117","stop":false}],"changeRuleInfo":{"viewType":1,"hasTime":true,"tgqText":"同舱更改条件：航班起飞前,改期手续费为0元;航班起飞后,改期手续费为72元<br />退票条件：航班起飞前,退票手续费为72元;航班起飞后,退票手续费为108元<br />签转条件：不可签转","signText":"不可签转","childTgqMsg":"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）","timePointCharges":"[{\"time\":0,\"timeText\":\"起飞前\",\"returnFee\":72,\"changeFee\":0},{\"time\":-2147483648,\"timeText\":\"起飞后\",\"returnFee\":108,\"changeFee\":72}]","refundDescription":null},"contactInfo":{"contactName":"李海光","phone":"152****0254"},"tgqViewInfoJson":"{\"orderNo\":\"xep171117211816765\",\"orderId\":19009218,\"depTime\":1513299600000,\"haveChild\":false,\"tgqType\":\"change\",\"serverTime\":1511247870282,\"nowChangeHour\":\"0\",\"nowChangeText\":\"起飞前\",\"nowChangeTextTwo\":\"起飞\",\"nowChangeTime\":1513299600000,\"nextChangeHour\":\"-1\",\"nextChangeText\":\"起飞后\",\"nextChangeTextTwo\":\"起飞后\",\"nextChangeTime\":-1,\"nowChildChangeHour\":null,\"nowChildChangeText\":null,\"nowChildChangeTextTwo\":null,\"nowChildChangeTime\":0,\"nextChildChangeHour\":null,\"nextChildChangeText\":null,\"nextChildChangeTextTwo\":null,\"nextChildChangeTime\":0,\"volunteers\":[1,2],\"viewType\":1,\"childTgqMsg\":\"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）\",\"contact\":\"李海光\",\"contactMob\":\"152****0254\",\"orderHaveBx\":false,\"orderHaveDelay\":false,\"orderHaveRefundTicket\":false,\"validBalance\":0,\"workTime\":true,\"urgent\":false}","tgqReasons":[{"code":1,"msg":"我要改变行程计划、我要改航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":2,"msg":"选错日期、选错航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":3,"msg":"航班延误或取消、航班时刻变更","will":false,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]}],"canChange":true}
     * changeApplyResult : null
     * id : 29320613
     * name : 李海光
     * cardType : NI
     * cardNum : 4102kPbps9Mo7I=539
     * ticketNum : 999-5908314020
     * birthday : 1988-02-04
     * gender : 1
     */

    private Object refundSearchResult;
    private Object refundApplyResult;
    private ChangeSearchResultBean changeSearchResult;
    private Object changeApplyResult;
    private int id;
    private String name;
    private String cardType;
    private String cardNum;
    private String ticketNum;
    private String birthday;
    private int gender;

    public Object getRefundSearchResult() {
      return refundSearchResult;
    }

    public void setRefundSearchResult(Object refundSearchResult) {
      this.refundSearchResult = refundSearchResult;
    }

    public Object getRefundApplyResult() {
      return refundApplyResult;
    }

    public void setRefundApplyResult(Object refundApplyResult) {
      this.refundApplyResult = refundApplyResult;
    }

    public ChangeSearchResultBean getChangeSearchResult() {
      return changeSearchResult;
    }

    public void setChangeSearchResult(ChangeSearchResultBean changeSearchResult) {
      this.changeSearchResult = changeSearchResult;
    }

    public Object getChangeApplyResult() {
      return changeApplyResult;
    }

    public void setChangeApplyResult(Object changeApplyResult) {
      this.changeApplyResult = changeApplyResult;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCardType() {
      return cardType;
    }

    public void setCardType(String cardType) {
      this.cardType = cardType;
    }

    public String getCardNum() {
      return cardNum;
    }

    public void setCardNum(String cardNum) {
      this.cardNum = cardNum;
    }

    public String getTicketNum() {
      return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
      this.ticketNum = ticketNum;
    }

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public int getGender() {
      return gender;
    }

    public void setGender(int gender) {
      this.gender = gender;
    }

    public static class ChangeSearchResultBean implements Serializable{
      /**
       * reason : null
       * baseOrderInfo : {"status":2,"statusDesc":"出票完成","showNotWork":false,"distributeType":1}
       * flightSegmentList : [{"flightNo":"ZH1117","flightCo":"中国深圳航空公司","flightShortCo":"深圳航空","flightLogoUrl":"http://source.qunar.com/site/images/airlines/small/ZH.gif","flightPhone":"075595361","dptCity":"北京","arrCity":"呼和浩特","dptPort":"首都机场","arrPort":"白塔机场","dptAirport":"PEK","arrAirport":"HET","dptTerminal":null,"arrTerminal":null,"dptDate":"2017-12-15","dptTime":"09:00","arrDate":"2017-12-15","arrTime":"10:25","stopCity":null,"stopAirport":null,"isShared":true,"realFlightNum":"CA1117","stop":false}]
       * changeRuleInfo : {"viewType":1,"hasTime":true,"tgqText":"同舱更改条件：航班起飞前,改期手续费为0元;航班起飞后,改期手续费为72元<br />退票条件：航班起飞前,退票手续费为72元;航班起飞后,退票手续费为108元<br />签转条件：不可签转","signText":"不可签转","childTgqMsg":"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）","timePointCharges":"[{\"time\":0,\"timeText\":\"起飞前\",\"returnFee\":72,\"changeFee\":0},{\"time\":-2147483648,\"timeText\":\"起飞后\",\"returnFee\":108,\"changeFee\":72}]","refundDescription":null}
       * contactInfo : {"contactName":"李海光","phone":"152****0254"}
       * tgqViewInfoJson : {"orderNo":"xep171117211816765","orderId":19009218,"depTime":1513299600000,"haveChild":false,"tgqType":"change","serverTime":1511247870282,"nowChangeHour":"0","nowChangeText":"起飞前","nowChangeTextTwo":"起飞","nowChangeTime":1513299600000,"nextChangeHour":"-1","nextChangeText":"起飞后","nextChangeTextTwo":"起飞后","nextChangeTime":-1,"nowChildChangeHour":null,"nowChildChangeText":null,"nowChildChangeTextTwo":null,"nowChildChangeTime":0,"nextChildChangeHour":null,"nextChildChangeText":null,"nextChildChangeTextTwo":null,"nextChildChangeTime":0,"volunteers":[1,2],"viewType":1,"childTgqMsg":"退改签规则以航空公司最新规定为准，可咨询客服电话（95117）","contact":"李海光","contactMob":"152****0254","orderHaveBx":false,"orderHaveDelay":false,"orderHaveRefundTicket":false,"validBalance":0,"workTime":true,"urgent":false}
       * tgqReasons : [{"code":1,"msg":"我要改变行程计划、我要改航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":2,"msg":"选错日期、选错航班","will":true,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]},{"code":3,"msg":"航班延误或取消、航班时刻变更","will":false,"refundPassengerPriceInfoList":null,"changeFlightSegmentList":[{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]}]
       * canChange : true
       */

      private Object reason;
      private BaseOrderInfoBean baseOrderInfo;
      private ChangeRuleInfoBean changeRuleInfo;
      private ContactInfoBean contactInfo;
      private String tgqViewInfoJson;
      private boolean canChange;
      private List<FlightSegmentListBean> flightSegmentList;
      private List<TgqReasonsBean> tgqReasons;

      public Object getReason() {
        return reason;
      }

      public void setReason(Object reason) {
        this.reason = reason;
      }

      public BaseOrderInfoBean getBaseOrderInfo() {
        return baseOrderInfo;
      }

      public void setBaseOrderInfo(BaseOrderInfoBean baseOrderInfo) {
        this.baseOrderInfo = baseOrderInfo;
      }

      public ChangeRuleInfoBean getChangeRuleInfo() {
        return changeRuleInfo;
      }

      public void setChangeRuleInfo(ChangeRuleInfoBean changeRuleInfo) {
        this.changeRuleInfo = changeRuleInfo;
      }

      public ContactInfoBean getContactInfo() {
        return contactInfo;
      }

      public void setContactInfo(ContactInfoBean contactInfo) {
        this.contactInfo = contactInfo;
      }

      public String getTgqViewInfoJson() {
        return tgqViewInfoJson;
      }

      public void setTgqViewInfoJson(String tgqViewInfoJson) {
        this.tgqViewInfoJson = tgqViewInfoJson;
      }

      public boolean isCanChange() {
        return canChange;
      }

      public void setCanChange(boolean canChange) {
        this.canChange = canChange;
      }

      public List<FlightSegmentListBean> getFlightSegmentList() {
        return flightSegmentList;
      }

      public void setFlightSegmentList(List<FlightSegmentListBean> flightSegmentList) {
        this.flightSegmentList = flightSegmentList;
      }

      public List<TgqReasonsBean> getTgqReasons() {
        return tgqReasons;
      }

      public void setTgqReasons(List<TgqReasonsBean> tgqReasons) {
        this.tgqReasons = tgqReasons;
      }

      public static class BaseOrderInfoBean implements Serializable{
        /**
         * status : 2
         * statusDesc : 出票完成
         * showNotWork : false
         * distributeType : 1
         */

        private int status;
        private String statusDesc;
        private boolean showNotWork;
        private int distributeType;

        public int getStatus() {
          return status;
        }

        public void setStatus(int status) {
          this.status = status;
        }

        public String getStatusDesc() {
          return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
          this.statusDesc = statusDesc;
        }

        public boolean isShowNotWork() {
          return showNotWork;
        }

        public void setShowNotWork(boolean showNotWork) {
          this.showNotWork = showNotWork;
        }

        public int getDistributeType() {
          return distributeType;
        }

        public void setDistributeType(int distributeType) {
          this.distributeType = distributeType;
        }
      }

      public static class ChangeRuleInfoBean implements Serializable{
        /**
         * viewType : 1
         * hasTime : true
         * tgqText : 同舱更改条件：航班起飞前,改期手续费为0元;航班起飞后,改期手续费为72元<br />退票条件：航班起飞前,退票手续费为72元;航班起飞后,退票手续费为108元<br />签转条件：不可签转
         * signText : 不可签转
         * childTgqMsg : 退改签规则以航空公司最新规定为准，可咨询客服电话（95117）
         * timePointCharges : [{"time":0,"timeText":"起飞前","returnFee":72,"changeFee":0},{"time":-2147483648,"timeText":"起飞后","returnFee":108,"changeFee":72}]
         * refundDescription : null
         */

        private int viewType;
        private boolean hasTime;
        private String tgqText;
        private String signText;
        private String childTgqMsg;
        private String timePointCharges;
        private Object refundDescription;

        public int getViewType() {
          return viewType;
        }

        public void setViewType(int viewType) {
          this.viewType = viewType;
        }

        public boolean isHasTime() {
          return hasTime;
        }

        public void setHasTime(boolean hasTime) {
          this.hasTime = hasTime;
        }

        public String getTgqText() {
          return tgqText;
        }

        public void setTgqText(String tgqText) {
          this.tgqText = tgqText;
        }

        public String getSignText() {
          return signText;
        }

        public void setSignText(String signText) {
          this.signText = signText;
        }

        public String getChildTgqMsg() {
          return childTgqMsg;
        }

        public void setChildTgqMsg(String childTgqMsg) {
          this.childTgqMsg = childTgqMsg;
        }

        public String getTimePointCharges() {
          return timePointCharges;
        }

        public void setTimePointCharges(String timePointCharges) {
          this.timePointCharges = timePointCharges;
        }

        public Object getRefundDescription() {
          return refundDescription;
        }

        public void setRefundDescription(Object refundDescription) {
          this.refundDescription = refundDescription;
        }
      }

      public static class ContactInfoBean implements Serializable{
        /**
         * contactName : 李海光
         * phone : 152****0254
         */

        private String contactName;
        private String phone;

        public String getContactName() {
          return contactName;
        }

        public void setContactName(String contactName) {
          this.contactName = contactName;
        }

        public String getPhone() {
          return phone;
        }

        public void setPhone(String phone) {
          this.phone = phone;
        }
      }

      public static class FlightSegmentListBean implements Serializable{
        /**
         * flightNo : ZH1117
         * flightCo : 中国深圳航空公司
         * flightShortCo : 深圳航空
         * flightLogoUrl : http://source.qunar.com/site/images/airlines/small/ZH.gif
         * flightPhone : 075595361
         * dptCity : 北京
         * arrCity : 呼和浩特
         * dptPort : 首都机场
         * arrPort : 白塔机场
         * dptAirport : PEK
         * arrAirport : HET
         * dptTerminal : null
         * arrTerminal : null
         * dptDate : 2017-12-15
         * dptTime : 09:00
         * arrDate : 2017-12-15
         * arrTime : 10:25
         * stopCity : null
         * stopAirport : null
         * isShared : true
         * realFlightNum : CA1117
         * stop : false
         */

        private String flightNo;
        private String flightCo;
        private String flightShortCo;
        private String flightLogoUrl;
        private String flightPhone;
        private String dptCity;
        private String arrCity;
        private String dptPort;
        private String arrPort;
        private String dptAirport;
        private String arrAirport;
        private Object dptTerminal;
        private Object arrTerminal;
        private String dptDate;
        private String dptTime;
        private String arrDate;
        private String arrTime;
        private Object stopCity;
        private Object stopAirport;
        private boolean isShared;
        private String realFlightNum;
        private boolean stop;

        public String getFlightNo() {
          return flightNo;
        }

        public void setFlightNo(String flightNo) {
          this.flightNo = flightNo;
        }

        public String getFlightCo() {
          return flightCo;
        }

        public void setFlightCo(String flightCo) {
          this.flightCo = flightCo;
        }

        public String getFlightShortCo() {
          return flightShortCo;
        }

        public void setFlightShortCo(String flightShortCo) {
          this.flightShortCo = flightShortCo;
        }

        public String getFlightLogoUrl() {
          return flightLogoUrl;
        }

        public void setFlightLogoUrl(String flightLogoUrl) {
          this.flightLogoUrl = flightLogoUrl;
        }

        public String getFlightPhone() {
          return flightPhone;
        }

        public void setFlightPhone(String flightPhone) {
          this.flightPhone = flightPhone;
        }

        public String getDptCity() {
          return dptCity;
        }

        public void setDptCity(String dptCity) {
          this.dptCity = dptCity;
        }

        public String getArrCity() {
          return arrCity;
        }

        public void setArrCity(String arrCity) {
          this.arrCity = arrCity;
        }

        public String getDptPort() {
          return dptPort;
        }

        public void setDptPort(String dptPort) {
          this.dptPort = dptPort;
        }

        public String getArrPort() {
          return arrPort;
        }

        public void setArrPort(String arrPort) {
          this.arrPort = arrPort;
        }

        public String getDptAirport() {
          return dptAirport;
        }

        public void setDptAirport(String dptAirport) {
          this.dptAirport = dptAirport;
        }

        public String getArrAirport() {
          return arrAirport;
        }

        public void setArrAirport(String arrAirport) {
          this.arrAirport = arrAirport;
        }

        public Object getDptTerminal() {
          return dptTerminal;
        }

        public void setDptTerminal(Object dptTerminal) {
          this.dptTerminal = dptTerminal;
        }

        public Object getArrTerminal() {
          return arrTerminal;
        }

        public void setArrTerminal(Object arrTerminal) {
          this.arrTerminal = arrTerminal;
        }

        public String getDptDate() {
          return dptDate;
        }

        public void setDptDate(String dptDate) {
          this.dptDate = dptDate;
        }

        public String getDptTime() {
          return dptTime;
        }

        public void setDptTime(String dptTime) {
          this.dptTime = dptTime;
        }

        public String getArrDate() {
          return arrDate;
        }

        public void setArrDate(String arrDate) {
          this.arrDate = arrDate;
        }

        public String getArrTime() {
          return arrTime;
        }

        public void setArrTime(String arrTime) {
          this.arrTime = arrTime;
        }

        public Object getStopCity() {
          return stopCity;
        }

        public void setStopCity(Object stopCity) {
          this.stopCity = stopCity;
        }

        public Object getStopAirport() {
          return stopAirport;
        }

        public void setStopAirport(Object stopAirport) {
          this.stopAirport = stopAirport;
        }

        public boolean isIsShared() {
          return isShared;
        }

        public void setIsShared(boolean isShared) {
          this.isShared = isShared;
        }

        public String getRealFlightNum() {
          return realFlightNum;
        }

        public void setRealFlightNum(String realFlightNum) {
          this.realFlightNum = realFlightNum;
        }

        public boolean isStop() {
          return stop;
        }

        public void setStop(boolean stop) {
          this.stop = stop;
        }
      }

      public static class TgqReasonsBean implements Serializable{
        /**
         * code : 1
         * msg : 我要改变行程计划、我要改航班
         * will : true
         * refundPassengerPriceInfoList : null
         * changeFlightSegmentList : [{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"09:00","endTime":"10:25","flight":"中国国航","flightNo":"CA1117","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"5c6401d739a4f00b3415d09168b2b504","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1117","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"12:05","endTime":"13:30","flight":"中国国航","flightNo":"CA1111","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c907ccf69869b8b9d73844cdf5f01669","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1111","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"16:25","endTime":"17:50","flight":"中国国航","flightNo":"CA1105","flightType":"波音737(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"c9959cdc6fc4a4b3a77377565f96e6cf","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1105","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"19:25","endTime":"20:50","flight":"中国国航","flightNo":"CA1101","flightType":"波音738(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"9eac3f05dbf1e36aa89b07eceb4ac6e7","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1101","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null},{"dptAirportCode":"PEK","arrAirportCode":"HET","dptTerminal":"T3","arrTerminal":"","stopFlightInfo":{"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]},"startTime":"21:20","endTime":"22:45","flight":"中国国航","flightNo":"CA1115","flightType":"空客321(中)","startPlace":"北京首都国际机场","endPlace":"呼和浩特白塔机场","cabin":"经济舱","cabinCode":"Y","uniqKey":"14aabb1911fb9fd1dbd691d6a0fbd35d","carrier":"CA","extraPrice":null,"gqFee":"0","upgradeFee":"0","allFee":"0","cabinStatus":"A","actFlightNo":"CA1115","share":false,"adultUFee":"0","extraPriceText":null,"gqFeeText":"0/成人","upgradeFeeText":"0/成人","allFeeText":"0/成人","adultUFeeText":"0/成人","cabinStatusText":null}]
         */

        private int code;
        private String msg;
        private boolean will;
        private Object refundPassengerPriceInfoList;
        private List<ChangeFlightSegmentListBean> changeFlightSegmentList;

        public int getCode() {
          return code;
        }

        public void setCode(int code) {
          this.code = code;
        }

        public String getMsg() {
          return msg;
        }

        public void setMsg(String msg) {
          this.msg = msg;
        }

        public boolean isWill() {
          return will;
        }

        public void setWill(boolean will) {
          this.will = will;
        }

        public Object getRefundPassengerPriceInfoList() {
          return refundPassengerPriceInfoList;
        }

        public void setRefundPassengerPriceInfoList(Object refundPassengerPriceInfoList) {
          this.refundPassengerPriceInfoList = refundPassengerPriceInfoList;
        }

        public List<ChangeFlightSegmentListBean> getChangeFlightSegmentList() {
          return changeFlightSegmentList;
        }

        public void setChangeFlightSegmentList(List<ChangeFlightSegmentListBean> changeFlightSegmentList) {
          this.changeFlightSegmentList = changeFlightSegmentList;
        }

        public static class ChangeFlightSegmentListBean implements Serializable{
          /**
           * dptAirportCode : PEK
           * arrAirportCode : HET
           * dptTerminal : T3
           * arrTerminal :
           * stopFlightInfo : {"stopType":1,"stopTypeDesc":"直飞","stopCityInfoList":[]}
           * startTime : 09:00
           * endTime : 10:25
           * flight : 中国国航
           * flightNo : CA1117
           * flightType : 波音737(中)
           * startPlace : 北京首都国际机场
           * endPlace : 呼和浩特白塔机场
           * cabin : 经济舱
           * cabinCode : Y
           * uniqKey : 5c6401d739a4f00b3415d09168b2b504
           * carrier : CA
           * extraPrice : null
           * gqFee : 0
           * upgradeFee : 0
           * allFee : 0
           * cabinStatus : A
           * actFlightNo : CA1117
           * share : false
           * adultUFee : 0
           * extraPriceText : null
           * gqFeeText : 0/成人
           * upgradeFeeText : 0/成人
           * allFeeText : 0/成人
           * adultUFeeText : 0/成人
           * cabinStatusText : null
           */

          private String dptAirportCode;
          private String arrAirportCode;
          private String dptTerminal;
          private String arrTerminal;
          private StopFlightInfoBean stopFlightInfo;
          private String startTime;
          private String endTime;
          private String flight;
          private String flightNo;
          private String flightType;
          private String startPlace;
          private String endPlace;
          private String cabin;
          private String cabinCode;
          private String uniqKey;
          private String carrier;
          private Object extraPrice;
          private String gqFee;
          private String upgradeFee;
          private String allFee;
          private String cabinStatus;
          private String actFlightNo;
          private boolean share;
          private String adultUFee;
          private Object extraPriceText;
          private String gqFeeText;
          private String upgradeFeeText;
          private String allFeeText;
          private String adultUFeeText;
          private Object cabinStatusText;

          public String getDptAirportCode() {
            return dptAirportCode;
          }

          public void setDptAirportCode(String dptAirportCode) {
            this.dptAirportCode = dptAirportCode;
          }

          public String getArrAirportCode() {
            return arrAirportCode;
          }

          public void setArrAirportCode(String arrAirportCode) {
            this.arrAirportCode = arrAirportCode;
          }

          public String getDptTerminal() {
            return dptTerminal;
          }

          public void setDptTerminal(String dptTerminal) {
            this.dptTerminal = dptTerminal;
          }

          public String getArrTerminal() {
            return arrTerminal;
          }

          public void setArrTerminal(String arrTerminal) {
            this.arrTerminal = arrTerminal;
          }

          public StopFlightInfoBean getStopFlightInfo() {
            return stopFlightInfo;
          }

          public void setStopFlightInfo(StopFlightInfoBean stopFlightInfo) {
            this.stopFlightInfo = stopFlightInfo;
          }

          public String getStartTime() {
            return startTime;
          }

          public void setStartTime(String startTime) {
            this.startTime = startTime;
          }

          public String getEndTime() {
            return endTime;
          }

          public void setEndTime(String endTime) {
            this.endTime = endTime;
          }

          public String getFlight() {
            return flight;
          }

          public void setFlight(String flight) {
            this.flight = flight;
          }

          public String getFlightNo() {
            return flightNo;
          }

          public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
          }

          public String getFlightType() {
            return flightType;
          }

          public void setFlightType(String flightType) {
            this.flightType = flightType;
          }

          public String getStartPlace() {
            return startPlace;
          }

          public void setStartPlace(String startPlace) {
            this.startPlace = startPlace;
          }

          public String getEndPlace() {
            return endPlace;
          }

          public void setEndPlace(String endPlace) {
            this.endPlace = endPlace;
          }

          public String getCabin() {
            return cabin;
          }

          public void setCabin(String cabin) {
            this.cabin = cabin;
          }

          public String getCabinCode() {
            return cabinCode;
          }

          public void setCabinCode(String cabinCode) {
            this.cabinCode = cabinCode;
          }

          public String getUniqKey() {
            return uniqKey;
          }

          public void setUniqKey(String uniqKey) {
            this.uniqKey = uniqKey;
          }

          public String getCarrier() {
            return carrier;
          }

          public void setCarrier(String carrier) {
            this.carrier = carrier;
          }

          public Object getExtraPrice() {
            return extraPrice;
          }

          public void setExtraPrice(Object extraPrice) {
            this.extraPrice = extraPrice;
          }

          public String getGqFee() {
            return gqFee;
          }

          public void setGqFee(String gqFee) {
            this.gqFee = gqFee;
          }

          public String getUpgradeFee() {
            return upgradeFee;
          }

          public void setUpgradeFee(String upgradeFee) {
            this.upgradeFee = upgradeFee;
          }

          public String getAllFee() {
            return allFee;
          }

          public void setAllFee(String allFee) {
            this.allFee = allFee;
          }

          public String getCabinStatus() {
            return cabinStatus;
          }

          public void setCabinStatus(String cabinStatus) {
            this.cabinStatus = cabinStatus;
          }

          public String getActFlightNo() {
            return actFlightNo;
          }

          public void setActFlightNo(String actFlightNo) {
            this.actFlightNo = actFlightNo;
          }

          public boolean isShare() {
            return share;
          }

          public void setShare(boolean share) {
            this.share = share;
          }

          public String getAdultUFee() {
            return adultUFee;
          }

          public void setAdultUFee(String adultUFee) {
            this.adultUFee = adultUFee;
          }

          public Object getExtraPriceText() {
            return extraPriceText;
          }

          public void setExtraPriceText(Object extraPriceText) {
            this.extraPriceText = extraPriceText;
          }

          public String getGqFeeText() {
            return gqFeeText;
          }

          public void setGqFeeText(String gqFeeText) {
            this.gqFeeText = gqFeeText;
          }

          public String getUpgradeFeeText() {
            return upgradeFeeText;
          }

          public void setUpgradeFeeText(String upgradeFeeText) {
            this.upgradeFeeText = upgradeFeeText;
          }

          public String getAllFeeText() {
            return allFeeText;
          }

          public void setAllFeeText(String allFeeText) {
            this.allFeeText = allFeeText;
          }

          public String getAdultUFeeText() {
            return adultUFeeText;
          }

          public void setAdultUFeeText(String adultUFeeText) {
            this.adultUFeeText = adultUFeeText;
          }

          public Object getCabinStatusText() {
            return cabinStatusText;
          }

          public void setCabinStatusText(Object cabinStatusText) {
            this.cabinStatusText = cabinStatusText;
          }

          public static class StopFlightInfoBean implements Serializable{
            /**
             * stopType : 1
             * stopTypeDesc : 直飞
             * stopCityInfoList : []
             */

            private int stopType;
            private String stopTypeDesc;
            private List<?> stopCityInfoList;

            public int getStopType() {
              return stopType;
            }

            public void setStopType(int stopType) {
              this.stopType = stopType;
            }

            public String getStopTypeDesc() {
              return stopTypeDesc;
            }

            public void setStopTypeDesc(String stopTypeDesc) {
              this.stopTypeDesc = stopTypeDesc;
            }

            public List<?> getStopCityInfoList() {
              return stopCityInfoList;
            }

            public void setStopCityInfoList(List<?> stopCityInfoList) {
              this.stopCityInfoList = stopCityInfoList;
            }
          }
        }
      }
    }
  }
}