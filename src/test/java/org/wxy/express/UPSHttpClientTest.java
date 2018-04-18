package org.wxy.express;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.wxy.express.util.HttpClientUtils;

public class UPSHttpClientTest {
	static String url1 = "https://tracking.i-parcel.com/";
	static // 注意 %s
	String url2 = "https://wwwapps.ups.com/WebTracking/reference?loc=zh_CN&Requester=WS&TypeOfInquiryNumber=R&tracknum=%s&FromDatepicker=%s&ToDatepicker=%s&shipmentType=package&CountryOrigin=&DestinationCountry=";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public static void main(String[] args) {
		String order = "619372832792";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("TrackingNumber", order);
//		String a = HttpClientUtils.doPostSSL(url1, params);
//		System.out.println(a);
		
		
		// 第二个地址
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String currentDate = sdf.format(date);

		calendar.add(Calendar.MONTH, -1);
		String startDate = sdf.format(calendar.getTime());
		String ups2 = String.format(url2, order, startDate, currentDate);
		String aa = HttpClientUtils.doGet(ups2);
		System.out.println(aa);
//		String aa = "[0x9][0x9][0x9][0x9][0x9][0x9][0x9]<input type=\"hidden\" name=\"trackNums\" value=\"1Z1EF7860300374894\">[\\r][\\n]";
		int num1 = aa.indexOf("name=\"trackNums\" value=\"");
		if (num1 != -1) {
			System.out.println("查询结果："+aa.substring(num1, num1 + 18));
	
		}
		
	}
}
