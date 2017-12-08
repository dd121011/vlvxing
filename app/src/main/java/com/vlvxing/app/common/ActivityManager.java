package com.vlvxing.app.common;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/7.
 */
public class ActivityManager {

    private static ActivityManager mInstance;
    private HashMap<String,WeakReference<Activity>> activityHashMap;
    private LinkedList<String> componentNames;

    private ActivityManager(){
        activityHashMap = new HashMap<>();
        componentNames = new LinkedList<>();
    }

    public static ActivityManager getInstance(){
        if (mInstance == null) {
            synchronized (ActivityManager.class){
                if (mInstance == null) {
                    ActivityManager activityManager = new ActivityManager();
                    mInstance = activityManager;
                }
            }
        }
        return mInstance;
    }

    public void addActivity(String componentName, Activity activity){
        WeakReference<Activity> wa = new WeakReference<Activity>(activity);
        this.activityHashMap.put(componentName,wa);
        this.componentNames.add(componentName);
    }

    public void removeActivity(String componentName){
        this.activityHashMap.remove(componentName);
        this.componentNames.remove(componentName);
    }

    public Activity getLastActivity(){
        if (componentNames.size()  > 0){
            Iterator<String> iterator = componentNames.descendingIterator();
            while (iterator.hasNext()){
                String previous = iterator.next();
                WeakReference<Activity> activityWeakReference = activityHashMap.get(previous);
                Activity activity = activityWeakReference.get();
                if (activity == null){
                    iterator.remove();
                    activityHashMap.remove(previous);
                }else{
                    return activity;
                }
            }
        }
        return null;
    }

    public void destoryAll(){
        for (Map.Entry<String, WeakReference<Activity>> entry : activityHashMap.entrySet()) {
            WeakReference<Activity> value = entry.getValue();
            Activity activity = value.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
    public void finishOthers(String key){
        if (key == null){
            throw new RuntimeException();
        }
        Iterator<Map.Entry<String, WeakReference<Activity>>> iterator = activityHashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WeakReference<Activity>> next = iterator.next();
            if (next.getKey().equals(key)){
                continue;
            }
            iterator.remove();
            Activity activity = next.getValue().get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
    public Map<String, WeakReference<Activity>> getActivities(){
        return activityHashMap;
    }


}
