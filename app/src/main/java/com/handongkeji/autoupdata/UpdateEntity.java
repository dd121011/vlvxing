package com.handongkeji.autoupdata;

import com.handongkeji.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sangbo on 16-5-19.
 */
public class UpdateEntity {

    public int versionCode = 0;
    public String isForceUpdate = "0";
    public String preBaselineCode = "0";
    public String versionName = "";
    public String downUrl = "";
    public String updateLog = "";
//    public String md5 = "";


    public UpdateEntity(String json) throws JSONException {

        JSONObject jso = new JSONObject(json);
        JSONObject jsonObject = jso.getJSONObject("data");
        String code = jsonObject.getString("versionCode");
        if (!StringUtils.isStringNull(code)) {
			this.versionCode = Integer.valueOf(code);
		}else{
			this.versionCode = 0;
		}
        this.versionName = jsonObject.getString("versionName");
        this.isForceUpdate = jsonObject.getString("isForceUpdate");
        this.downUrl = jsonObject.getString("downUrl");
        this.preBaselineCode = jsonObject.getString("preBaselineCode");
        this.updateLog = jsonObject.getString("updateLog");
//        this.md5 = jsonObject.getString("md5");

    }
}
