package com.mhe.rest.datacash;

import java.math.BigDecimal;

import com.mhe.rest.datacash.request.entity.Request;
import com.mhe.rest.datacash.request.entity.TxnDetails;

public class DataCashFulFillRequest {

	
	static Request createFulFillRequest(String merchantref, String amount, String authcode, String dtreference,
			String currency) {

		System.out.println("merchantref value is   : " + merchantref);
		System.out.println("amount value is   : " + amount);
		System.out.println("authcode value is   : " + authcode);
		System.out.println("Query reference value is   : " + dtreference);
		System.out.println("currency value is   : " + currency);

		Request request = new Request();

		// Authentication Block
		request.setAuthentication(DataCashAbstractService.getAuthenticationObject());
	

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
}
