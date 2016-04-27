package com.mhe.rest.datacash.exception;

import java.net.URI;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.util.HPPUtil;
import com.mhe.rest.datacash.util.LoggerUtility;
import com.mhe.rest.datacash.util.SendEmailAlert;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	protected static Logger logger = LoggerUtility
			.getLogger(GenericExceptionMapper.class.getName());

	@Override
	public Response toResponse(Throwable ex) {

		AppException errorMessage = new AppException();

		setHttpStatus(ex, errorMessage);
		errorMessage.setStatus(AppConstants.GENERIC_APP_ERROR_CODE);
		errorMessage.setMessage(ex.getMessage());

		logger.error("The error is a Generic Exception and error is :"
				+ errorMessage.toString());
		logger.error("GENERIC EXCEPTION:", ex);
		try {
			String Order_id = errorMessage.getOrderId();
			if (Order_id != null) {
				logger.error(" Generic Exception happened for Order Id  :"
						+ Order_id);
			} else {
				Order_id = "XXXXX";
			}
			SendEmailAlert.sendEmail(ex, ex.getMessage(),
					AppConstants.GENERIC_APP_ERROR_CODE, Order_id);

		} catch (Exception e) {
			logger.error("Execption sending email -- " + ex.toString());
		}
		String errorResponseURL = HPPUtil.getErrorResponseURL(null);
		UriBuilder builder = UriBuilder.fromUri(errorResponseURL);

		builder.queryParam("ErrorCode", "999");
		builder.queryParam("ErrorMessage", ex.getMessage());
		builder.queryParam("status", "500");
		// builder.queryParam("request", ex.getConsumerRequest());
		URI uri = builder.build();
		return Response.seeOther(uri).build();

	}

	private void setHttpStatus(Throwable ex, AppException errorMessage) {
		if (ex instanceof WebApplicationException) {
			errorMessage.setStatus(((WebApplicationException) ex).getResponse()
					.getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode()); // defaults
										// to
										// internal
										// server
										// error
										// 500
		}
	}

}
