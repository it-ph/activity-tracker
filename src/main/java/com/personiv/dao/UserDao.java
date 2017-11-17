package com.personiv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.model.User;
import com.personiv.utils.rowmapper.UserRowMapper;


@Repository
@Transactional(readOnly = false)
public class UserDao extends JdbcDaoSupport{
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
   
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
	public List<User>getUsers(){
		String query = "call _proc_getUsers()";
		List<User> users =jdbcTemplate.query(query,new UserRowMapper());
		return users;
	}
	
	public User getUserByUsername(String username) {
		
		String query = "call _proc_getUserByUsername(?)";
		User user = jdbcTemplate.queryForObject(query,new Object[] {username}, new UserRowMapper());	
		return user;
	}

	public List<User> getNonAdminUsers() {
		String query ="Call _proc_getNonAdminUsers()";
		
		List<User> users = jdbcTemplate.query(query,new BeanPropertyRowMapper<User>(User.class));		
		return users;
	}
	

	
	public void addEmployeeAccount(EmployeeUser empUser) {
		String query = "INSERT INTO users(username,password) VALUES(?,?)";
		
		String query2 ="INSERT INTO user_roles(userId,role) VALUES(?,?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	 	jdbcTemplate.update(
		    	new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						
						ps.setString(1, empUser.getUser().getUsername());
						ps.setString(2, empUser.getUser().getPassword());
						return ps;
					}
		    		
		    	},
		    	keyHolder);
	 	
	 	Long id = keyHolder.getKey().longValue();
	 	
	 	jdbcTemplate.update(query2,new Object[] {id,empUser.getUser().getRole().getId()}); 
	 	
	}
	
	public void resetPassword(User user) {
		String query = "call _proc_resetPassword(?,?)";
		jdbcTemplate.update(query,new Object[] {user.getId(),passwordEncoder().encode("!welcome10")});
		
	}

	public List<Role> getRoles() {
		String sql ="SELECT * FROM roles WHERE roles.role != 'ADMIN'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
	}
	
	


	
}
