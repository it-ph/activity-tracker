package com.personiv.model;

import lombok.Data;

@Data
public class Greeting {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
