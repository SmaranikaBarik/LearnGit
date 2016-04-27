package com.mhe.rest.datacash;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.entity.Request;
import com.mhe.rest.datacash.entity.TxnDetails;
import com.mhe.rest.datacash.util.LoggerUtility;
import com.mhe.rest.datacash.util.PropertiesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DataCashAbstractService {

	protected static Logger logger = LoggerUtility
			.getLogger(DataCashAbstractService.class.getName());

	// logger.info("Entered the DataCashAbstractService class");

	protected static final String HPS_URL = (PropertiesUtil.readProperty())
			.getProperty("hps_url");

	protected static final String CLIENT = (PropertiesUtil.readProperty())
			.getProperty("client");

	protected static final String PASSWORD = (PropertiesUtil.readProperty())
			.getProperty("password");

	protected static final int PAGE_SET_ID = Integer.parseInt((PropertiesUtil
			.readProperty()).getProperty("page_set_id"));

	protected static final String RETURN_URL = (PropertiesUtil.readProperty())
			.getProperty("return_url");

	protected static final String EXPIRY_URL = (PropertiesUtil.readProperty())
			.getProperty("expiry_url");

	protected static final String SESSION_REDIRECT_URL = (PropertiesUtil
			.readProperty()).getProperty("session_redirect_url");

	protected static final String RESPONSE_URL = (PropertiesUtil.readProperty())
			.getProperty("response_url");

	private static WebResource webInitResource = null;

	private static WebResource getWebInitResource() {

		logger.info("Within the getWebInitResource method");

		if (webInitResource != null)
			return webInitResource;
		Client client = Client.create();
		webInitResource = client.resource(HPS_URL);

		logger.info("webInitResource is::" + webInitResource);
		return webInitResource;
	}

	private static com.mhe.rest.datacash.entity.Response getResponseObject(
			String output) throws JAXBException {

		logger.info("Within the getResponseObject method");

		JAXBContext jaxbContext = JAXBContext
				.newInstance(com.mhe.rest.datacash.entity.Response.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StreamSource streamSource = new StreamSource(new StringReader(output));
		JAXBElement<com.mhe.rest.datacash.entity.Response> res = unmarshaller
				.unmarshal(streamSource,
						com.mhe.rest.datacash.entity.Response.class);

		logger.info("Response is::"
				+ (com.mhe.rest.datacash.entity.Response) res.getValue());

		return (com.mhe.rest.datacash.entity.Response) res.getValue();

	}

	private static Request createSessionSetUpRequest(
			MultivaluedMap<String, String> queryParams) {

		logger.info("Within the createSessionSetUpRequest method ");

		Request request = new Request();

		Request.Authentication authentication = new Request.Authentication();

		logger.info("Client is::" + CLIENT);
		logger.info("PASSWORD is::" + PASSWORD);

		authentication.setClient(CLIENT);
		authentication.setPassword(PASSWORD);
		request.setAuthentication(authentication);

		Request.Transaction transaction = new Request.Transaction();
		TxnDetails txnDetails = new TxnDetails();
		TxnDetails.Amount amount = new TxnDetails.Amount();
		Request.Transaction.CardTxn cardTxn = new Request.Transaction.CardTxn();
		Request.Transaction.HpsTxn hpsTxn = new Request.Transaction.HpsTxn();

		String output = " Form parameters :\n";

		// Set All the values here
		for (String key : queryParams.keySet()) {

			output += key + " : " + queryParams.getFirst(key) + "\n";

			if (key != null) {
				if ("merchantref".equals(key)) {

					logger.info("merchantref is::" + queryParams.getFirst(key));
					txnDetails.setMerchantreference(queryParams.getFirst(key));
				}
				if ("amount".equals(key)) {
					// amount.setValue(BigDecimal.valueOf(23.99));
					logger.info("amount is::" + queryParams.getFirst(key));
					amount.setValue(BigDecimal.valueOf(Double
							.parseDouble(queryParams.getFirst(key))));
				}
				if ("currency".equals(key)) {
					logger.info("currency is::" + queryParams.getFirst(key));
					amount.setCurrency(queryParams.getFirst(key));
				}

			}
		}

		// amount.setCurrency("CAD");

		System.out.println("Output" + output);
		txnDetails.setAmount(amount);
		cardTxn.setMethod("pre");
		hpsTxn.setMethod("setup_full");
		logger.info("PAGE_SET_ID is::" + PAGE_SET_ID);
		logger.info("RETURN_URL is::" + RETURN_URL);
		logger.info("EXPIRY_URL is::" + EXPIRY_URL);
		hpsTxn.setPageSetId(PAGE_SET_ID);
		hpsTxn.setReturnUrl(RETURN_URL);
		hpsTxn.setExpiryUrl(EXPIRY_URL);
		transaction.setTxnDetails(txnDetails);
		transaction.setCardTxn(cardTxn);
		transaction.setHpsTxn(hpsTxn);
		request.setTransaction(transaction);

		return request;
	}

	@SuppressWarnings("restriction")
	protected Response getSessionId(MultivaluedMap<String, String> queryParams)
			throws JAXBException, XPathExpressionException {

		logger.info("Within the getSessionId method ");

		JAXBContext jc = null;
		Marshaller marshaller = null;
		try {
			jc = JAXBContext.newInstance(Request.class);
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			logger.error("JAXBException::", e1);

		}

		Request request = createSessionSetUpRequest(queryParams);

		marshaller.marshal(request, System.out);

		ClientResponse response = getWebInitResource().accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class, request);

		String output1 = response.getEntity(String.class);

		logger.info("Session Setup Output::" + output1);

		com.mhe.rest.datacash.entity.Response st = getResponseObject(output1);

		String session_id = st.getHpsTxn().getSessionId();

		String hps_url = st.getHpsTxn().getHpsUrl();

		// String session_id = output1.split("<session_id>")[1]
		// .split("</session_id>")[0];

		logger.info("session_id is ::" + session_id);
		logger.info("hps_url is ::" + hps_url);

		String URL = hps_url + "?HPS_SessionID=" + session_id;

		logger.info("URL formed for re-direction is::" + URL);

		UriBuilder builder = UriBuilder.fromUri(URL);

		URI uri = builder.build();
		Response response1 = Response.seeOther(uri).build();

		return response1;
	}

	private static Request createQueryRequest(String reference) {

		logger.info("Within the createQueryRequest method");

		Request request = new Request();

		// Authentication Block
		Request.Authentication authentication = new Request.Authentication();
		authentication.setClient(CLIENT);
		authentication.setPassword(PASSWORD);
		request.setAuthentication(authentication);

		Request.Transaction transaction = new Request.Transaction();

		// HistoricTxn block
		Request.Transaction.HistoricTxn historictxn = new Request.Transaction.HistoricTxn();

		logger.info("Query Refernce is::" + reference);

		historictxn.setReference(reference);
		historictxn.setMethod("query");
		transaction.setHistoricTxn(historictxn);

		request.setTransaction(transaction);

		return request;
	}

	@SuppressWarnings("restriction")
	protected Response getQueryResults(String reference) throws JAXBException,
			XPathExpressionException {

		logger.info("Within the getQueryResults method");

		JAXBContext jc = null;
		Marshaller marshaller = null;
		try {
			jc = JAXBContext.newInstance(Request.class);
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Request firstQueryRequest = createQueryRequest(reference);

		marshaller.marshal(firstQueryRequest, System.out);

		ClientResponse firstQueryresponse = getWebInitResource().accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class,
				firstQueryRequest);

		String firstQueryResults = firstQueryresponse.getEntity(String.class);

		String finalQueryRef = firstQueryResults.split("<datacash_reference>")[1]
				.split("</datacash_reference>")[0];

		Request finalQueryRequest = createQueryRequest(finalQueryRef);

		marshaller.marshal(finalQueryRequest, System.out);

		ClientResponse finalQueryresponse = getWebInitResource().accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class,
				finalQueryRequest);

		String finalQueryResults = finalQueryresponse.getEntity(String.class);

		String orderId = finalQueryResults.split("<merchant_reference>")[1]
				.split("</merchant_reference>")[0];

		String token = finalQueryResults.split("<token>")[1].split("</token>")[0];

		String authCode = finalQueryResults.split("<authcode>")[1]
				.split("</authcode>")[0];

		String dataCashRef = finalQueryResults.split("<datacash_reference>")[1]
				.split("</datacash_reference>")[0];

		String cardType = finalQueryResults.split("<scheme>")[1]
				.split("</scheme>")[0];

		String cardallDigits = finalQueryResults.split("<pan>")[1]
				.split("</pan>")[0];

		String cardLastFourDigits = cardallDigits.substring(cardallDigits
				.length() - 4);

		String reason = finalQueryResults.split("<reason>")[1]
				.split("</reason>")[0];
		String status = finalQueryResults.split("<status>")[1]
				.split("</status>")[0];

		String finalStatus = null;
		String finalStatusCode = null;

		if (reason.equalsIgnoreCase("ACCEPTED") && status.equals("1")) {
			finalStatus = "SUCCESS";
			finalStatusCode = "0000";

		} else {
			finalStatus = "FAILURE";
			finalStatusCode = "0001";
		}

		// String siteLocation =
		// "https://172.26.6.142:8002/DataCashService/HPS_Datacash_Response.jsp";

		UriBuilder builder = UriBuilder.fromUri(RESPONSE_URL);

		logger.info("orderId is::" + orderId);
		logger.info("token is::" + token);
		logger.info("authCode is::" + authCode);
		logger.info("dataCashRef is::" + dataCashRef);
		logger.info("cardType is::" + cardType);
		logger.info("cardLastFourDigits is::" + cardLastFourDigits);
		logger.info("finalStatus is::" + finalStatus);
		logger.info("finalStatusCode is::" + finalStatusCode);

		builder.queryParam("orderId", orderId);
		builder.queryParam("token", token);
		builder.queryParam("authCode", authCode);
		builder.queryParam("dataCashRef", dataCashRef);
		builder.queryParam("cardType", cardType);
		builder.queryParam("cardLastFourDigits", cardLastFourDigits);
		builder.queryParam("finalStatus", finalStatus);
		builder.queryParam("finalStatusCode", finalStatusCode);

		URI uri = builder.build();

		Response response = Response.seeOther(uri).build();
		return response;

	}

	private static Request createFulFillRequest(String merchantref,
			String amount, String authcode, String dtreference, String currency) {

		System.out.println("merchantref value is   : " + merchantref);
		System.out.println("amount value is   : " + amount);
		System.out.println("authcode value is   : " + authcode);
		System.out.println("Query reference value is   : " + dtreference);
		System.out.println("currency value is   : " + currency);

		Request request = new Request();

		// Authentication Block
		Request.Authentication authentication = new Request.Authentication();
		authentication.setClient(CLIENT);
		authentication.setPassword(PASSWORD);
		request.setAuthentication(authentication);

		Request.Transaction transaction = new Request.Transaction();

		TxnDetails txnDetails = new TxnDetails();
		txnDetails.setMerchantreference(merchantref);
		TxnDetails.Amount trxnamount = new TxnDetails.Amount();

		double d = Double.parseDouble(amount);

		trxnamount.setValue(BigDecimal.valueOf(d));

		trxnamount.setCurrency(currency);
		txnDetails.setAmount(trxnamount);
		transaction.setTxnDetails(txnDetails);

		// HistoricTxn block
		Request.Transaction.HistoricTxn historictxn = new Request.Transaction.HistoricTxn();
		historictxn.setAuthcode(authcode);
		historictxn.setReference(dtreference);
		historictxn.setMethod("fulfill");
		transaction.setHistoricTxn(historictxn);

		request.setTransaction(transaction);

		return request;
	}

	@SuppressWarnings("restriction")
	protected Response getFulfillResults(String merchantref, String amount,
			String authcode, String reference, String currency)
			throws JAXBException, XPathExpressionException {

		JAXBContext jc = null;
		Marshaller marshaller = null;
		try {
			jc = JAXBContext.newInstance(Request.class);
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Request request = createFulFillRequest(merchantref, amount, authcode,
				reference, currency);

		marshaller.marshal(request, System.out);

		ClientResponse response = getWebInitResource().accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class, request);

		String output1 = response.getEntity(String.class);
		System.out.println("check the request --- " + response.getStatus());
		System.out.println("check the response entity --- " + output1);

		com.mhe.rest.datacash.entity.Response st = getResponseObject(output1);

		System.out.println(" response entity --- " + st);
		System.out.println("check the response entity --- "
				+ st.getDatacashReference());

		Response response1 = null;
		if (response.getStatus() == 200) {
			return response1;
		} else {
			// logger.info("GET failed");

		}

		return response1;
	}

	private static Request createReAuthRequest(String merchantref,
			String amount, String dtreference, String currency) {

		System.out.println("merchantref value is   : " + merchantref);
		System.out.println("amount value is   : " + amount);
		System.out.println("datacash reference value is   : " + dtreference);
		System.out.println("currency value is   : " + currency);

		Request request = new Request();

		// Authentication Block
		Request.Authentication authentication = new Request.Authentication();
		authentication.setClient(CLIENT);
		authentication.setPassword(PASSWORD);
		request.setAuthentication(authentication);

		Request.Transaction transaction = new Request.Transaction();

		TxnDetails txnDetails = new TxnDetails();
		txnDetails.setMerchantreference(merchantref);
		TxnDetails.Amount trxnamount = new TxnDetails.Amount();

		double d = Double.parseDouble(amount);

		trxnamount.setValue(BigDecimal.valueOf(d));

		trxnamount.setCurrency(currency);
		txnDetails.setAmount(trxnamount);

		txnDetails.setCapturemethod("ecomm");

		transaction.setTxnDetails(txnDetails);

		// CardTxn block
		Request.Transaction.CardTxn cardtxn = new Request.Transaction.CardTxn();
		cardtxn.setMethod("pre");
		Request.Transaction.CardTxn.CardDetails carddetails = new Request.Transaction.CardTxn.CardDetails();
		carddetails.setType("preregistered");
		carddetails.setValue(dtreference);
		cardtxn.setCardDetails(carddetails);

		transaction.setCardTxn(cardtxn);

		request.setTransaction(transaction);

		return request;
	}

	@SuppressWarnings("restriction")
	protected Response getReAuthResults(String merchantref, String amount,
			String dtreference, String currency) throws JAXBException,
			XPathExpressionException {

		JAXBContext jc = null;
		Marshaller marshaller = null;
		try {
			jc = JAXBContext.newInstance(Request.class);
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Request request = createReAuthRequest(merchantref, amount, dtreference,
				currency);

		marshaller.marshal(request, System.out);

		ClientResponse response = getWebInitResource().accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class, request);

		String output1 = response.getEntity(String.class);
		System.out.println("check the request --- " + response.getStatus());
		System.out.println("check the response entity --- " + output1);

		com.mhe.rest.datacash.entity.Response st = getResponseObject(output1);

		System.out.println(" response entity --- " + st);
		System.out.println("check the response entity --- "
				+ st.getDatacashReference());

		Response response1 = null;
		if (response.getStatus() == 200) {
			return response1;
		} else {
			// logger.info("GET failed");

		}

		return response1;
	}

}
