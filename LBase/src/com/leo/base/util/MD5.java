package com.leo.base.util;

import java.security.MessageDigest;

import com.leo.base.exception.LException;


/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public final class MD5 {

	private MD5() {
	}

	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHasnString(digest);
		} catch (Exception e) {
			L.e(LException.getStackMsg(e));
		}
		return null;
	}

	private static String getHasnString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}
}
