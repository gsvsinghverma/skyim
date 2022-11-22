package com.adv.utilmail;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adv.model.Admin;
import com.adv.model.MailConfiguration;
import com.adv.repository.AdminRepository;
import com.adv.serviceimpl.AdminServiceImpl;
import com.adv.util.Constant;

public class OutlookMailService {

	@Autowired
	AdminRepository adminRepository;
	

	AdminServiceImpl adminServiceImpl=new AdminServiceImpl();
	String username1="";
	String password1="";
	
	
	public OutlookMailService(AdminRepository adminRepository, AdminServiceImpl adminServiceImpl) {
		super();
		this.adminRepository = adminRepository;
		this.adminServiceImpl = adminServiceImpl;
	}



	public OutlookMailService() {
		

	}

	
	public HashMap<Admin,String> MailVerification(String receiverEmail,String username, Admin admin, List<MailConfiguration> mailconfig) throws MessagingException {
	
		HashMap<Admin,String> map=new HashMap<>();
		
		String msg="";

		Properties props = new Properties();
	
		PasswordGeneratorPPolicy pwdpolicy=new PasswordGeneratorPPolicy();

		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); 
		for(MailConfiguration mailconfig1:mailconfig) {
		if(mailconfig1.getParameters().equals(Constant.HOST_OUTLOOK)) {
		props.put("mail.smtp.host",mailconfig1.getParametervalues());
		}
		if(mailconfig1.getParameters().equals(Constant.PORT_OUTLOOK)) {
		props.put("mail.smtp.port",mailconfig1.getParametervalues());
		}
		if(mailconfig1.getParameters().equals(Constant.MAIL_USERNAME)) {
			username1=mailconfig1.getParametervalues();
		}
		if(mailconfig1.getParameters().equals(Constant.MAIL_PASSWORD)) {
			password1=mailconfig1.getParametervalues();
		}
		
		}
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username1, password1);
			}
		});
		
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.debug", "true");
		String subject="Mail for Password";
		String password=pwdpolicy.generateStrongPassword();
		String pwdupdate="";
		pwdupdate= DigestUtils.md5Hex(password);
		String messageText="Password for username is : "+username+" and  password is : "+password;
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username1));
			message.setRecipient(Message.RecipientType.TO,new InternetAddress(receiverEmail));
			
            message.setSubject(subject);
            message.setContent(messageText, "text/html");
            admin.setPassword(pwdupdate);
            
            Transport.send(message);
            msg="Password Successfully sent in mail to the user with name: "+username;
            map.put(admin, msg);
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	
}
	
	
	