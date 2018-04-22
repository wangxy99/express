package org.wxy.express.service;

import java.util.List;
import java.util.Map;

import org.wxy.express.model.ProgressInfo;

public interface QueryService {
	
	/**
	 * 查询UPS订单号
	 * @return
	 */
	Map<String,Object> find(String orders);
	
	
	/**
	 * 查询UPS订单号
	 * @return
	 */
	Map<String,Object> find(List<String> orderList);
	
	/**
	 * 获取进度
	 * @return
	 */
	ProgressInfo getProgress();
	
}
