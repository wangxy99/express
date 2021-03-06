package org.wxy.express.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxy.express.access.IDataAccess;
import org.wxy.express.access.ParcelDataAccess;
import org.wxy.express.access.UPSDataAccess;
import org.wxy.express.constant.Constants;
import org.wxy.express.model.ExcelStyleInfo;
import org.wxy.express.model.ProgressInfo;
import org.wxy.express.service.QueryService;
import org.wxy.express.util.POIUtils;

public class UPSService implements QueryService {
	private static final Logger logger = LoggerFactory.getLogger(UPSService.class);

	private static IDataAccess ups;
	private static IDataAccess parcel;
	private static ExcelStyleInfo style;

	static {
		ups = new UPSDataAccess();
		parcel = new ParcelDataAccess();
		style = new ExcelStyleInfo();
		Map<Integer, Integer> columnWidth = new HashMap<Integer, Integer>();
		columnWidth.put(0, 15 * 256);
		columnWidth.put(1, 20 * 256);
		columnWidth.put(2, 30 * 256);
		style.setColumnWidth(columnWidth); // 设置每列的宽度
	}

	ReadWriteLock rwl = new ReentrantReadWriteLock(); // 读写锁
	ProgressInfo progress = new ProgressInfo(2);

	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	// 注意 %s
	// String url2 =
	// "https://wwwapps.ups.com/WebTracking/reference?loc=zh_CN&Requester=WS&TypeOfInquiryNumber=R&tracknum=%s&FromDatepicker=%s&ToDatepicker=%s&shipmentType=package&CountryOrigin=&DestinationCountry=";

	@Override
	public Map<String, Object> find(List<String> orderList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.STATE, Constants.STATE_FAIL);
		map.put(Constants.MGS, "查询失败");

		if (orderList == null || orderList.isEmpty()) {
			throw new IllegalArgumentException("传入订单号异常");
		}
		// Calendar calendar = Calendar.getInstance();
		// Date date = calendar.getTime();
		// String currentDate = sdf.format(date);
		//
		// calendar.add(Calendar.MONTH, -1);
		// String startDate = sdf.format(calendar.getTime());

		List<String[]> data = new ArrayList<String[]>();
		for (int i = 0, len = orderList.size(); i < len; i++) {
			// // 判断线程状态，用于线程中止功能
			// logger.info("当前线程状态，" + Thread.currentThread().isInterrupted() + "; " +
			// Thread.currentThread().toString());
			// if(Thread.currentThread().isInterrupted()) {
			// break;
			// }
			// 设置监听，后续改为观察都模式
			progress.setMaximum(orderList.size());
			progress.setCurrentValue(i + 1);
			String order = orderList.get(i);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("TrackingNumber", order);

			// 单号查询
			String[] sub = { order, "NO_SEARCH", "" }; // 默认返回结果

			String[] trackInfo = ups.getTrackingNumber(order);
			if (StringUtils.isNotBlank(trackInfo[1]) || StringUtils.isNotBlank(trackInfo[2])) {
				sub = trackInfo;
				logger.info("Q1:" + Arrays.toString(sub));
				continue;
			}

			trackInfo = parcel.getTrackingNumber(order);
			if (StringUtils.isNotBlank(trackInfo[1]) || StringUtils.isNotBlank(trackInfo[2])) {
				sub = trackInfo;
				logger.info("Q2:" + Arrays.toString(sub));
				continue;
			}

			data.add(sub);

			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

		}

		map.put(Constants.STATE, Constants.STATE_SUCCESS);
		map.put(Constants.MGS, "查询成功");

		// 写入Excel
		POIUtils.writeExcel(Constants.EXCEL_FILE_NAME, "ups单号", data, style);

		return map;
	}

	@Override
	public Map<String, Object> find(String orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.STATE, Constants.STATE_FAIL);
		map.put(Constants.MGS, "查询失败");

		try {
			String[] list = orders.split("\\s"); // 非字符
			List<String> orderList = new ArrayList<String>();
			for (String order : list) {
				if (StringUtils.isBlank(order)) {
					continue;
				}
				orderList.add(order);
			}

			map = find(orderList);

		} catch (Exception ee) {
			logger.error(ee.getMessage(), ee);
		}
		return map;
	}

	@Override
	public ProgressInfo getProgress() {
		return progress;
	}

	// @Override
	// public Map<String, Object> find(String orders) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put(Constants.STATE, Constants.STATE_FAIL);
	// map.put(Constants.MGS, "查询失败");
	//
	// Calendar calendar = Calendar.getInstance();
	// Date date = calendar.getTime();
	// String currentDate = sdf.format(date);
	//
	// calendar.add(Calendar.MONTH, -1);
	// String startDate = sdf.format(calendar.getTime());
	// try {
	// String[] list = orders.split("\\s"); // 非字符
	//
	// List<String[]> data = new ArrayList<String[]>();
	// for (String order : list) {
	// if(StringUtils.isBlank(order)) {
	// continue;
	// }
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("TrackingNumber", order);
	// String[] sub = new String[3];
	// sub[0] = order;
	// sub[1] = "未查询到";
	// try {
	// // 查询第一个地址
	// logger.info("查询地址1: "+url1);
	// String a = HttpClientUtils.doPostSSL(url1, params);
	// int num = a.indexOf("Their tracking number is ");
	// if (num != -1) {
	// sub[1] = a.substring(num + 25, num + 25 + 18);
	// sub[2] = "";
	// } else {
	// String ups2 = String.format(url2, order, startDate, currentDate);
	// logger.info("查询地址2: "+ups2);
	// String aa = HttpClientUtils.doGet(ups2);
	// int num1 = aa.indexOf("name=\"tdmp1.y\">");
	// if (num1 != -1) {
	// sub[1] = aa.substring(num1 + 15, num1 + 15 + 18);
	// sub[2] = "通过第二个地址查询到";
	// }
	//
	// }
	// data.add(sub);
	// Thread.sleep(1000L);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// }
	// System.out.println(order + "\t" + sub);
	// logger.info("Q:"+Arrays.toString(sub));
	//
	// }
	// map.put(Constants.STATE, Constants.STATE_SUCCESS);
	// map.put(Constants.MGS, "查询成功");
	//
	// // 写入Excel
	// POIUtils.writeExcel(Constants.EXCEL_FILE_NAME, "ups单号", data);
	//
	// } catch (Exception ee) {
	// logger.error(ee.getMessage(), ee);
	// }
	// return map;
	// }

}
