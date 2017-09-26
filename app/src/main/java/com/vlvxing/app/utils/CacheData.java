package com.vlvxing.app.utils;

import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.RecordModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class CacheData {

    public static final int recent_subject_list = 8;

    /**
     * 获取文件名
     *
     * @param type :数据类型 标注点  轨迹
     * @return
     */
    public static String getFileNameById(String type) {
        File file = new File(Constants.CACHE_DIR);
        if (!file.exists())
            file.mkdirs();
        if ("1".equals(type)) {
            deleteFilesByDirectory(file);
        }
//        fname = context.getFilesDir().getPath().toString() + "/screenshot.png";
        String fileName = Constants.CACHE_DIR + "/" + type + MyApp.getInstance().getUserId() + ".dat";
        return fileName;
    }


    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 存储缓存文件：
     */
    public static void saveRecentSubList(List<RecordModel> list, String type) {

        String fileName = getFileNameById(type);
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            List<RecordModel> list1 = getRecentSubList(type);
            if (list1 != null) {
                list1.addAll(list);
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(list1);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取缓存文件：
     */

    public static List<RecordModel> getRecentSubList(String type) {
        List<RecordModel> resultList = new ArrayList<>();
        String fileName = getFileNameById(type);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            ArrayList<RecordModel> list_ext = (ArrayList<RecordModel>) ois.readObject();

            for (RecordModel obj : list_ext) {
                RecordModel bean = obj;
                if (bean != null) {
                    resultList.add(bean);
                }
            }
            ois.close();
        } catch (Exception e) {
            return resultList;
        }
        return resultList;
    }
}
