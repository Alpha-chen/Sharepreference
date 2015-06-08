package com.cachetest.xpg;

//**  * 文 件 名:  DataCleanManager.java  * 描    述:  主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录  */

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/** * 本应用数据清除管理器 */
public class ClearCache {

	/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
		Log.d(context.toString(), "要删除的cache的目录："
				+ context.getCacheDir().toString());
	}

	/** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
		Log.d(context.toString(),
				"要删除本地数据库文件目录：" + "/data/data/" + context.getPackageName()
						+ "/databases");
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
	 * context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
		Log.d(context.toString(), "要删除本地SharedPreference：" + "/data/data/"
				+ context.getPackageName() + "/shared_prefs");
	}

	/** * 按名字清除本应用数据库 * * @param context * @param dbName */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
		Log.d(context.toString(), "按名字清除本应用数据库：" + dbName.toString());
	}

	/** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
		Log.d(context.toString(), "清除/data/data/com.xxx.xxx/files"
				+ context.getFilesDir().toString());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
	 * context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			deleteFilesByDirectory(context.getExternalCacheDir());
			Log.d(context.toString(), "清除外部cache下的内容:"
					+ context.getExternalCacheDir().toString());
		}
	}

	/** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
		Log.d(filePath.toString(), "清除自定义路径下的文件：" + filePath.toString());
	}

	/** * 清除本应用所有的数据 * * @param context * @param filepath */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	/**
	 * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，<br>
	 * 否则就调用getCacheDir() 方法来获取缓存路径。 <br>
	 * 前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个路径， <br>
	 * 而后者获取到的是 /data/data/<application package>/cache 这个路径。
	 * 
	 * @Title: getDiskCacheDir
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param context
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @author AT xpg
	 */
	public String getDiskCacheDir(Context context) {
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}
}