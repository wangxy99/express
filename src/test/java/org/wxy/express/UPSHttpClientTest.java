package org.wxy.express;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.wxy.express.util.HttpClientUtils;

public class UPSHttpClientTest {
	static String url1 = "https://tracking.i-parcel.com/";
	static // 注意 %s
	String url2 = "https://wwwapps.ups.com/WebTracking/reference?loc=zh_CN&Requester=WS&TypeOfInquiryNumber=R&tracknum=%s&FromDatepicker=%s&ToDatepicker=%s&shipmentType=package&CountryOrigin=&DestinationCountry=";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	static String url3 = "https://tracking.i-parcel.com/Home/Index?trackingnumber=%s";

	public static void main(String[] args) {
		// String order = "619372832792";
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("TrackingNumber", order);
		// String a = HttpClientUtils.doPostSSL(url1, params);
		// System.out.println(a);

		// 第二个地址
		// Calendar calendar = Calendar.getInstance();
		// Date date = calendar.getTime();
		// String currentDate = sdf.format(date);
		//
		// calendar.add(Calendar.MONTH, -1);
		// String startDate = sdf.format(calendar.getTime());
		// String ups2 = String.format(url2, order, startDate, currentDate);
		// String aa = HttpClientUtils.doGet(ups2);
		// System.out.println(aa);
		//// String aa = "[0x9][0x9][0x9][0x9][0x9][0x9][0x9]<input type=\"hidden\" name=\"trackNums\"
		// value=\"1Z1EF7860300374894\">[\\r][\\n]";
		// int num1 = aa.indexOf("name=\"trackNums\" value=\"");
		// if (num1 != -1) {
		// System.out.println("查询结果："+aa.substring(num1, num1 + 18));
		//
		// }

		String order = "619959320106";
		String ups3 = String.format(url3, order);
		String html = HttpClientUtils.doGet(ups3);
		System.out.println(html);

	}

	@Test
	public void test1() {
		// String str = "Upon successful customs clearance, your parcel will be given to UPS who we use for local
		// delivery to your address. "
		// + "Their tracking number is 1ZX448150393862174, you can link to it ";

		String order = "619959320106";
		String ups3 = String.format(url3, order);
		String html = HttpClientUtils.doGet(ups3);
		// System.out.println(html);

		String matcher = "Their tracking number is ";
		int index = html.indexOf(matcher);
		if (index > 0) {
			int len = matcher.length();
			System.out.println("index:" + index + " len:" + len);
			String UPSOrder = html.substring(index + len, index + len + 18);
			System.out.println(UPSOrder);
		}
	}
}
