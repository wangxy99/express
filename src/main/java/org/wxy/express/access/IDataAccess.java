package org.wxy.express.access;

/**
 * 数据访问层
 * 
 * @author wang by 20180526
 *
 */
public interface IDataAccess {

	String[] getTrackingNumber(String orderNum);
}
