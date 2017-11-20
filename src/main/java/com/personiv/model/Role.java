package com.personiv.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	private Long id;
	private String role;
	private Date createdAt;
	private Date updatedAt;
}
