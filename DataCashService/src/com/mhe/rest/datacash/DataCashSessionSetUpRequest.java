package com.mhe.rest.datacash;

import java.math.BigDecimal;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import com.mhe.rest.datacash.request.entity.Request;
import com.mhe.rest.datacash.request.entity.TxnDetails;
import com.mhe.rest.datacash.util.LoggerUtility;

public class DataCashSessionSetUpRequest {

	protected static Logger logger = LoggerUtility.getLogger(DataCashSessionSetUpRequest.class.getName());

	static Request createSessionSetUpRequest(MultivaluedMap <String, String> queryParams, int PAGE_SET_ID,
			String RETURN_URL, String EXPIRY_URL) {

		logger.info("Within the createSessionSetUpRequest method ");
		Request request = new Request();
		request.setAuthentication(DataCashAbstractService.getAuthenticationObject());

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
					logger.info("amount is::" + queryParams.getFirst(key));
					amount.setValue(BigDecimal.valueOf(Double.parseDouble(queryParams.getFirst(key))));
				}
				if ("currency".equals(key)) {
					logger.info("currency is::" + queryParams.getFirst(key));
					amount.setCurrency(queryParams.getFirst(key));
				}

			}
		}

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

}
