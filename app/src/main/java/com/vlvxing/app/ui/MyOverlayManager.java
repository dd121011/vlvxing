package com.vlvxing.app.ui;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.handongkeji.utils.OverlayManager;

import java.util.ArrayList;
import java.util.List;


public class MyOverlayManager extends OverlayManager {

    private List<OverlayOptions> optionsList = new ArrayList<OverlayOptions>();

    public MyOverlayManager(BaiduMap baiduMap) {
        super(baiduMap);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        // TODO Auto-generated method stub
        return optionsList;
    }

    public void setData(List<OverlayOptions> optionsList) {
        this.optionsList = optionsList;
    }
}

