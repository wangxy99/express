package org.wxy.express.access;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxy.express.exception.BusinessException;
import org.wxy.express.util.HttpClientUtils;
import org.wxy.express.util.RegexUtils;

/**
 * 数据访问实现1 <br>
 * 原URL:******<br>
 * 后续改为 URL: https://wwwapps.ups.com/tracking/tracking.cgi?tracknum=1ZX448150393862174
 * 
 * @author wang by 20180526
 *
 */
public class UPSDataAccess implements IDataAccess {
	private static final Logger logger = LoggerFactory.getLogger(UPSDataAccess.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	static String URL = "https://wwwapps.ups.com/WebTracking/reference?loc=zh_CN&Requester=WS&TypeOfInquiryNumber=R&tracknum=%s&FromDatepicker=%s&ToDatepicker=%s&shipmentType=package&CountryOrigin=&DestinationCountry=";

	public String[] getTrackingNumber(String orderNum) {
		String trackingNumber[] = { orderNum, "", "" };
		try {
			logger.info("待查询的单据编号:{}", new Object[] { orderNum });
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String currentDate = sdf.format(date);
			calendar.add(Calendar.MONTH, -1);

			String startDate = sdf.format(calendar.getTime());

			String ups2 = String.format(URL, orderNum, startDate, currentDate);
			logger.info("查询地址: " + ups2);
			String html = HttpClientUtils.doGet(ups2);
			logger.debug("返回的结果：\n" + html);

			String matcher = "<input type=\"hidden\" name=\"trackNums\" value=\"\\w{18}\">";
			String matcherStr = RegexUtils.getString(html, matcher);
			if (StringUtils.isNotBlank(matcherStr)) {
				int start = matcherStr.length() - 20;
				int end = matcherStr.length() - 2;
				trackingNumber[1] = matcherStr.substring(start, end);
				logger.info("trackingNumber:{}", trackingNumber[1]);
			}
		} catch (BusinessException be) {
			logger.error(be.getErrorMessage(), be);
		} catch (Exception e) {
			if (e instanceof java.net.ConnectException) {
				trackingNumber[2] = "链接失败，稍后重试此单";
			}
			logger.error(e.getMessage(), e);
		}
		return trackingNumber;
	}
}
