package com.personiv.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.personiv.model.Report;
import com.personiv.model.TaskReport;


public class ExcelBuilder{
	
	private Workbook workbook;
	private Sheet sheet = null;
	private Row row = null;
	
	private Report report;
	
	
	public ExcelBuilder(Report report) {
		this.report = report;
		createReport();
	}
	
	private void createReport() {
		 workbook = new XSSFWorkbook();
		 sheet = workbook.createSheet(report.getTitle());
		 
		 row = sheet.createRow(0);
		 sheet.setColumnWidth(0, 8200);
		 sheet.setColumnWidth(1, 8200);
		 sheet.setColumnWidth(2, 8200);
		 sheet.setColumnWidth(3, 8200);
		 sheet.setColumnWidth(4, 8200);
		 sheet.setColumnWidth(5, 8200);
				 
		 CellStyle cellStyle = workbook.createCellStyle();
		 CellStyle cellStyle2 = workbook.createCellStyle();
		 
		 cellStyle.setBorderBottom((short)2);
		 cellStyle.setBorderLeft((short)2);
		 cellStyle.setBorderRight((short)2);
		 cellStyle.setBorderTop((short)2);
		 
		 cellStyle2.setBorderBottom((short)1);
		 cellStyle2.setBorderLeft((short)1);
		 cellStyle2.setBorderRight((short)1);
		 cellStyle2.setBorderTop((short)1);
		 
		 
		 Cell cell1 = row.createCell(0);
		 cell1.setCellValue("Employee Number");
		 cell1.setCellStyle(cellStyle);
		 
		 
		 Cell cell2 = row.createCell(1);
		 cell2.setCellValue("Employee");
		 cell2.setCellStyle(cellStyle);
		 
		 Cell cell3 = row.createCell(2);
		 cell3.setCellValue("Task");
		 cell3.setCellStyle(cellStyle);
		 
		 Cell cell4 = row.createCell(3);
		 cell4.setCellValue("Start");
		 cell4.setCellStyle(cellStyle);

		 Cell cell5 = row.createCell(4);
		 cell5.setCellValue("End");
		 cell5.setCellStyle(cellStyle);

		 Cell cell6 = row.createCell(5);
		 cell6.setCellValue("Duration");
		 cell6.setCellStyle(cellStyle);
		 
		 int rowIndex =1;
		 
		 SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		 
		 for(TaskReport et:  report.getTasks()) {
			 
			 Row  row = sheet.createRow(rowIndex);
			
			 Cell rowCell0 = row.createCell(0);		 
			 rowCell0.setCellValue(et.getEmployeeNumber());
			 rowCell0.setCellStyle(cellStyle2);
			 
			 Cell rowCell1 = row.createCell(1);
			 rowCell1.setCellValue(et.getEmployee());
			 rowCell1.setCellStyle(cellStyle2);
			 
			 Cell rowCell2 = row.createCell(2);
			 rowCell2.setCellValue(et.getTask());
			 rowCell2.setCellStyle(cellStyle2);
			 
			 Cell rowCell3 = row.createCell(3);
			 rowCell3.setCellValue(format.format(et.getStart()));
			 rowCell3.setCellStyle(cellStyle2);
			 
			 Cell rowCell4 = row.createCell(4);
			 if(et.getEnd() !=null) rowCell4.setCellValue(format.format(et.getEnd()));
			 rowCell4.setCellStyle(cellStyle2);
			 
			 
			 Cell rowCell5 = row.createCell(5);
			 rowCell5.setCellValue(et.getDuration());
			 rowCell5.setCellStyle(cellStyle2);
			 
			 rowIndex++;
		 }
	
	}
	
	public byte[] getBytes() {
		
	  ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(bos);
        }
       return bos.toByteArray();
	}
}