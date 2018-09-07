package com.ahmed.utils.mail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailUtil {

	private Properties props = null;
	private Session session = null;

	public GMailUtil() {
		load();

	}

	public void sendMail(String to, String subject, String body) {

		if (session == null) {
			getSession();
		}

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("from")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void load() {

		props = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			props.load(input);
			
//			props.put("mail.smtp.host", "smtp.gmail.com");
//			props.put("mail.smtp.socketFactory.port", "465");
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.port", "465");
//
//			props.put("from", "shabeer@olivecrypto.com");
//			props.put("username", "shabeer@olivecrypto.com");
//			props.put("password", "password");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void getSession() {
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
			}
		});
	}

	public static void main(String[] args) {

		GMailUtil emailUtil = new GMailUtil();

		String to = "sivareddy.g@olivecrypto.com";
		String subject = "Testing Subject";
		String body = "Dear Customer," + "\n\n No spam to my email, please!";

		emailUtil.sendMail(to, subject, body);
	}
}
