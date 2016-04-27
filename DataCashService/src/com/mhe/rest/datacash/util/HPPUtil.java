package com.mhe.rest.datacash.util;

import java.io.IOException;
import java.util.LinkedList;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

//import com.mhe.soa.hpp.orderabstract.HPPAbstractService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HPPUtil {
	public static final String CONSUMER_ERROR_RESPONSE_URL = (PropertiesUtil
			.readProperty()).getProperty("consumer_error_response_url");
	protected static Logger logger = LoggerUtility.getLogger(HPPUtil.class
			.getName());

	public static Response buildErrorResponse(String errorResponseURL,
			String orderId, String uID, String code, String message,
			String status) {
		UriBuilder uriBuilder = UriBuilder.fromUri(errorResponseURL);
		uriBuilder.queryParam("orderId", orderId);
		uriBuilder.queryParam("uID", uID);
		uriBuilder.queryParam("ErrorCode", code);
		uriBuilder.queryParam("ErrorMessage", message);
		uriBuilder.queryParam("status", status);

		Response response = Response.seeOther(uriBuilder.build()).build();
		return response;
	}

	public static String getValue(MultivaluedMap formParams, String elementName) {
		String elementValue = "";
		if (formParams == null)
			return elementValue;
		if (((LinkedList) formParams.get(elementName)) == null)
			return elementValue;
		elementValue = (String) ((LinkedList) formParams.get(elementName))
				.get(0);
		return elementValue;

	}

	public static String getErrorResponseURL(MultivaluedMap formParams) {
		String location = null;
		if (formParams == null)
			return CONSUMER_ERROR_RESPONSE_URL;
		if (((LinkedList) formParams.get("consumer_error_response_url")) == null
				|| ((String) ((LinkedList) formParams
						.get("consumer_error_response_url")).get(0)).equals("")) {
			return CONSUMER_ERROR_RESPONSE_URL;
		}

		location = HPPUtil.getValue(formParams, "consumer_error_response_url");
		return location;
	}

	public static String encode(String input) {
		String encInput = new String(new BASE64Encoder().encode(input
				.getBytes()));
		encInput = encInput.replaceAll("/", "_");
		return encInput;

	}

	public static String decode(String encInput) {
		String decInput = null;
		try {
			encInput = encInput.replaceAll("_", "/");
			decInput = new String(new BASE64Decoder().decodeBuffer(encInput));
		} catch (IOException e) {
			logger.error("ERROR WHILE DECODING BASE64", e);
		}
		return decInput;

	}

	public static void main(String[] args) {
		System.out
				.println(encode("https://shopqastg.mheducation.com/store/checkout/orderreview.html?errorCode=SOA_FAILURE"));
		System.out
				.println(decode("aHR0cHM6Ly9zaG9wcWFzdGcubWhlZHVjYXRpb24uY29tL21oc2hvcC9wYXJpcy9jaGVja291dC90b2tlbml6YXRpb24vcmV2aWV3Lmh0bWw_d2ViX29yZGVyX25vPU1IRVFBU1RHLUhFLTk1NTU4NjMmZXJyb3JDb2RlPVNPQV9GQUlMVVJF"));

	}
}
