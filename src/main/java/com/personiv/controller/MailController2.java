package com.personiv.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MailController2 {
	
	@Autowired
	private JavaMailSender sender;

	@RequestMapping("/sendMail")
	public @ResponseBody String sendMail() {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);    
		
		try {
			
			helper.setTo("jerico.grijaldo@personiv.com");
			helper.setText("Test email2");
			helper.setSubject("Hi");
			sender.send(message);
			
		} catch (MessagingException e) {

			e.printStackTrace();
			return e.getMessage();
		}
		
		return "EMAIL SENT";

	}
	
}
