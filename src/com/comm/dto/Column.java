package com.comm.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Column implements Serializable {
	private String header;
	private int width;
	private String name;
	private String align;
	private String render;

	public Column(String paramString1, int paramInt, String paramString2,
			String paramString3, String paramString4) {
		this.header = paramString1;
		this.width = paramInt;
		this.name = paramString2;
		this.align = paramString3;
		this.render = paramString4;
	}

	public Column() {
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String paramString) {
		this.header = paramString;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int paramInt) {
		this.width = paramInt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getAlign() {
		return this.align;
	}

	public void setAlign(String paramString) {
		this.align = paramString;
	}

	public String getRender() {
		return this.render;
	}

	public void setRender(String paramString) {
		this.render = paramString;
	}
}