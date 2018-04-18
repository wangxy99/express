package org.wxy.express.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxy.express.constant.Constants;
import org.wxy.express.service.QueryService;
import org.wxy.express.util.HttpClientUtils;
import org.wxy.express.util.POIUtils;

public class UPSService implements QueryService {
	private static final Logger logger = LoggerFactory.getLogger(UPSService.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	String url1 = "https://tracking.i-parcel.com/";
	// 注意 %s
	String url2 = "https://wwwapps.ups.com/WebTracking/reference?loc=zh_CN&Requester=WS&TypeOfInquiryNumber=R&tracknum=%s&FromDatepicker=%s&ToDatepicker=%s&shipmentType=package&CountryOrigin=&DestinationCountry=";
	
	@Override
	public Map<String, Object> find(String orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.STATE, Constants.STATE_FAIL);
		map.put(Constants.MGS, "查询失败");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String currentDate = sdf.format(date);

		calendar.add(Calendar.MONTH, -1);
		String startDate = sdf.format(calendar.getTime());
		try {
			String[] list = orders.split("\\s"); // 非字符
			
			List<String[]> data = new ArrayList<String[]>();
			for (String order : list) {
				if(StringUtils.isBlank(order)) {
					continue;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("TrackingNumber", order);
				String[] sub = new String[3];
				sub[0] = order;
				sub[1] = "未查询到";
				try {
					// 查询第一个地址
					logger.info("查询地址1: "+url1);
					String a = HttpClientUtils.doPostSSL(url1, params);
					int num = a.indexOf("Their tracking number is ");
					if (num != -1) {
						sub[1] = a.substring(num + 25, num + 25 + 18);
						sub[2] = "";
					} else {
						String ups2 = String.format(url2, order, startDate, currentDate);
						logger.info("查询地址2: "+ups2);
						String aa = HttpClientUtils.doGet(ups2);
						int num1 = aa.indexOf("name=\"tdmp1.y\">");
						if (num1 != -1) {
							sub[1] = aa.substring(num1 + 15, num1 + 15 + 18);
							sub[2] = "通过第二个地址查询到";
						}

					}
					data.add(sub);
					Thread.sleep(1000L);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				System.out.println(order + "\t" + sub);
				logger.info("Q:"+Arrays.toString(sub));

			}
			map.put(Constants.STATE, Constants.STATE_SUCCESS);
			map.put(Constants.MGS, "查询成功");
			
			// 写入Excel
			POIUtils.writeExcel(Constants.EXCEL_FILE_NAME, "ups单号", data);
			
		} catch (Exception ee) {
			logger.error(ee.getMessage(), ee);
		}
		return map;
	}

}
