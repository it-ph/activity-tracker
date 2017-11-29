package com.personiv.model;

import lombok.Data;

@Data
public class ReportFile {
	private String fileName;
	private byte[] contents;
}
