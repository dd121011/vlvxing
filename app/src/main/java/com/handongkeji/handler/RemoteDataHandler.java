/**
/ *  ClassName: TopicsDataHandler.java
 *  created on 2012-3-3
 *  Copyrights 2011-2012 qjyong All rights reserved.
 *  site: http://blog.csdn.net/qjyong
 *  email: qjyong@gmail.com
 */
package com.handongkeji.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.handongkeji.common.HttpHelper;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.OtherLoginActivity;
import com.handongkeji.utils.ConfigCacheUtil;
import com.handongkeji.widget.NetUtils;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 用于发送HTTP请求并处理响应返回的数据的Handler
 * 
 * @author qjyong
 */
public class RemoteDataHandler {
	public static final String TAG = "RemoteDataLoader";
	private static final String _CODE = "status";
	private static final String _DATAS = "Json";
	private static final String _HASMORE = "haveMore";
	private static final String _COUNT = "count";
	private static final String _RESULT = "result";
	private static Handler mhandler;
	// private static final String _URL = "url";
	// 线程池
//	 private ExecutorService pool = Executors.newCachedThreadPool();
	//LinkedBlockingQueue 链表实现的阻塞队列(线程安全)
	public static ThreadPoolExecutor THREADPOOL = new ThreadPoolExecutor(0, 100, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	private RemoteDataHandler() {
	}

	public interface Callback {
		/**
		 * HTTP响应完成的回调方法
		 * 
		 * @param data
		 *            响应返回的数据对象
		 */
		public void dataLoaded(ResponseData data) throws JSONException;
	}

	public interface StringCallback {
		public void dataLoaded(String str);
	}

	/**
	 * 异步GET请求封装
	 * 
	 * @param url
	 * @param callback
	 */
	public static void asyncGet(final String url, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));
				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean(_HASMORE, false);

				try {
					String json = HttpHelper.get(url);
					// 注意:目前服务器返回的JSON数据串中会有特殊字符（换行、回车）。需要处理一下
					json = json.replaceAll("\\x0a|\\x0d", "");
					JSONObject obj = new JSONObject(json);
					if (null != obj && obj.has(_CODE)) {
						msg.what = Integer.valueOf(obj.getString(_CODE));
						if (obj.has(_DATAS)) {
							JSONArray array = obj.getJSONArray(_DATAS);
							msg.obj = array.toString();
						}
						if (obj.has(_HASMORE)) {
							msg.getData().putBoolean(_HASMORE, obj.getBoolean(_HASMORE));
						}
						if (obj.has(_COUNT)) {
							msg.getData().putLong(_COUNT, obj.getLong(_COUNT));
						}

						if (obj.has(_RESULT)) {
							msg.getData().putString(_RESULT, obj.getString(_RESULT));
						}
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * 异步GET请求封装
	 * 
	 * @param url
	 * @param callback
	 */
	public static void asyncGet2(final String url, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean(_HASMORE, false);

				try {
					String json = HttpHelper.get(url);
					// 注意:目前服务器返回的JSON数据串中会有特殊字符（换行、回车）。需要处理一下
					json = json.replaceAll("\\x0a|\\x0d", "");
					json = json.replaceAll("\\\\t", "");
					json = json.replaceAll("\\\\n", "");
					json = json.replaceAll("\\\\r", "");
					json = json.replaceAll("<br\\\\/>", "");
					JSONObject obj = new JSONObject(json);
					if (null != obj && obj.has(_CODE)) {
						msg.what = Integer.valueOf(obj.getString(_CODE));

						if (obj.has(_DATAS)) {
							// JSONArray array = obj.getJSONArray(_DATAS);
							msg.obj = obj.getJSONObject(_DATAS).toString();
						}
						if (obj.has(_HASMORE)) {
							msg.getData().putBoolean(_HASMORE, obj.getBoolean(_HASMORE));
						}
						if (obj.has(_COUNT)) {
							msg.getData().putLong(_COUNT, obj.getLong(_COUNT));
						}

						if (obj.has(_RESULT)) {
							msg.getData().putString(_RESULT, obj.getString(_RESULT));
						}
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * 异步GET请求封装
	 * 
	 * @param url
	 * @param callback
	 */
	public static void asyncGet3(final String url, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean(_HASMORE, false);

				try {
					String json = HttpHelper.get(url);
					// 注意:目前服务器返回的JSON数据串中会有特殊字符（换行、回车）。需要处理一下
					json = json.replaceAll("\\x0a|\\x0d", "");

					JSONObject obj = new JSONObject(json);
					if (null != obj && obj.has(_CODE)) {
						msg.what = Integer.valueOf(obj.getString(_CODE));

						if (obj.has(_DATAS)) {
							// JSONArray array = obj.getJSONArray(_DATAS);
							msg.obj = obj.getString(_DATAS).toString();
						}
						if (obj.has(_HASMORE)) {
							msg.getData().putBoolean(_HASMORE, obj.getBoolean(_HASMORE));
						}
						if (obj.has(_COUNT)) {
							msg.getData().putLong(_COUNT, obj.getLong(_COUNT));
						}

						if (obj.has(_RESULT)) {
							msg.getData().putString(_RESULT, obj.getString(_RESULT));
						}
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * 异步GET请求封装
	 * 
	 * @param url
	 * @param callback
	 */
	public static void asyncCountGet(final String url, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setJson((String) msg.obj);
				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				Log.d(TAG, url);
				try {
					String json = HttpHelper.get(url);

					// 注意:目前服务器返回的JSON数据串中会有特殊字符（换行、回车）。需要处理一下
					json = json.replaceAll("\\x0a|\\x0d", "");
					msg.obj = json;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * 异步get分页数据请求封装
	 * 
	 * @param url
	 * @param pagesize
	 * @param pageno
	 * @param callback
	 */
	public static void asyncGet(final String url, final int pagesize, final int pageno, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {

				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean(_HASMORE, false);

				String realUrl = "";// url + "&" + Constants.PARAM_PAGESIZE +
									// "=" + pagesize
				// + "&" + Constants.PARAM_PAGENO + "=" + pageno;

				try {
					Thread.sleep(1000);

					String json = HttpHelper.get(realUrl);

					// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
					json = json.replaceAll("\\x0a|\\x0d", "");

					JSONObject obj = new JSONObject(json);
					if (null != obj && obj.has(_CODE)) {
						msg.what = Integer.valueOf(obj.getString(_CODE));

						if (obj.has(_DATAS)) {
							JSONArray array = obj.getJSONArray(_DATAS);
							msg.obj = array.toString();

							if (pagesize == array.length()) {
								msg.getData().putBoolean(_HASMORE, true);
							}
						}
						if (obj.has(_COUNT)) {
							msg.getData().putLong(_COUNT, Long.valueOf(obj.getString(_COUNT)));
						}

						if (obj.has(_RESULT)) {
							msg.getData().putString(_RESULT, obj.getString(_RESULT));
						}
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * 同步GET请求封装
	 * 
	 * @param url
	 * @param
	 * @param
	 * @return
	 */
	public static ResponseData get(final String url) {
		ResponseData rd = new ResponseData();

		try {
			String json = HttpHelper.get(url);

			// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
			json = json.replaceAll("\\x0a|\\x0d", "");

			JSONObject obj = new JSONObject(json);
			if (null != obj && obj.has(_CODE)) {
				rd.setCode(obj.getInt(_CODE));

				if (obj.has(_DATAS)) {
					JSONArray array = obj.getJSONArray(_DATAS);
					rd.setJson(array.toString());
				}
				if (obj.has(_HASMORE)) {
					rd.setHasMore(obj.getBoolean(_HASMORE));
				}

				if (obj.has(_RESULT)) {
					rd.setResult(obj.getString(_RESULT));
				}

				if (obj.has(_COUNT)) {
					rd.setCount(obj.getLong(_COUNT));
				}
			}
		} catch (IOException e) {
			rd.setCode(HttpStatus.SC_REQUEST_TIMEOUT);
			e.printStackTrace();
		} catch (JSONException e) {
			rd.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			rd.setCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}

		return rd;
	}

	/**
	 * 同步GET请求封装
	 * 
	 * @param url
	 * @param pagesize
	 * @param pageno
	 * @return
	 */
	public static ResponseData get(final String url, final int pagesize, final int pageno) {
		ResponseData rd = new ResponseData();

		String realUrl = "";// url + "&" + Constants.PARAM_PAGESIZE + "=" +
							// pagesize
		// + "&" + Constants.PARAM_PAGENO + "=" + pageno;

		try {
			String json = HttpHelper.get(realUrl);

			// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
			json = json.replaceAll("\\x0a|\\x0d", "");

			JSONObject obj = new JSONObject(json);
			if (null != obj && obj.has(_CODE)) {
				rd.setCode(obj.getInt(_CODE));

				if (obj.has(_DATAS)) {
					JSONArray array = obj.getJSONArray(_DATAS);
					rd.setJson(array.toString());

					if (pagesize == array.length()) {
						rd.setHasMore(true);
					}
				}
				if (obj.has(_HASMORE)) {
					rd.setHasMore(obj.getBoolean(_HASMORE));
				}

				if (obj.has(_RESULT)) {
					rd.setResult(obj.getString(_RESULT));
				}

				if (obj.has(_COUNT)) {
					rd.setCount(obj.getLong(_COUNT));
				}
			}
		} catch (IOException e) {
			rd.setCode(HttpStatus.SC_REQUEST_TIMEOUT);
			e.printStackTrace();
		} catch (JSONException e) {
			rd.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			rd.setCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}

		return rd;
	}

	public static ResponseData post(final String url, final HashMap<String, String> params) {
		ResponseData rd = new ResponseData();
		try {
			String json = HttpHelper.post(url, params);

			// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
			json = json.replaceAll("\\x0a|\\x0d", "");

			JSONObject obj = new JSONObject(json);
			if (null != obj && obj.has(_CODE)) {
				rd.setCode(obj.getInt(_CODE));

				if (obj.has(_DATAS)) {
					JSONArray array = obj.getJSONArray(_DATAS);
					rd.setJson(array.toString());
				}
				if (obj.has(_HASMORE)) {
					rd.setHasMore(obj.getBoolean(_HASMORE));
				}

				if (obj.has(_RESULT)) {
					rd.setResult(obj.getString(_RESULT));
				}

				if (obj.has(_COUNT)) {
					rd.setCount(obj.getLong(_COUNT));
				}
			}
		} catch (IOException e) {
			rd.setCode(HttpStatus.SC_REQUEST_TIMEOUT);
			e.printStackTrace();
		} catch (JSONException e) {
			rd.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			rd.setCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}

		return rd;
	}

	public static ResponseData LonginPost(final String url, final HashMap<String, String> params) {
		ResponseData rd = new ResponseData();
		try {
			String json = HttpHelper.post(url, params);
			// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
			if (json != null && !json.equals("")) {
				json = json.replaceAll("\\x0a|\\x0d", "");
				JSONObject obj = new JSONObject(json);
				if (null != obj && obj.has(_CODE)) {
					rd.setCode(obj.getInt(_CODE));

					if (obj.has(_DATAS)) {
						rd.setJson(obj.getString(_DATAS));
					}
					if (obj.has(_HASMORE)) {
						rd.setHasMore(obj.getBoolean(_HASMORE));
					}

					if (obj.has(_RESULT)) {
						rd.setResult(obj.getString(_RESULT));
					}

					if (obj.has(_COUNT)) {
						rd.setCount(obj.getLong(_COUNT));
					}
				}
			} else {
				rd.setCode(HttpStatus.SC_ACCEPTED);
			}
		} catch (IOException e) {
			rd.setCode(HttpStatus.SC_REQUEST_TIMEOUT);
			e.printStackTrace();
		} catch (JSONException e) {
			rd.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			rd.setCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}

		return rd;
	}

	/**
	 * 异步的POST请求登录
	 * 
	 * @param url
	 * @param params
	 * @param callback
	 */
	public static void asyncLoginPost(final String url, final HashMap<String, String> params, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean("hasMore", false);
				try {
					String json = HttpHelper.post(url, params);
					if (json != null && !json.equals("")) {
						// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
						json = json.replaceAll("\\x0a|\\x0d", "");
						JSONObject obj = new JSONObject(json);
						if (null != obj && obj.has(_CODE)) {
							msg.what = Integer.valueOf(obj.getString(_CODE));
							msg.obj = json;
							// if(obj.has(_DATAS)){
							// //JSONObject array = obj.getJSONObject(_DATAS);
							// msg.obj = obj.getString(_DATAS).toString();
							// }
							if (obj.has(_RESULT)) {
								msg.getData().putString(_RESULT, obj.getString(_RESULT));
							}
						}
					} else {
						msg.what = HttpStatus.SC_ACCEPTED;
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}
		});
	}

	/**
	 *
	 * @Description 异步的Get请求
	 * @Create On 2015-12-9上午9:49:07
	 * @Site http://www.handongkeji.com
	 * @author chaiqs
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param context
	 *            上下文对象
	 *            是否缓存 true or false
	 * @param callback
	 *            回调
	 * @Copyrights 2015-12-9 handongkeji All rights reserved.
	 */
	public static void asyncPlaneGet(final String url, final HashMap<String, Object> params, final Context context, final Callback callback) {
		String pkey = "";
		if(params!=null){
			for (String key : params.keySet()) {
				pkey+=key+"="+params.get(key)+"&";
			}
		}
		final String ukey = url+"?"+pkey;
		System.out.println("接口  pkey "+ pkey);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
//				data.setCode(msg.getData().getInt(_CODE));
				data.setJson((String) msg.obj);
				System.out.println("改签申请  data "+ data.getJson());

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				try {
					System.out.println("booking  ukey "+ ukey);
					String json = HttpHelper.get2(ukey);
					System.out.println("改签申请  json "+ json);
						if (json != null) {
							json = json.replaceAll("\\x0a|\\x0d|", "");
//							json = json.replaceAll("null","\"\"");
							msg.obj = json;
							// 在此处添加缓存
						}
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}
	/**
	 *
	 * @Description 异步的Get请求
	 * @Create On 2015-12-9上午9:49:07
	 * @Site http://www.handongkeji.com
	 * @author chaiqs
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param context
	 *            上下文对象
	 *            是否缓存 true or false
	 * @param callback
	 *            回调
	 * @Copyrights 2015-12-9 handongkeji All rights reserved.
	 */
	public static void asyncPlanePost(final String url, final HashMap<String, String> params, final Context context, final Callback callback) {
		String pkey = "";
		if(params!=null){
			for (String key : params.keySet()) {
				pkey+=key+"="+params.get(key)+"&";
			}
		}
		final String ukey = url+"?"+pkey;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setJson((String) msg.obj);
				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				try {
					String json = HttpHelper.post(url, params);
					if (json != null) {
						json = json.replaceAll("\\x0a|\\x0d|", "");
//							json = json.replaceAll("null","\"\"");
						msg.obj = json;
						// 在此处添加缓存
					}
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}



	/**
	 * 
	 * @Description 异步的POST请求
	 * @Create On 2015-12-9上午9:49:07
	 * @Site http://www.handongkeji.com
	 * @author chaiqs
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param context
	 *            上下文对象
	 * @param cache
	 *            是否缓存 true or false
	 * @param callback
	 *            回调
	 * @Copyrights 2015-12-9 handongkeji All rights reserved.
	 */
	public static void asyncPost(final String url, final HashMap<String, String> params, final Context context, final boolean cache, final Callback callback) {
		String pkey = "";
		if(params!=null){
			for (String key : params.keySet()) {
				pkey+=key+"="+params.get(key)+"&";
			}
		}
		final String ukey = url+"?"+pkey;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				// 如果是选择缓存，则从缓存中读取json数据并返回 TODO
				if (cache) {
					String json = ConfigCacheUtil.getUrlCache(ukey, ConfigCacheUtil.ConfigCacheModel.CONFIG_CACHE_MODEL_SO_SHORT);
					if (!"".equals(json) && json != null) {
						data.setJson(json);
					} else {
						data.setJson((String) msg.obj);
					}

				} else {
					data.setJson((String) msg.obj);
				}
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean("hasMore", false);
				try {
					boolean isNetFlag = NetUtils.isNet(context);
					if (!isNetFlag && cache) {
						String json = ConfigCacheUtil.getUrlCache(ukey, ConfigCacheUtil.ConfigCacheModel.CONFIG_CACHE_MODEL_SO_SHORT);
						msg.obj = json;
						handler.sendMessage(msg);
					} else {
						String json = HttpHelper.post(url, params);
						// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
						if (json != null) {
							json = json.replaceAll("\\x0a|\\x0d|", "");
//							json = json.replaceAll("null","\"\"");
							msg.obj = json;
							// 在此处添加缓存
							ConfigCacheUtil.setUrlCache(ukey, json);
						}
						handler.sendMessage(msg);
					}

				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 
	 * @Description 异步的POST带Token请求
	 * @Create On 2015-12-9上午9:49:07
	 * @Site http://www.handongkeji.com
	 * @author wmm
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param context
	 *            上下文对象
	 * @param cache
	 *            是否缓存 true or false
	 * @param callback
	 *            回调
	 * @Copyrights 2015-12-9 handongkeji All rights reserved.
	 */
	public static void asyncTokenPost(final String url, final Context context, final boolean cache, final HashMap<String, String> params, final Callback callback) {
		String pkey = "";
		if(params!=null){
			for (String key : params.keySet()) {
				pkey+=key+"="+params.get(key)+"&";
			}
		}
		final String ukey = url+"?"+pkey;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				// 如果是选择缓存，则从缓存中读取json数据并返回 TODO
				if (cache) {
					String json = ConfigCacheUtil.getUrlCache(ukey, ConfigCacheUtil.ConfigCacheModel.CONFIG_CACHE_MODEL_SO_SHORT);
					if (!"".equals(json) && json != null) {
						data.setJson(json);
					} else {
						data.setJson((String) msg.obj);
					}
				} else {
					data.setJson((String) msg.obj);
				}
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));
				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				OtherLoginActivity.OtherLogin(context,msg.obj.toString());
				super.handleMessage(msg);
			};
		};

		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean("hasMore", false);
				try {
					boolean isNetFlag = NetUtils.isNet(context);
					if (!isNetFlag && cache) {
						String json = ConfigCacheUtil.getUrlCache(ukey, ConfigCacheUtil.ConfigCacheModel.CONFIG_CACHE_MODEL_SO_SHORT);
						msg.obj = json;
					} else {
						String json = HttpHelper.post(url, params);
						// 注意:目前服务器返回的JSON数据串中会有特殊字符（如换行）。需要处理一下
						if (json != null) {
							json = json.replaceAll("\\x0a|\\x0d", "");
							JSONObject jso = new JSONObject(json);
							String status = jso.getString("status");
							String message = jso.getString("message");
							if ("602".equals(status)) {
								JSONObject dat = jso.getJSONObject("data");
								String currentip = dat.getString("currentip");//ip
								String apptype = dat.getString("apptype");//1安卓2ios
								if(apptype.equals("1")){
									apptype="安卓";
								}else {
									apptype="苹果";
								}
								Message newMessage = new Message();
								newMessage.obj = message+"  ip:"+currentip+",设备:"+apptype;
								mhandler.sendMessage(newMessage);
							} else {
								msg.obj = json;
								// 在此处添加缓存
								ConfigCacheUtil.setUrlCache(ukey, json);
							}

						}
					}
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = HttpStatus.SC_INTERNAL_SERVER_ERROR;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * 异步的多消息体POST请求封装
	 * 
	 * @param url
	 * @param params
	 * @param fileMap
	 * @param callback
	 */
	public static void asyncMultipartPost(final String url, final HashMap<String, String> params, final HashMap<String, File> fileMap, final Callback callback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseData data = new ResponseData();
				data.setCode(msg.what);
				data.setHasMore(msg.getData().getBoolean(_HASMORE));
				data.setJson((String) msg.obj);
				data.setResult(msg.getData().getString(_RESULT));
				data.setCount(msg.getData().getLong(_COUNT));

				try {
					callback.dataLoaded(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		THREADPOOL.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage(HttpStatus.SC_OK);
				msg.getData().putBoolean("hasMore", false);

				try {
					System.out.println("上传图片   url:"+url);
					String json = HttpHelper.multipartPost(url, params, fileMap);
					if (json != null) {
						json = json.replaceAll("\\x0a|\\x0d", "");
						msg.obj = json;
						System.out.println("上传图片   json:"+json);
					}
					handler.sendMessage(msg);
					System.out.println("上传图片   msg:"+msg);
				} catch (IOException e) {
					msg.what = HttpStatus.SC_REQUEST_TIMEOUT;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msg.what = HttpStatus.SC_SERVICE_UNAVAILABLE;
					e.printStackTrace();
				}

			}
		});
	}
}
