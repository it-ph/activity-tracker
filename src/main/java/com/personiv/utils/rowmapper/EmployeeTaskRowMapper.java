package com.personiv.utils.rowmapper;

import com.personiv.model.Employee;
import com.personiv.model.EmployeeTask;
import com.personiv.model.Task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeTaskRowMapper
  implements RowMapper<EmployeeTask>
{
  public EmployeeTask mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    Long id = Long.valueOf(rs.getLong("id"));
    String empNumber = rs.getString("employeeNumber");
    String empFName = rs.getString("firstName");
    String empMName = rs.getString("middleName");
    String empLName = rs.getString("lastName");
    String empSuffix = rs.getString("suffix");
    String email = rs.getString("email");
    String position = rs.getString("position");
    String dept = rs.getString("department");
    Date empCreated = rs.getTimestamp("createdAt");
    Date empUpdated = rs.getTimestamp("updatedAt");
    String remarks = rs.getString("remarks");
    Long taskId = Long.valueOf(rs.getLong("taskId"));
    String taskName = rs.getString("taskName");
    Date taskCreated = rs.getTimestamp("taskCreated");
    Date taskUpdated = rs.getTimestamp("taskUpdated");
    
    Date startDate = rs.getTimestamp("startDate");
    Date endDate = rs.getTimestamp("endDate");
    
    Employee emp = new Employee(id, empNumber, empFName, empMName, empLName, empSuffix, email, position, dept, empCreated, empUpdated);
    
    Task task = new Task(taskId, taskName, taskCreated, taskUpdated);
    
    EmployeeTask empTask = new EmployeeTask(id, startDate, endDate, emp, task, remarks);
    return empTask;
  }
}
