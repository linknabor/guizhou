/**
 * 
 */
package com.yumu.hexie.service.exception;

/**
 * @author huym
 *
 */
public class IntegrationBizException extends RuntimeException {
	
	private String requestId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5195477727633812416L;

	/**
	 * 
	 */
	public IntegrationBizException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public IntegrationBizException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 */
	public IntegrationBizException(String message, String requestId) {
		super(message);
		this.requestId = requestId;
	}

	/**
	 * @param cause
	 */
	public IntegrationBizException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IntegrationBizException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public IntegrationBizException(String message, Throwable cause, String requestId) {
		super(message, cause);
		this.requestId = requestId;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IntegrationBizException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	

}
