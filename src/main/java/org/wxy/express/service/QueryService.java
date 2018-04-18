package org.wxy.express.service;

import java.util.Map;

public interface QueryService {
	
	/**
	 * 查询UPS订单号
	 * @return
	 */
	Map<String,Object> find(String orders);
	
	
}
