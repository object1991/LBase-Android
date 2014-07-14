package com.leo.base.net;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LReqEntity {

	/**
	 * 网络请求地址
	 */
	private String mUrl;

	/**
	 * 网络请求参数
	 */
	private Map<String, String> mParams;

	/**
	 * 需要上传的文件集合
	 */
	private List<LFileEntity> mFileParams;

	/**
	 * 网络请求模式
	 */
	private ReqMothed mReqMode;

	/**
	 * 网络请求编码方式
	 */
	private ReqEncode mReqEncode;

	/**
	 * 网络请求是否启用缓存
	 */
	private boolean mUseCache;

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public Map<String, String> getParams() {
		return mParams;
	}

	public void setParams(Map<String, String> params) {
		this.mParams = params;
	}

	public ReqMothed getReqMode() {
		return mReqMode;
	}

	public void setReqMode(ReqMothed mReqMode) {
		this.mReqMode = mReqMode;
	}

	public ReqEncode getReqEncode() {
		return mReqEncode;
	}

	public void setReqEncode(ReqEncode mReqEncode) {
		this.mReqEncode = mReqEncode;
	}

	public boolean getUseCache() {
		return mUseCache;
	}

	public void setUseCache(boolean mUseCache) {
		this.mUseCache = mUseCache;
	}

	public List<LFileEntity> getFileParams() {
		return mFileParams;
	}

	public void setFileParamsList(List<LFileEntity> list) {
		this.mFileParams = list;
	}

	public LReqEntity() {
		init(null, null, null, ReqMothed.GET, ReqEncode.UTF8, false);
	}

	/**
	 * 网络请求封装实体<br/>
	 * 无参，以GET方式请求，UTF-8编码，不启用缓存
	 * 
	 * @param url
	 *            ：地址
	 */
	public LReqEntity(String url) {
		init(url, null, null, ReqMothed.GET, ReqEncode.UTF8, false);
	}

	/**
	 * 网络请求封装实体<br/>
	 * 以POST方式请求，UTF-8编码，不启用缓存
	 * 
	 * @param url
	 *            ：地址
	 * @param params
	 *            ：参数
	 */
	public LReqEntity(String url, Map<String, String> params) {
		init(url, params, null, ReqMothed.POST, ReqEncode.UTF8, false);
	}

	/**
	 * 网络请求封装实体<br/>
	 * UTF-8编码，不启用缓存
	 * 
	 * @param url
	 *            ：地址
	 * @param params
	 *            ：参数
	 * @param mode
	 *            ：请求模式
	 */
	public LReqEntity(String url, Map<String, String> params, ReqMothed mode) {
		init(url, params, null, mode, ReqEncode.UTF8, false);
	}

	/**
	 * 网络请求封装实体<br/>
	 * 不启用缓存
	 * 
	 * @param url
	 *            ：地址
	 * @param params
	 *            ：参数
	 * @param mode
	 *            ：请求模式
	 * @param encoding
	 *            ：请求编码
	 */
	public LReqEntity(String url, Map<String, String> params, ReqMothed mode,
			ReqEncode encoding) {
		init(url, params, null, mode, encoding, false);
	}

	/**
	 * 网络请求封装实体
	 * 
	 * @param url
	 *            ：地址
	 * @param params
	 *            ：参数
	 * @param list
	 *            ：文件集合
	 * @param mode
	 *            ：请求模式
	 * @param encoding
	 *            ：请求编码
	 * @param useCache
	 *            ：启用缓存
	 */
	public LReqEntity(String url, Map<String, String> params, ReqMothed mode,
			ReqEncode encoding, boolean useCache) {
		init(url, params, null, mode, encoding, useCache);
	}

	/**
	 * 网络请求封装实体
	 * 
	 * @param url
	 *            ：地址
	 * @param params
	 *            ：参数
	 * @param list
	 *            ：文件集合
	 * @param encoding
	 *            ：编码
	 */
	public LReqEntity(String url, Map<String, String> params,
			List<LFileEntity> list, ReqEncode encoding) {
		init(url, params, list, ReqMothed.POST, encoding, false);
	}

	private void init(String url, Map<String, String> params,
			List<LFileEntity> list, ReqMothed mode, ReqEncode encoding,
			boolean useCache) {
		this.mUrl = url;
		this.mParams = params;
		this.mFileParams = list;
		this.mReqMode = mode;
		this.mReqEncode = encoding;
		this.mUseCache = useCache;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("地址：").append(this.mUrl);
		sb.append("<--->");
		sb.append("参数：").append(this.mParams);
		sb.append("<--->");
		sb.append("文件：").append(this.mFileParams);
		sb.append("<--->");
		sb.append("模式：").append(this.mReqMode.getMothed());
		sb.append("<--->");
		sb.append("编码：").append(this.mReqEncode.getEncode());
		sb.append("<--->");
		sb.append("缓存：").append(this.mUseCache ? "不启用" : "启用");
		return sb.toString();
	}

}
