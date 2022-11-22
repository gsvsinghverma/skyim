package com.adv.util;

	import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.adv.model.User;
	
	public class ExcelHelper {
		
		
		
		
	  public ExcelHelper() {
			super();
					}

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "First_Name", "Last_Name", "User_Name", "LoginUserName", "EmailId", "MobileNumber", "Password", "DisplayName", "OtpType", "CustomValue", "OtpEnable", "OutBoundCidValue"};
	  static String SHEET = "Users";

		public static ByteArrayInputStream userToExcel(List<User> userrequest) {
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	      // Header
	      Row headerRow = sheet.createRow(0);
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	      
	 
	      
	      
	      int rowIdx = 1;
	      for (User userrequests : userrequest) {
	        Row row = sheet.createRow(rowIdx++);
	        row.createCell(0).setCellValue(userrequests.getId());

	        row.createCell(1).setCellValue(userrequests.getFirstName());
	        row.createCell(2).setCellValue(userrequests.getLastName());
	        row.createCell(3).setCellValue(userrequests.getUsername());
	        row.createCell(4).setCellValue(userrequests.getLoginUsername());
	        row.createCell(5).setCellValue(userrequests.getEmailId());
	        row.createCell(6).setCellValue(userrequests.getMobileNumber());

	        row.createCell(7).setCellValue(userrequests.getPassword());
	        row.createCell(8).setCellValue(userrequests.getDisplayName());
	        row.createCell(9).setCellValue(userrequests.getOtpType());
	        		 row.createCell(10).setCellValue(userrequests.getCustomValue());
	        		 row.createCell(11).setCellValue(userrequests.isOtpEnable());
	        		 row.createCell(12).setCellValue(userrequests.getOutboundCid());


	      }
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
