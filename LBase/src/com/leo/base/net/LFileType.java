package com.leo.base.net;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public enum LFileType {

	JPEG("image/pjpeg");
	
	private String type;
	
	private LFileType(String str) {
		type = str;
	}
	
	public String getType() {
		return type;
	}
	
}
