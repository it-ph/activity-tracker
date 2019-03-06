package com.personiv.service;

import com.personiv.dao.GroupDao;
import com.personiv.model.Group;
import com.personiv.model.GroupTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService
{
  @Autowired
  private GroupDao groupDao;
  
  public Group getGroupById(Long groupId)
  {
    return this.groupDao.getGroupById(groupId);
  }
  
  public List<Group> getGroups()
  {
    return this.groupDao.getGroups();
  }
  
  public void addGroup(Group group)
  {
    this.groupDao.addGroup(group);
  }
  
  public void editGroup(Group group)
  {
    this.groupDao.editGroup(group);
  }
  
  public void deleteGroup(Group group)
  {
    this.groupDao.deleteGroup(group);
  }
  
  public void addGroupMember(Long groupId, Long userId)
  {
    this.groupDao.addGroupMember(groupId, userId);
  }
  
  public void addGroupAdmin(Long groupId, Long userId)
  {
    this.groupDao.addGroupAdmin(groupId, userId);
  }
  
  public List<GroupTask> getGroupTask(Long id)
  {
    return this.groupDao.getGroupTask(id);
  }
  
  public void removeGroupAdmin(Long groupId, Long userId)
  {
    this.groupDao.removeGroupAdmin(groupId, userId);
  }
  
  public void removeGroupMember(Long groupId, Long userId)
  {
    this.groupDao.removeGroupMember(groupId, userId);
  }
}
