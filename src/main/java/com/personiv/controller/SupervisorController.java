package com.personiv.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.personiv.model.Period;
import com.personiv.model.TaskHistory;
import com.personiv.model.TaskSummary;
import com.personiv.model.User;
import com.personiv.service.SupervisorService;
import com.personiv.service.UserService;


@CrossOrigin(origins = "*")
@RestController
public class SupervisorController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SupervisorService supService;
	
	
	
    @RequestMapping(path={"/supervisor/subbordinate_history"}, method =RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSubbordinateTaskHistory(@RequestBody Period period, Principal principal){
    	
    	User user  = userService.getUserByUsername(principal.getName());
//    	List<TaskHistory> history = supService.getSubbordinateHistory(period, user.getId());
    	List<TaskSummary> summary = supService.getSubbordinateTaskSummary(period, user.getId());
    	
		return ResponseEntity.ok(summary);
	}
}
