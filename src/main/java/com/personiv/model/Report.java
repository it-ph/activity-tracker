package com.personiv.model;

import java.util.List;

import lombok.Data;

@Data
public class Report {
	private String title;
	private List<TaskReport> tasks;
}
