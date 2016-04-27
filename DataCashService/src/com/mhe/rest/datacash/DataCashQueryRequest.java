package com.mhe.rest.datacash;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.request.entity.Request;
import com.mhe.rest.datacash.util.LoggerUtility;

public class DataCashQueryRequest {

	protected static Logger logger = LoggerUtility.getLogger(DataCashQueryRequest.class.getName());
	
	static Request createQueryRequest(String reference) {

		logger.info("Within the createQueryRequest method");

		Request request = new Request();
		request.setAuthentication(DataCashAbstractService.getAuthenticationObject());

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

	
}
