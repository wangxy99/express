package org.wxy.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wxy.express.constant.Constants;
import org.wxy.express.model.ExcelStyleInfo;
import org.wxy.express.util.POIUtils;

public class POITest2 {

	public static void main(String[] args) {
		List<String[]> data = new ArrayList<String[]>();
		String[] row1 = new String[] { "619959320106", "1ZX448150393862174", "wewfwfewfewfwfwf" };
		String[] row2 = new String[] { "619959320106", "1ZX448150393862174", "" };
		String[] row3 = new String[] { "619959320106", "1ZX448150393862174" };
		data.add(row1);
		data.add(row2);
		data.add(row3);

		ExcelStyleInfo style = new ExcelStyleInfo();
		Map<Integer, Integer> columnWidth = new HashMap<Integer, Integer>();
		columnWidth.put(0, 15 * 256);
		columnWidth.put(1, 20 * 256);
		columnWidth.put(2, 30 * 256);
		style.setColumnWidth(columnWidth);
		POIUtils.writeExcel(Constants.EXCEL_FILE_NAME, "测试", data, style);
	}

}
