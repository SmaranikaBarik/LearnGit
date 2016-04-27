package com.mhe.rest.datacash.exception;

import java.io.Serializable;

public class AppException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private int status;
	private String location;
	private String orderId;
	private String consumerRequest;

	public AppException(String code,String message,int responseStatus,String location, String orderId,String myconsumerRequest) {
		super(message);
		
		this.code=code;
		this.message=message;
		this.status=responseStatus;
		this.location = location;
		this.orderId = orderId;
		this.consumerRequest = myconsumerRequest;
	}

	public AppException() {

	}

	public AppException(String code, String message, int status,
			String location, String orderId) {
		super(message);
		this.code = code;
		this.message = message;
		this.status = status;
		this.location = location;
		this.orderId = orderId;
	}

	public AppException(String code, String message, int status, String location) {
		super(message);
		this.code = code;
		this.message = message;
		this.status = status;
		this.location = location;

	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "AppException [code=" + code + ", message=" + message
				+ ", status=" + status + ", location=" + location
				+ ", orderId=" + orderId + ", consumerRequest=" + consumerRequest + "]";
	}

	public String getConsumerRequest() {
		return consumerRequest;
	}

	public void setConsumerRequest(String consumerRequest) {
		this.consumerRequest = consumerRequest;
	}

}
