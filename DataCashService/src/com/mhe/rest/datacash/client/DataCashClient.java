package com.mhe.rest.datacash.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.DataCashAbstractService;
import com.mhe.rest.datacash.util.LoggerUtility;

@Path("/DataCash")
public class DataCashClient extends DataCashAbstractService {

	// static Logger logger = Logger.getLogger(DataCashClient.class.getName());   

	protected static Logger logger = LoggerUtility
			.getLogger(DataCashClient.class.getName());

	@GET
	@Path("SessionSetUp")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public Response requestSessionId(@Context UriInfo uri)
			throws JAXBException, XPathExpressionException {

		logger.info("Absolute URL for the GET request is  : "
				+ uri.getAbsolutePath().toString());
		MultivaluedMap<String, String> queryParams = uri.getQueryParameters();
		logger.info("@GET requestSessionId is  : " + queryParams.toString());

		if (queryParams.size() == 0) {
			logger.info("Within VALIDATION method");
			return Response.ok("Validating Endpoint was SUCCESSFUL").build();
		} else
			return getSessionId(queryParams);
	}

	@GET
	@Path("QueryData")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public Response requestQueryData(
			@QueryParam("dts_reference") String dts_reference)
			throws JAXBException, XPathExpressionException {

		logger.info("Query reference value is   : " + dts_reference);

		if (dts_reference == null || dts_reference.isEmpty()) {
			logger.info("Within VALIDATION for query method");
			return Response.ok("Validating query Endpoint was SUCCESSFUL")
					.build();
		} else {
			return getQueryResults(dts_reference);
		}

	}

	@GET
	@Path("FulFill")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public Response requestQueryData(
			@QueryParam("merchantref") String merchantref,
			@QueryParam("amount") String amount,
			@QueryParam("authcode") String authcode,
			@QueryParam("dtreference") String dtreference,
			@QueryParam("currency") String currency) throws JAXBException,
			XPathExpressionException {

		logger.info("merchantref value is   : " + merchantref);
		logger.info("amount value is   : " + amount);
		logger.info("authcode value is   : " + authcode);
		logger.info("Query reference value is   : " + dtreference);
		logger.info("currency value is   : " + currency);

		if (merchantref.equals(null) || amount.equals(null)) {
			logger.info("Within VALIDATION for FulFill method");
			return Response.ok("Validating Fulfill Endpoint was SUCCESSFUL")
					.build();
		} else {
			return getFulfillResults(merchantref, amount, authcode,
					dtreference, currency);
		}

	}

	@GET
	@Path("ReAuth")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public Response requestQueryData(
			@QueryParam("merchantref") String merchantref,
			@QueryParam("amount") String amount,
			@QueryParam("dtreference") String dtreference,
			@QueryParam("currency") String currency) throws JAXBException,
			XPathExpressionException {

		logger.info("merchantref value is   : " + merchantref);
		logger.info("amount value is   : " + amount);
		logger.info("DataCash reference value is   : " + dtreference);

		if (merchantref.equals(null) || amount.equals(null)) {
			logger.info("Within VALIDATION for FulFill method");
			return Response.ok("Validating Fulfill Endpoint was SUCCESSFUL")
					.build();
		} else {
			return getReAuthResults(merchantref, amount, dtreference, currency);
		}

	}

}
