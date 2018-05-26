package org.wxy.express.model;

import java.util.Map;

public class ExcelStyleInfo {
	private int DEFAULT_ROW_HEIGHT;
	private int DEFAULT_COLUMN_WIDTH;
	private Map<Integer, Integer> columnWidth;
	private Map<String, String> border;
	private String alignment;

	// 后续再添加
	public int getDEFAULT_ROW_HEIGHT() {
		return DEFAULT_ROW_HEIGHT;
	}

	public void setDEFAULT_ROW_HEIGHT(int dEFAULT_ROW_HEIGHT) {
		DEFAULT_ROW_HEIGHT = dEFAULT_ROW_HEIGHT;
	}

	public int getDEFAULT_COLUMN_WIDTH() {
		return DEFAULT_COLUMN_WIDTH;
	}

	public void setDEFAULT_COLUMN_WIDTH(int dEFAULT_COLUMN_WIDTH) {
		DEFAULT_COLUMN_WIDTH = dEFAULT_COLUMN_WIDTH;
	}

	public Map<Integer, Integer> getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(Map<Integer, Integer> columnWidth) {
		this.columnWidth = columnWidth;
	}

	public Map<String, String> getBorder() {
		return border;
	}

	public void setBorder(Map<String, String> border) {
		this.border = border;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

}
