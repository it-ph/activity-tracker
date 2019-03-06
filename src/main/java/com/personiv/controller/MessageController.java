package com.personiv.controller;

import com.personiv.model.EmployeeTask;
import com.personiv.model.Greeting;
import com.personiv.model.Group;
import com.personiv.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController
{
  @MessageMapping({"/hello"})
  @SendTo({"/topic/greetings"})
  public Greeting greeting(Message message)
    throws Exception
  {
    Greeting g = new Greeting();
    g.setContent("Hello, " + message.getFrom() + "!");
    return g;
  }
  
  @MessageMapping({"/user-status"})
  @SendTo({"/user-update/status"})
  public EmployeeTask userUpdate(EmployeeTask usertask)
    throws Exception
  {
    return usertask;
  }
  
  @MessageMapping({"/group-status"})
  @SendTo({"/user-update/group"})
  public Group groupUpdate(Group group)
    throws Exception
  {
    return group;
  }
}
