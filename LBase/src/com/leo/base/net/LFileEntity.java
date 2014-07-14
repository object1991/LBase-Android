package com.leo.base.net;

import java.io.File;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LFileEntity {

	private String name;
	private File file;
	private LFileType type;

	public LFileEntity(String name, File file, LFileType type) {
		this.name = name;
		this.file = file;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public LFileType getType() {
		return type;
	}

	public void setType(LFileType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("文件名：").append(name).append("<--->");
		sb.append("文件地址：").append(file.getPath()).append("<--->");
		sb.append("文件类型：").append(type.getType());
		return sb.toString();
	}
}
