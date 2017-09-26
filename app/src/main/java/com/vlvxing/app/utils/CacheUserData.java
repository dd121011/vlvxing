package com.vlvxing.app.utils;

import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.model.UserInfo;

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

public class CacheUserData {

    public static final int recent_subject_list = 8;

    /**
     * 获取文件名
     * @return
     */
    public static String getFileNameById() {
        File file = new File(Constants.CACHE_DIR);
        if (!file.exists())
            file.mkdirs();
//        fname = context.getFilesDir().getPath().toString() + "/screenshot.png";
        String fileName = Constants.CACHE_DIR+"/" + MyApp.getInstance().getUserId() + ".dat";
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
    public static void saveRecentSubList(List<UserInfo> list) {

        String fileName = getFileNameById();
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            List<UserInfo> list1 = getRecentSubList();
            if (list1!=null){
                list.addAll(list1);
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(list);
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

    public static List<UserInfo> getRecentSubList() {
        List<UserInfo> resultList = new ArrayList<>();
        String fileName = getFileNameById();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            ArrayList<UserInfo> list_ext = (ArrayList<UserInfo>) ois.readObject();

            for (UserInfo obj : list_ext) {
                UserInfo bean = obj;
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
