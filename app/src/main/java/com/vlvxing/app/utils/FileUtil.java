package com.vlvxing.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

	private static final String TAG = "FileUtil";

	/**
	 * 获取指定文件大小
	 *
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(String filePath) throws Exception {
		File file = new File(filePath);
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			// file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}

	// 获得音频输入流
	public static InputStream getFileInputStream(String path) {

		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileInputStream;
	}

	//
	public static final String createDirectory(File storageDirectory) {

		if (!storageDirectory.exists()) {
			Log.d(TAG,
					"Trying to create storageDirectory: "
							+ String.valueOf(storageDirectory.mkdirs()));

			Log.d(TAG,
					"Exists: " + storageDirectory + " "
							+ String.valueOf(storageDirectory.exists()));
			Log.d(TAG, "State: " + Environment.getExternalStorageState());
			Log.d(TAG,
					"Isdir: " + storageDirectory + " "
							+ String.valueOf(storageDirectory.isDirectory()));
			Log.d(TAG,
					"Readable: " + storageDirectory + " "
							+ String.valueOf(storageDirectory.canRead()));
			Log.d(TAG,
					"Writable: " + storageDirectory + " "
							+ String.valueOf(storageDirectory.canWrite()));
			File tmp = storageDirectory.getParentFile();
			Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
			Log.d(TAG,
					"Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
			Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
			Log.d(TAG,
					"Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
			tmp = tmp.getParentFile();
			Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
			Log.d(TAG,
					"Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
			Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
			Log.d(TAG,
					"Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
		}

		if (!storageDirectory.isDirectory()) {
			throw new RuntimeException("Unable to create storage directory");
		}

		return storageDirectory.getPath();
	}

	public static byte[] getByteFromUri(Uri uri) {
		InputStream input = getFileInputStream(uri.getPath());
		try {
			int count = 0;
			while (count == 0) {
				count = input.available();
			}

			byte[] bytes = new byte[count];
			input.read(bytes);

			return bytes;
		} catch (Exception e) {
			LogUtils.inner_i("报错了。。。" + e.toString(), true);
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static Uri writeByte(Uri uri, byte[] data) {
		File fileFolder = new File(uri.getPath().substring(0,
				uri.getPath().lastIndexOf("/")));
		fileFolder.mkdirs();
		File file = new File(uri.getPath());
		try {
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					file));
			os.write(data);
			os.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return Uri.fromFile(file);
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	public static void createFile(String name, String dir) {

		File nomediaFile = new File(dir, name);

		if (!nomediaFile.exists()) {
			try {
				Log.d(TAG,
						"Created file: " + nomediaFile + " "
								+ String.valueOf(nomediaFile.createNewFile()));
			} catch (IOException e) {
				Log.d(TAG, "Unable to create .nomedia file for some reason.", e);
				throw new IllegalStateException(
						"Unable to create nomedia file.");
			}
		}
	}

	public static String saveFile(byte[] data, String filepath, Context context) {
		File file = null;
		try {
			file = new File(filepath);
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public static boolean saveImage(Bitmap bitmap, String filepath,
			Context context) {
		boolean bool = false;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		File file = null;
		try {
			file = new File(filepath);
			bos = new BufferedOutputStream(new FileOutputStream(file));
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			bis = new BufferedInputStream(new ByteArrayInputStream(
					baos.toByteArray()));
			int b = -1;
			while ((b = bis.read()) != -1) {
				bos.write(b);
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
			Log.i(context.getPackageName(),
					"the local storage is not available");
		} finally {
			try {
				bos.close();
				bis.close();
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					System.gc();
				}
			} catch (Exception e) {
				bool = false;
				Log.i(context.getPackageName(),
						"the local storage is not available");
			}
		}
		return bool;
	}

	public static String getImageAcachePath(Context context, String filename) {
		String path = context.getFilesDir().getAbsolutePath() + File.separator
				+ "image" + File.separator + "qifude";
		createDirectory(new File(path));
		if (filename == null || "".equals(filename)) {
			return path;
		}
		return path + File.separator + filename;
	}

	public static String getImageExternalFilePath(String filename) {
		String path = "";
		if (SDCardUtils.isSDCardEnable()) {
			path = SDCardUtils.getSDCardPath() + File.separator + "image"
					+ File.separator + "qifude";
		}

		createDirectory(new File(path));

		return path + File.separator + filename;
	}


	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		} finally {
			if (fs != null) {
				try {
					inStream.close();
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 清空文件夹
	 */
	// public static boolean delFile(String strPath) {
	// boolean filebool = false;
	// File file = new File(strPath);
	// if (file.exists() && file.isDirectory()) {
	// if (file.listFiles().length == 0) {
	// file.delete();
	// } else {
	// File[] ff = file.listFiles();
	// for (int i = 0; i < ff.length; i++) {
	// if (ff[i].isDirectory()) {
	// delFile(strPath);
	// }
	// ff[i].delete();
	//
	// }
	//
	// }
	// }
	// // file.delete();// 如果要把文件夹也删除。。就去掉注释。。
	// filebool = true;
	// return filebool;
	//
	// }

	/**
	 * 判断apk是否是完整并可以正常安装的apk包
	 * */
	public static boolean getUninatllApkInfo(Context context, String filePath) {
		boolean result = false;
		try {
			PackageManager pm = context.getPackageManager();
			Log.e("archiveFilePath", filePath);
			PackageInfo info = pm.getPackageArchiveInfo(filePath,
					PackageManager.GET_ACTIVITIES);
			if (info != null) {
				result = true;// 完整
			}
		} catch (Exception e) {
			result = false;// 不完整
		}
		return result;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete();
			else if (file.isDirectory())
				deleteDir();
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static String getPhotopath() {
        // 照片全路径  
        String fileName = "";
        // 文件夹路径  
        String pathUrl = Environment.getExternalStorageDirectory()+"/image/qifude/";
        String imageName = "imageTest.jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹  
        fileName = pathUrl + imageName;
        return fileName;
    }


	/**
	 * 将Bitmap 图片保存到本地路径，并返回路径
	 * @param c
	 * @param fileName 文件名称
	 * @param bitmap 图片
	 * @return
	 */
	public static String saveFile(Context c, String fileName, Bitmap bitmap) {
		return saveFile(c, "", fileName, bitmap);
	}

	public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
		byte[] bytes = bitmapToBytes(bitmap);
		return saveFile(c, filePath, fileName, bytes);
	}
	/**
	 * 将图片转换为byte字节流，用来给上传图片用的
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
		String fileFullName = "";
		FileOutputStream fos = null;
		String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date());
		try {
			String suffix = "";
			if (filePath == null || filePath.trim().length() == 0) {
//				filePath = Environment.getExternalStorageDirectory() + "/SmartWFJ/" + dateFolder + "/";
				filePath = c.getCacheDir() + "/SmartWFJ/" + dateFolder + "/";
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			File fullFile = new File(filePath, fileName + suffix);
			fileFullName = fullFile.getPath();
			fos = new FileOutputStream(new File(filePath, fileName + suffix));
			fos.write(bytes);
		} catch (Exception e) {
			fileFullName = "";
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					fileFullName = "";
				}
			}
		}
		return fileFullName;
	}

}
