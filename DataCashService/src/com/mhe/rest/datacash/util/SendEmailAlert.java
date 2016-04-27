package com.mhe.rest.datacash.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.mhe.rest.datacash.exception.AppException;

public class SendEmailAlert {

	protected static Logger logger = LoggerUtility
			.getLogger(SendEmailAlert.class.getName());

	private static final String SMTPHost = (PropertiesUtil.readProperty())
			.getProperty("mail.smtp.host");
	private static final String SMTPPort = (PropertiesUtil.readProperty())
			.getProperty("mail.smtp.port");
	private static final String mailFrom = (PropertiesUtil.readProperty())
			.getProperty("mail.from");
	private static final String mailTo = (PropertiesUtil.readProperty())
			.getProperty("mail.to");
	private static final String mailCc = (PropertiesUtil.readProperty())
			.getProperty("mail.cc");
	private static final String mailBcc = (PropertiesUtil.readProperty())
			.getProperty("mail.bcc");
	private static final String mailSubject = (PropertiesUtil.readProperty())
			.getProperty("mail.subject");

	public static void sendEmail(Throwable exception, String subject,
			String request, HashMap keys) {

		logger.debug("Inside send email block.");
		try {
			// Get system properties
			Properties properties = System.getProperties();

			properties.put("mail.smtp.host", SMTPHost);
			properties.put("mail.smtp.port", SMTPPort);

			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties, null);

			// Define message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			if (!"".equals(mailTo) || mailTo != null)
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(mailTo));
			if (!"".equals(mailCc) || mailCc != null)
				message.addRecipients(Message.RecipientType.CC,
						InternetAddress.parse(mailCc));
			if (!"".equals(mailBcc) || mailBcc != null)
				message.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(mailBcc));

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			message.setSubject(subject);
			StringBuffer sb = new StringBuffer();
			printKeys(keys, sb);
			sb.append("REQUEST: \n");
			sb.append(request);
			messageBodyPart.setText(sb.toString());
			if (exception != null) {
				StringWriter errors = new StringWriter();
				exception.printStackTrace(new PrintWriter(errors));
				messageBodyPart.setText(errors.toString());
			}
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);
			logger.info("Error message sent");
		} catch (Exception e) {
			logger.error("Error emailing: " + e.getMessage());
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				logger.error(ste);
			}
		}
	}

	public static void sendEmail(AppException ex) {

		logger.debug("Inside send email block.");
		try {
			Message message=getMessage();
			BodyPart messageBodyPart = new MimeBodyPart();
			StringBuffer messageBuffer=new StringBuffer();
			if (!"".equals(ex.getOrderId()) || ex.getOrderId() != null) {
				messageBuffer
						.append("DataCash HPP Service Failed with ERROR: \n"
								+ ex.getMessage()
								+ "\n ORDER_ID: "
								+ ex.getOrderId()
								+ "\n ERROR_CODE: "
								+ ex.getCode() + "\n " + ex.getConsumerRequest());
				message.setSubject(mailSubject + " for Order ID: "
						+ ex.getOrderId());
			} else {

				message.setSubject(mailSubject);
				messageBuffer
				.append("DataCash HPP Service Failed with ERROR: \n"
								+ ex.getMessage()
								+ " With Error Code: "
								+ ex.getCode());
			}
			messageBodyPart.setText(messageBuffer.toString());
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("Error message sent");
		} catch (Exception e) {
			logger.error("ERROR SENDING EMAIL:" ,e);
		}
	}

	private static void printKeys(HashMap keys, StringBuffer stringBuffer)
			throws MessagingException {
		Set keyset = keys.keySet();
		Iterator iterator = keyset.iterator();
		String key = null;
		stringBuffer.append("KEYS: \n");
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			stringBuffer.append(key + "=" + keys.get(key) + "\n");
		}
	}

	public static Message getMessage()
	{
		Message message=null;
		try
		{
		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", SMTPHost);
		properties.put("mail.smtp.port", SMTPPort);
		Session session = Session.getDefaultInstance(properties, null);
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailFrom));
		if (!"".equals(mailTo) || mailTo != null)
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(mailTo));
		if (!"".equals(mailCc) || mailCc != null)
			message.addRecipients(Message.RecipientType.CC,
					InternetAddress.parse(mailCc));
		if (!"".equals(mailBcc) || mailBcc != null)
			message.addRecipient(Message.RecipientType.BCC,
					new InternetAddress(mailBcc));
	} catch (Exception e) {
		logger.error("ERROR SENDING EMAIL:" ,e);
	}
		return message;
	}
	public static void sendEmail(Throwable exception, String errMsg,
			int errCode, String orderID) {

			Message message=getMessage();
		logger.debug("Inside send email block.");
		try {
		
			if (!"".equals(orderID) || orderID != null)
				message.setSubject(mailSubject + " for Order ID: " + orderID);
			else
				message.setSubject(mailSubject);
			BodyPart messageBodyPart = new MimeBodyPart();
			StringBuffer messageBuffer=new StringBuffer();
			if (!"".equals(orderID) || orderID != null)
				messageBuffer.append("DataCash HPP Service Failed with ERROR: \n"
								+ errMsg
								+ " for OrderID: "
								+ orderID
								+ " With Error Code: " + errCode);
			else
				messageBuffer.append("DataCash HPP Service Failed with ERROR: \n"
								+ errMsg + " With Error Code: " + errCode);
			if (exception != null) {
				StringWriter errors = new StringWriter();
				exception.printStackTrace(new PrintWriter(errors));
				messageBuffer.append(errors.toString());
			}
			messageBodyPart.setText(messageBuffer.toString());
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("Error message sent");
		} catch (Exception e) {
			logger.error("ERROR IN SENDING EMAIL:" ,e);
		}
	}

}
