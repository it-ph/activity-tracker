package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.personiv.dao.GroupDao;
import com.personiv.model.Group;
import com.personiv.model.GroupTask;

@Service
public class GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	public Group getGroupById(Long groupId) {
		return groupDao.getGroupById(groupId);
	}
	public List<Group> getGroups(){
		return groupDao.getGroups();
	}

	
	public void addGroup(Group group) {
		groupDao.addGroup(group);
	}
	public void editGroup(Group group) {
		groupDao.editGroup(group);
		
	}
	public void deleteGroup(Group group) {
		groupDao.deleteGroup(group);
	}
	
	public void addGroupMember(Long groupId,Long userId) {
		groupDao.addGroupMember(groupId, userId);
	}
	
	public void addGroupAdmin(Long groupId,Long userId) {
		groupDao.addGroupAdmin(groupId, userId);
	}
	public List<GroupTask> getGroupTask(Long id) {
		return groupDao.getGroupTask(id);
	}
	public void removeGroupAdmin(Long groupId, Long userId) {
		groupDao.removeGroupAdmin(groupId,userId);
		
	}
	public void removeGroupMember(Long groupId, Long userId) {
		groupDao.removeGroupMember(groupId,userId);
		
	}
	
	

	
	
	
	
}
