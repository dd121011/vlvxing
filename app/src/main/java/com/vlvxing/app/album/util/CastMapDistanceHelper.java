package com.vlvxing.app.album.util;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.vlvxing.app.common.ItineraryObject;


import java.util.List;

public class CastMapDistanceHelper {
	
	/**
	 * 计算终点跟起点的距离 
	 * @return
	 */
	public static  double castDoubleDistance(List<ItineraryObject> ll) {
		// TODO Auto-generated method stub
		double distance = 0 ;
		for(int i=0;i<ll.size()-1;i++){
			LatLng startLatLng = new LatLng(ll.get(i).getMyLat(), ll.get(i).getMyLng());
			LatLng endLatLng = new LatLng(ll.get(i+1).getMyLat(), ll.get(i+1).getMyLng());
			distance = distance + DistanceUtil.getDistance(startLatLng,endLatLng);
		}
		return distance + 0.01;
	}

}
