package com.leo.base.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.leo.base.application.LApplication;
import com.leo.base.application.LConfig;
import com.leo.base.cache.LCache;
import com.leo.base.entity.LReqEncode;
import com.leo.base.entity.LReqFile;
import com.leo.base.entity.LReqMothed;
import com.leo.base.exception.LException;
import com.leo.base.util.L;
import com.leo.base.util.LFormat;
import com.leo.base.util.MD5;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public class LCaller {
	private static LCache<String> cache = null;

	private static final String RUNTIME_EXCEPTION = "网络请求失败";

	private static final String SEND_ERROR = "ERROR=0";

	/**
	 * 禁止实例化
	 */
	private LCaller() {
	}

	private static final BasicHttpParams getClientParams() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,
				LConfig.REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, LConfig.SO_TIMEOUT);
		return httpParams;
	}

	/**
	 * 网络请求方法
	 * 
	 * @param url
	 *            ：请求地址
	 * @param params
	 *            ：请求参数
	 * @param useCache
	 *            ：是否启用缓存
	 * @param mothed
	 *            ：请求方式{@link LReqMothed}
	 * @param encoding
	 *            ：请求编码{@link LReqEncode}
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @return：String类型请求结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doConn(String url, Map<String, String> params,
			boolean useCache, LReqMothed mothed, LReqEncode encoding)
			throws Exception {

		if (TextUtils.isEmpty(url)) {
			throw new NullPointerException("网络请求地址不能为空");
		}
		String data = null;
		String encode = LReqEncode.UTF8.getEncode();
		if (encoding != null) {
			encode = encoding.getEncode();
		}
		if (mothed == null) {
			if (params == null) {
				data = doGet(url, useCache, encode);
			} else {
				data = doPost(url, params, useCache, encode);
			}
		} else {
			if (mothed == LReqMothed.POST) {
				data = doPost(url, params, useCache, encode);
			} else if (mothed == LReqMothed.GET) {
				data = doGet(url, useCache, encode);
			} else {
				throw new IllegalArgumentException("请求方式参数错误");
			}
		}
		return data;
	}

	/**
	 * 返回缓存
	 * 
	 * @param url
	 * @return
	 */
	private static String doGetCache(String url) {
		String data = null;
		if (cache != null)
			data = cache.get(url);
		return data;
	}

	/**
	 * 添加缓存
	 * 
	 * @param url
	 * @param value
	 */
	private static void doSetCache(String url, String value) {
		if (cache == null)
			cache = new LCache<String>();
		cache.put(url, value);
	}

	/**
	 * post 方式请求http
	 * 
	 * @param url
	 *            ：路径
	 * @param params
	 *            ：参数
	 * @param useCache
	 *            ：是否使用缓存
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	private static String doPost(String url, Map<String, String> params,
			boolean useCache, String encoding) throws Exception {
		String data = null;
		if (useCache) {
			data = doGetCache(url);
			if (!LFormat.isEmpty(data)) {
				return data;
			}
		}
		HttpPost post = new HttpPost(url);
		if (params != null) {
			List<NameValuePair> lParams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				lParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			post.setEntity(new UrlEncodedFormEntity(lParams, encoding));
		}
		if (!LFormat.isEmpty(LApplication.getInstance().getSessionValue())) {
			post.setHeader(LApplication.getInstance().getSessionKey(),
					LApplication.getInstance().getSessionValue());
		}
		DefaultHttpClient httpClient = new DefaultHttpClient(getClientParams());
		HttpResponse httpResponse = httpClient.execute(post);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			data = EntityUtils.toString(httpResponse.getEntity());
			if (LFormat.isEmpty(data))
				throw new ConnectException(RUNTIME_EXCEPTION);
			data = LFormat.JSONTokener(data);
			Header[] headers = httpResponse.getHeaders(LApplication
					.getInstance().getSessionKey());
			if (headers.length == 1) {
				LApplication.getInstance().setSessionValue(
						headers[0].getValue());
			}
		} else {
			throw new ConnectException(RUNTIME_EXCEPTION);
		}
		if (useCache && !LFormat.isEqual(SEND_ERROR, data)) {
			doSetCache(url, data);
		}
		return data;
	}

	/**
	 * get方式请求http
	 * 
	 * @param url
	 *            ：路径
	 * @param useCache
	 *            ：是否使用缓存
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	private static String doGet(String url, boolean useCache, String encoding)
			throws Exception {
		String data = null;
		if (useCache) {
			data = doGetCache(url);
			if (!LFormat.isEmpty(data)) {
				return data;
			}
		}
		DefaultHttpClient httpClient = new DefaultHttpClient(getClientParams());
		HttpGet httpGet = new HttpGet(url);
		if (!LFormat.isEmpty(LApplication.getInstance().getSessionValue())) {
			httpGet.setHeader(LApplication.getInstance().getSessionKey(),
					LApplication.getInstance().getSessionValue());
		}
		HttpResponse httpResponse;
		httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			InputStream inputStream = httpEntity.getContent();
			data = convertStreamToString(inputStream, encoding);
			if (LFormat.isEmpty(data))
				throw new ConnectException(RUNTIME_EXCEPTION);
			data = LFormat.JSONTokener(data);
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				if (LApplication.getInstance().getSessionKey()
						.equals(cookies.get(i).getName())) {
					LApplication.getInstance().setSessionValue(
							cookies.get(i).getValue());
				}
			}
		} else {
			throw new ConnectException(RUNTIME_EXCEPTION);
		}
		if (useCache && !LFormat.isEqual(SEND_ERROR, data)) {
			doSetCache(url, data);
		}
		return data;
	}

	/**
	 * 通过InputStream获得UTF-8格式的String
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String convertStreamToString(final InputStream stream)
			throws Exception {
		return convertStreamToString(stream, LReqEncode.UTF8.getEncode());
	}

	/**
	 * 通过InputStream获得charsetName格式的String
	 * 
	 * @param inSream
	 * @param charsetName
	 *            指定格式
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String convertStreamToString(InputStream inSream,
			String charsetName) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	/**
	 * 获取网络图片， 如果图片存在于缓存中， 就返回该图片， 否则从网络中加载该图片并缓存起来
	 * 
	 * @param path
	 *            图片路径
	 * @param cacheDir
	 * @return
	 */
	public static String getImage(String path, File cacheDir) {
		File localFile = new File(cacheDir, LFormat.getMD5Url(path));
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		boolean downFile = true;
		if (!localFile.exists()) {
			downFile = getNetDownFile(path, localFile);
		}
		if (downFile) {
			return localFile.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 从网络上下载文件
	 * 
	 * @param path
	 *            ：下载路径
	 * @param localFile
	 *            ：本地路径
	 * @return 下载成功：true---下载失败：false
	 */
	public static boolean getNetDownFile(String path, File localFile) {
		InputStream inputStream = null;
		FileOutputStream outStream = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(path)
					.openConnection();
			conn.setConnectTimeout(25000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				outStream = new FileOutputStream(localFile);
				inputStream = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
			}
		} catch (Exception e) {
			L.e(LException.getStackMsg(e));
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				inputStream = null;
			} catch (Exception e2) {
				L.e(LException.getStackMsg(e2));
			}
			try {
				if (outStream != null)
					outStream.close();
				outStream = null;
			} catch (Exception e2) {
				L.e(LException.getStackMsg(e2));
			}
		}
		return localFile.exists() ? true : false;
	}

	/**
	 * 将url转成MD5
	 * 
	 * @param url
	 * @return
	 */
	public static String getMD5Url(String url) {
		return MD5.getMD5(url);
	}

	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static String doUploadFile(String url, Map<String, String> params,
			List<LReqFile> files, LReqEncode encoding) throws Exception {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = encoding.getEncode();

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", CHARSET);
		conn.setRequestProperty(LApplication.getInstance().getSessionKey(),
				LApplication.getInstance().getSessionValue());
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 发送文件数据
		String resStr = SEND_ERROR;
		if (files != null) {
			for (LReqFile file : files) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getName() + "\"" + LINEND);
				sb1.append("Content-Type: " + file.getType().getType()
						+ "; charset=" + CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getFile());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				int ch;
				StringBuilder sb2 = new StringBuilder();
				while ((ch = in.read()) != -1) {
					sb2.append((char) ch);
				}
				String data = sb2.toString();
				if (TextUtils.isEmpty(data)) {
					throw new ConnectException(RUNTIME_EXCEPTION);
				}
				resStr = LFormat.JSONTokener(data);
			} else {
				throw new ConnectException(RUNTIME_EXCEPTION);
			}
			outStream.close();
			conn.disconnect();
		} else {
			throw new NullPointerException("需要上传的文件集合不能为空");
		}
		return resStr;
	}
}
