package org.wxy.express.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxy.express.exception.BusinessException;
import org.wxy.express.util.HttpClientUtils;

/**
 * 数据访问实现2 <br>
 * URL: https://www.i-parcel.com/
 * 
 * @author wang by 20180526
 *
 */
public class ParcelDataAccess implements IDataAccess {
	private static final Logger logger = LoggerFactory.getLogger(ParcelDataAccess.class);
	static String URL = "https://tracking.i-parcel.com/Home/Index?trackingnumber=%s";

	public String[] getTrackingNumber(String orderNum) {
		String trackingNumber[] = { orderNum, "", "" };
		try {
			logger.info("待查询的单据编号:{}", new Object[] { orderNum });
			String ups3 = String.format(URL, orderNum);
			String html = HttpClientUtils.doGet(ups3);
			logger.debug("返回的结果：\n" + html);

			String matcher = "Their tracking number is ";
			int index = html.indexOf(matcher);
			if (index > 0) {
				int len = matcher.length();
				trackingNumber[1] = html.substring(index + len, index + len + 18);
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
