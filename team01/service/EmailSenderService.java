package com.seven.team01.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.seven.team01.vo.EmailVO;

@Service
public class EmailSenderService {
	@Autowired
	protected JavaMailSender mailSender;

	public void SendEmail(EmailVO emailVO) throws Exception {

		MimeMessage msg = mailSender.createMimeMessage();
		try {
			msg.setSubject(emailVO.getSubject());
			msg.setText(emailVO.getContent());
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(emailVO.getReceiver()));

		} catch (MessagingException e) {
			System.out.println("MessagingException");
			e.printStackTrace();
		}

		try {
			mailSender.send(msg);
		} catch (MailException e) {
			System.out.println("MailException 발생");
			e.printStackTrace();
		}

	}

}
