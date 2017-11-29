package com.personiv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personiv.model.Employee;
import com.personiv.model.EmployeeTask;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Period;
import com.personiv.model.Report;
import com.personiv.model.ReportFile;
import com.personiv.model.Task;
import com.personiv.model.User;
import com.personiv.model.UserTask;
import com.personiv.service.UserService;
import com.personiv.service.UserTaskService;
import com.personiv.utils.ExcelBuilder;
import com.personiv.utils.JwtTokenUtil;

@RestController
public class UserTaskController {
	
	@Autowired
	private UserTaskService empTaskService;
	
	@Autowired
	private UserService userService;
	
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@RequestMapping(path= {"/employee-tasks","/employee-tasks/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserTask> getUserTasks(){
		return empTaskService.getUserTasks();
	}
	
	@RequestMapping(path= {"/employee-tasks/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserTask getUserTask(@PathVariable("id") Long id){
		try {

			return empTaskService.getUserTask(id);
			
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	@RequestMapping(path= "/employee-tasks/history", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTaskHistory(@RequestBody Period period){
		
		//return ResponseEntity.ok(period);
		List<EmployeeTask> userTasks = empTaskService.getTaskHistory(period);
		return ResponseEntity.ok(userTasks);
		
	}
	
	@RequestMapping(path= {"/employee-tasks/myTasks"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMyTasks(@RequestHeader(value="Authorization")String requestHeader){
	User user = null;
		
		if(requestHeader.startsWith("Bearer ")) {
			
			String authToken = requestHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(authToken);	
			
			try {
				System.out.println("USERNAME: "+username);
				user = userService.getUserByUsername(username);		
				
				System.out.println("USER: "+user);	
				

				return ResponseEntity.ok(empTaskService.getUserTaskByUserId(user.getId()));
				
			}catch(EmptyResultDataAccessException e){
				return null;
			}		
			
		}else {
			return ResponseEntity.status(402).body(new ErrorResponse("Authorization required",null));
		}
	}
	
	@RequestMapping(path= {"/employee-tasks/addTask"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTask(@RequestHeader(value="Authorization")String requestHeader,@RequestBody Task task){
		
		User user = null;
		
		if(requestHeader.startsWith("Bearer ")) {
			
			String authToken = requestHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(authToken);	
			
			try {
				System.out.println("USERNAME: "+username);
				user = userService.getUserByUsername(username);		
				
				System.out.println("USER: "+user);	
				
				empTaskService.addUserTask(user.getId(), task.getId());
				
			}catch(EmptyResultDataAccessException e){
				return ResponseEntity.status(422).body("No user found");
			}
			
			
		}else {
			return ResponseEntity.status(401).body("Not authorized");
		}
		return ResponseEntity.ok().body(null);
	}
	
	
	@RequestMapping(path= {"/employee-tasks/endTask"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> endUserTask(@RequestBody UserTask empTask){
		empTaskService.endUserTask(empTask);
		return ResponseEntity.ok(empTask);
	}
	
//	@RequestMapping(path= "/employee-tasks/report/download", method = RequestMethod.GET)
//	public void dowloadReport( HttpServletResponse response){
//		
//		Report report = new Report();
//		report.setTitle("test");
//		
//		List<EmployeeTask> tasks = new ArrayList<EmployeeTask>();
//		
//		EmployeeTask et = new EmployeeTask();
//		Employee emp = new Employee();
//		emp.setEmployeeNumber("10072101");
//		emp.setFirstName("Jerico");
//		emp.setLastName("Grijaldo");
//		
//		Task t = new Task();
//		t.setTaskName("Coding");
//		
//		
//		et.setEmployee(emp);
//		et.setTask(t);
//		et.setStartDate(new Date());
//		et.setEndDate(new Date());
//		
//		tasks.add(et);
//		
//		report.setTasks(tasks);
//		
//		ExcelBuilder  builder = new ExcelBuilder(report);
//	    
//		byte[] reportBytes = builder.getBytes();
//		 
//		
//		try {
//			
//			response.getOutputStream().write(reportBytes);
//	        response.setContentType("application/vnd.ms-excel");
//	        response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");
//	        response.getOutputStream().close();
//	        
//	
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	
//	}
	
	@RequestMapping(path= "/employee-tasks/report/download", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> dowloadReport(@RequestBody Report report,HttpServletResponse response){
		
		
//		List<EmployeeTask> tasks = new ArrayList<EmployeeTask>();
//		
//		EmployeeTask et = new EmployeeTask();
//		Employee emp = new Employee();
//		emp.setEmployeeNumber("10072101");
//		emp.setFirstName("Jerico");
//		emp.setLastName("Grijaldo");
//		
//		Task t = new Task();
//		t.setTaskName("Coding");
//		
//		
//		et.setEmployee(emp);
//		et.setTask(t);
//		et.setStartDate(new Date());
//		et.setEndDate(new Date());
//		
//		tasks.add(et);
//		
//		report.setTasks(tasks);
		
		ExcelBuilder  builder = new ExcelBuilder(report);
	    
		byte[] reportBytes = builder.getBytes();
		 
		ReportFile rf = new ReportFile();
		rf.setContents(reportBytes);
		return ResponseEntity.ok(rf);
	}
	
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> videoSimple(final HttpSession session) throws IOException {

	    ClassPathResource classPathResource = new ClassPathResource("/video2.mov");
	    return ResponseEntity
	            .ok()
	            .contentLength(classPathResource.contentLength())
	            .contentType(
	                    MediaType.parseMediaType("application/octet-stream"))
	            .body(new InputStreamResource(classPathResource.getInputStream()));
	}
}
