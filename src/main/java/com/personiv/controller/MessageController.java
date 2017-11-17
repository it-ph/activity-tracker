package com.personiv.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.personiv.model.Greeting;
import com.personiv.model.Message;
import com.personiv.model.UserTask;


@Controller
public class MessageController {
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(Message message) throws Exception {
		Greeting g = new Greeting();
		g.setContent("Hello, " + message.getFrom() + "!");
		return g;
	}
	
	@MessageMapping("/user-status")
	@SendTo("/user-update/status")
	public UserTask userUpdate(UserTask  usertask) throws Exception {
		return usertask;
	}

}
