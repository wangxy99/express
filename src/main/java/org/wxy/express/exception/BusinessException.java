package org.wxy.express.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 3216953464760621871L;

	private static final String EC_UNKNOW = "express.00";

	private String errorCode = EC_UNKNOW;
	private String errorReason;
	private String errorMessage;
	private Object errorArg;

	/**
	 * 无参构造函数
	 */
	public BusinessException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param errorMessage
	 *            错误消息
	 */
	public BusinessException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 构造函数
	 * 
	 * @param cause
	 *            原始异常
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorMessage
	 *            错误消息
	 * @param cause
	 *            原始异常
	 */
	public BusinessException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param errorReason
	 *            错误原因
	 * @param errorMessage
	 *            错误消息
	 * @param errorArg
	 *            错误参数
	 * @param cause
	 *            原始异常
	 */
	public BusinessException(String errorCode, String errorReason, String errorMessage, Object errorArg,
			Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorReason = errorReason;
		this.errorMessage = errorMessage;
		this.errorArg = errorArg;
	}

	/**
	 * 获取错误参数，错误参数应为可序列化的对象(实现了{@link java.io.Serializable}接口)
	 * 
	 */
	public Object getErrorArg() {
		return errorArg;
	}

	/**
	 * 设置错误参数，可用于错误跟踪或错误恢复。
	 * 
	 * @param errorArg
	 */
	public void setErrorArg(Object errorArg) {
		this.errorArg = errorArg;
	}

	/**
	 * 获取错误原因
	 * 
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * 设置错误原因
	 * 
	 * @param errorReason
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * 获取错误代码
	 * 
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置错误代码
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 获取错误消息
	 * 
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 设置错误消息
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}
}