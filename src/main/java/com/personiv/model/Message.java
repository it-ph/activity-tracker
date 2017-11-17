package com.personiv.model;

import lombok.Data;

@Data
public class Message {
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	private String from;
	private String to;
}
