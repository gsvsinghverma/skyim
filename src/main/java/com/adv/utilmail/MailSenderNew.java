package com.adv.utilmail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.adv.util.Constant;

public class MailSenderNew implements Runnable {


	String receiverEmail;
	String emailUsernameFrom;
	String emailPasswordFrom;
	String mailBody;
	String mailSubject;

	public MailSenderNew(String receiverEmail, String emailUsernameFrom, String emailPasswordFrom, String mailBody,
			String mailSubject) {
		this.receiverEmail = receiverEmail;
		this.emailUsernameFrom = emailUsernameFrom;
		this.emailPasswordFrom = emailPasswordFrom;
		this.mailBody = mailBody;
		this.mailSubject = mailSubject;
	}

	@Override
	public void run() {
		try {
			this.MailVerification(receiverEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int MailVerification(String receiverEmail) {
		int resp = 0;

		Properties props = new Properties();

		props.put("mail.smtp.host", Constant.CHANGEPASSEMAILSMTPHOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", Constant.CHANGEPASSEMAILSMTPPORT);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.debug", "false");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUsernameFrom, emailPasswordFrom);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

			message.setSubject(mailSubject);
			message.setFrom(new InternetAddress(emailUsernameFrom));

			message.setContent(mailBody, "text/html");
			Transport.send(message);
			resp = 1;
		} catch (MessagingException e) {
			resp = 0;
			e.printStackTrace();
		}
		return resp;
	}

}