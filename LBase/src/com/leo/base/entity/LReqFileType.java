package com.leo.base.entity;

/**
 * 此类为网络请求提交文件类型的类<br/>
 * 暂时只收录了jpeg格式<br/>
 * 如需其它类型，自行添加即可<br/>
 * 请见谅
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public enum LReqFileType {

	JPEG("image/pjpeg");

	private String type;

	private LReqFileType(String str) {
		type = str;
	}

	public String getType() {
		return type;
	}

}
