package com.vlvxing.app.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 内存卡 工具类<br>
 * 内部已经封装了打印功能,只需要把DEBUG参数改为true即可<br>
 * 如果需要更换tag可以直接更改,默认为KEZHUANG
 * 
 * @author KEZHUANG
 * 
 */
public class SDCardUtils {
	/**
	 * Log的开关<br>
	 * true为开启<br>
	 * false为关闭<br>
	 */
	public static boolean DEBUG = GlobalTools.DEFAULT_DEBUG;

	/**
	 * Log 输出标签
	 */
	public static String TAG = GlobalTools.DEFAULT_TAG;

	/**
	 * 判断SDCard是否可用
	 * 
	 */
	public static boolean isSDCardEnable() {
		boolean result = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (result)
			LogUtils.inner_i("当前内存卡有效", DEBUG);
		else
			LogUtils.inner_i("当前内存卡无效", DEBUG);

		return result;

	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		LogUtils.inner_i("当前内存卡路径" + path, DEBUG);
		return path;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			@SuppressWarnings("deprecation")
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			@SuppressWarnings("deprecation")
			long freeBlocks = stat.getAvailableBlocks();
			long result = freeBlocks * availableBlocks;
			LogUtils.inner_i("当前内存卡的容量：" + result, DEBUG);
			return result;
		}
		return 0;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath() {
		String path = Environment.getRootDirectory().getAbsolutePath();
		LogUtils.inner_i("当前存储路径：" + path, DEBUG);
		return path;
	}

}
