package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.SupervisorDao;
import com.personiv.model.Period;
import com.personiv.model.TaskHistory;
import com.personiv.model.TaskSummary;

@Service
public class SupervisorService {
	
	 @Autowired
	 private SupervisorDao supDao;
	 
	 public List<TaskHistory> getSubbordinateHistory(Period period, Long id){
		 return supDao.getSubbordinateHistory(period, id);
	 }
	 
	 public List<TaskSummary> getSubbordinateTaskSummary(Period period, Long id){
		 return supDao.getSubbordinateTaskSummary(period, id);
	 }
}
