package com.mhe.rest.datacash.exception;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.util.LoggerUtility;
import com.mhe.rest.datacash.util.SendEmailAlert;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

	protected static Logger logger = LoggerUtility
			.getLogger(AppExceptionMapper.class.getName());
	SendEmailAlert sendEmailAlert = new SendEmailAlert();

	@Override
	public Response toResponse(AppException ex) {
		// TODO Auto-generated method stub
		logger.error("The error is a App Exception and error is :"
				+ ex.toString());
		logger.error("APP EXCEPTION:", ex);
		UriBuilder builder = UriBuilder.fromUri(ex.getLocation());

		try {

			SendEmailAlert.sendEmail(ex);

		} catch (Exception e) {
			logger.error("Execption sending email -- " + ex.toString());
		}

		logger.debug("Inside toResponse = " + ex.toString());
		logger.debug("Inside toResponse for error code = " + ex.getCode());
		logger.debug("Inside toResponse for error message = " + ex.getMessage());
		builder.queryParam("orderId", ex.getOrderId());
		builder.queryParam("ErrorCode", ex.getCode());
		builder.queryParam("ErrorMessage", ex.getMessage());
		builder.queryParam("status", ex.getStatus());
		// builder.queryParam("request", ex.getConsumerRequest());
		URI uri = builder.build();
		return Response.seeOther(uri).build();
	}

}
