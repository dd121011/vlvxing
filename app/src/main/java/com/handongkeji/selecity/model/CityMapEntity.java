package com.handongkeji.selecity.model;

import java.util.HashMap;

public class CityMapEntity {

    private HashMap<Integer, SortModel> cityHashMap;

    public HashMap<Integer, SortModel> getCityHashMap() {
	return cityHashMap;
    }

    public void setCityHashMap(HashMap<Integer, SortModel> cityHashMap) {
	this.cityHashMap = cityHashMap;
    }
}
