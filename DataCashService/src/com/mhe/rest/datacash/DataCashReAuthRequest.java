package com.mhe.rest.datacash;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.request.entity.Request;
import com.mhe.rest.datacash.request.entity.TxnDetails;
import com.mhe.rest.datacash.util.LoggerUtility;

public class DataCashReAuthRequest {

	protected static Logger logger = LoggerUtility.getLogger(DataCashReAuthRequest.class.getName());
	
	static Request createReAuthRequest(String merchantref, String amount, String dtreference, String currency) {

		System.out.println("merchantref value is   : " + merchantref);
		System.out.println("amount value is   : " + amount);
		System.out.println("datacash reference value is   : " + dtreference);
		System.out.println("currency value is   : " + currency);

		Request request = new Request();

		request.setAuthentication(DataCashAbstractService.getAuthenticationObject());

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
}
