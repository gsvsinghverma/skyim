package com.adv.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.adv.model.Admin;
import com.adv.model.AuditLog;
import com.adv.model.User;

public class ExcelUtil {
	
	

	public ExcelUtil() {
		super();
			}

	static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

	public static byte[] downloadAuditLogsFile(List<AuditLog> auditLogs) {

		try (XSSFWorkbook workbook = new XSSFWorkbook();){
			XSSFSheet sheet = workbook.createSheet("AuditLogs");

			for (int i = 0; i < 7; i++) {
				sheet.setColumnWidth(i, 7000);
			}

			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			XSSFFont xSSFFont = workbook.createFont();

			xSSFFont.setBold(true);
			xSSFFont.setFontHeightInPoints((short) 14);
			xSSFFont.setColor(IndexedColors.RED.getIndex());

			cellStyle.setFont(xSSFFont);

			XSSFRow rowhead = sheet.createRow((short) 0);

			XSSFCell cell0 = rowhead.createCell(0);
			cell0.setCellValue(new XSSFRichTextString("S.No."));
			cell0.setCellStyle(cellStyle);

			XSSFCell cell1 = rowhead.createCell(1);
			cell1.setCellValue(new XSSFRichTextString("Module_Name"));
			cell1.setCellStyle(cellStyle);

			XSSFCell cell2 = rowhead.createCell(2);
			cell2.setCellValue(new XSSFRichTextString("Description"));
			cell2.setCellStyle(cellStyle);

			XSSFCell cell3 = rowhead.createCell(3);
			cell3.setCellValue(new XSSFRichTextString("User_Name"));
			cell3.setCellStyle(cellStyle);

			XSSFCell cell4 = rowhead.createCell(4);
			cell4.setCellValue(new XSSFRichTextString("Creation_Date"));
			cell4.setCellStyle(cellStyle);

			XSSFCell cell5 = rowhead.createCell(5);
			cell5.setCellValue(new XSSFRichTextString("Type"));
			cell5.setCellStyle(cellStyle);

			XSSFCell cell6 = rowhead.createCell(6);
			cell5.setCellValue(new XSSFRichTextString("Action"));
			cell5.setCellStyle(cellStyle);

			XSSFCellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2 = workbook.createCellStyle();
			cellStyle2.setWrapText(true);

			int k = 1;

			for (AuditLog auditlog : auditLogs) {
				XSSFRow row = sheet.createRow((short) k);
				row.setRowStyle(cellStyle2);
				row.createCell(0).setCellValue(k);
				row.createCell(1).setCellValue(auditlog.getModuleName().trim());
				row.createCell(2).setCellValue(auditlog.getDescription().trim());
				row.createCell(3).setCellValue(auditlog.getUserName().trim());
				row.createCell(4).setCellValue(String.valueOf(auditlog.getCreationDate()).trim());
				row.createCell(5).setCellValue(auditlog.getAction().trim());
				String type = "";
				if (auditlog.getWebmobile().trim().equalsIgnoreCase("1"))
					type = "WEB";
				else if (auditlog.getWebmobile().trim().equalsIgnoreCase("2"))
					type = "MOBILE";
				row.createCell(6).setCellValue(type.trim());

				k++;

			}

			try (ByteArrayOutputStream bout = new ByteArrayOutputStream();){
				workbook.write(bout);
				workbook.close();
				return bout.toByteArray();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] downloadAdmins(List<Admin> admins) {

		try (XSSFWorkbook workbook = new XSSFWorkbook();) {
			XSSFSheet sheet = workbook.createSheet("shieldcryptAdminManagement");

			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 7000);
			sheet.setColumnWidth(5, 7000);
			sheet.setColumnWidth(6, 6000);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			XSSFFont xSSFFont = workbook.createFont();

			xSSFFont.setBold(true);
			xSSFFont.setFontHeightInPoints((short) 14);
			xSSFFont.setColor(IndexedColors.RED.getIndex());

			cellStyle.setFont(xSSFFont);

			XSSFRow rowhead = sheet.createRow((short) 0);

			XSSFCell cell0 = rowhead.createCell(0);
			cell0.setCellValue(new XSSFRichTextString("S.No."));
			cell0.setCellStyle(cellStyle);

			XSSFCell cell1 = rowhead.createCell(1);
			cell1.setCellValue(new XSSFRichTextString("User_Name"));
			cell1.setCellStyle(cellStyle);

			XSSFCell cell2 = rowhead.createCell(2);
			cell2.setCellValue(new XSSFRichTextString("First_Name"));
			cell2.setCellStyle(cellStyle);

			XSSFCell cell3 = rowhead.createCell(3);
			cell3.setCellValue(new XSSFRichTextString("Last_Name"));
			cell3.setCellStyle(cellStyle);

			XSSFCell cell4 = rowhead.createCell(4);
			cell4.setCellValue(new XSSFRichTextString("Mobile_No"));
			cell4.setCellStyle(cellStyle);

			XSSFCell cell5 = rowhead.createCell(5);
			cell5.setCellValue(new XSSFRichTextString("Email"));
			cell5.setCellStyle(cellStyle);

			XSSFCell cell6 = rowhead.createCell(6);
			cell6.setCellValue(new XSSFRichTextString("Active_Status"));
			cell6.setCellStyle(cellStyle);

			XSSFCellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2 = workbook.createCellStyle();
			cellStyle2.setAlignment(HorizontalAlignment.LEFT);
			cellStyle2.setWrapText(true);

			int k = 1;

			for (Admin admin : admins) {
				XSSFRow row = sheet.createRow((short) k);
				row.setRowStyle(cellStyle2);
				XSSFCell t2 = row.createCell(0);
				t2.setCellStyle(cellStyle2);
				t2.setCellValue(k);
				row.createCell(1).setCellValue(admin.getUsername().trim());
				row.createCell(2).setCellValue(admin.getFirstName().trim());
				row.createCell(3).setCellValue(admin.getLastName().trim());
				row.createCell(4).setCellValue(admin.getMobileNumber().trim());
				row.createCell(5).setCellValue(admin.getEmail().trim());
				if (admin.isActive())
					row.createCell(6).setCellValue("Yes");
				else
					row.createCell(6).setCellValue("No");

				k++;
			}

			try(ByteArrayOutputStream bout = new ByteArrayOutputStream();) {

				workbook.write(bout);
				workbook.close();
				return bout.toByteArray();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] downloadUsers(List<User> admins) {

		try (XSSFWorkbook workbook = new XSSFWorkbook();){
			XSSFSheet sheet = workbook.createSheet("Admins");

			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 7000);
			sheet.setColumnWidth(5, 7000);
			sheet.setColumnWidth(6, 7000);

			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			XSSFFont xSSFFont = workbook.createFont();

			xSSFFont.setBold(true);
			xSSFFont.setFontHeightInPoints((short) 14);
			xSSFFont.setColor(IndexedColors.RED.getIndex());

			cellStyle.setFont(xSSFFont);

			XSSFRow rowhead = sheet.createRow((short) 0);

			XSSFCell cell0 = rowhead.createCell(0);
			cell0.setCellValue(new XSSFRichTextString("S.No."));
			cell0.setCellStyle(cellStyle);

			XSSFCell cell1 = rowhead.createCell(1);
			cell1.setCellValue(new XSSFRichTextString("User_Name"));
			cell1.setCellStyle(cellStyle);

			XSSFCell cell2 = rowhead.createCell(2);
			cell2.setCellValue(new XSSFRichTextString("First_Name"));
			cell2.setCellStyle(cellStyle);

			XSSFCell cell3 = rowhead.createCell(3);
			cell3.setCellValue(new XSSFRichTextString("Last_Name"));
			cell3.setCellStyle(cellStyle);

			XSSFCell cell4 = rowhead.createCell(4);
			cell4.setCellValue(new XSSFRichTextString("Creation_Date"));
			cell4.setCellStyle(cellStyle);

			XSSFCell cell5 = rowhead.createCell(5);
			cell5.setCellValue(new XSSFRichTextString("Updation_Date"));
			cell5.setCellStyle(cellStyle);

			XSSFCell cell6 = rowhead.createCell(6);
			cell6.setCellValue(new XSSFRichTextString("Active_Status"));
			cell6.setCellStyle(cellStyle);

			XSSFCellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2 = workbook.createCellStyle();
			cellStyle2.setWrapText(true);

			int k = 1;

			for (User admin : admins) {
				XSSFRow row = sheet.createRow((short) k);
				row.setRowStyle(cellStyle2);
				row.createCell(0).setCellValue(k);
				row.createCell(1).setCellValue(admin.getUsername().trim());
				row.createCell(2).setCellValue(admin.getFirstName().trim());
				row.createCell(3).setCellValue(admin.getLastName().trim());
				row.createCell(4).setCellValue(dateFormatter.format(admin.getCreationDate()));
				row.createCell(5).setCellValue(dateFormatter.format(admin.getUpdationDate()));

				if (admin.isActive())
					row.createCell(6).setCellValue("Active");
				else
					row.createCell(6).setCellValue("Inactive");

				k++;
			}

			try (ByteArrayOutputStream bout = new ByteArrayOutputStream();){
			
				workbook.write(bout);
				workbook.close();
				return bout.toByteArray();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ByteArrayInputStream userToExcel(List<User> userrequest) {

		String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String[] HEADERs = { "S. No", "FirstName", "LastName", "UserName", "Email", "MobileNumber" };
		String SHEET = "Users";

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();	XSSFWorkbook workbook2 = new XSSFWorkbook();) {
			Sheet sheet = workbook.createSheet(SHEET);

			XSSFFont xSSFFont = workbook2.createFont();

			xSSFFont.setBold(true);
			xSSFFont.setFontHeightInPoints((short) 14);
			xSSFFont.setColor(IndexedColors.RED.getIndex());

			// Header
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			int sno = 1;
			for (User userrequests : userrequest) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(sno);
				row.createCell(1).setCellValue(userrequests.getFirstName());
				row.createCell(2).setCellValue(userrequests.getLastName());
				row.createCell(3).setCellValue(userrequests.getUsername());
				row.createCell(4).setCellValue(userrequests.getEmailId());
				row.createCell(5).setCellValue(userrequests.getMobileNumber());
				sno++;
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static byte[] downloadAuditLog(List<AuditLog> auditLogs) {

		try(XSSFWorkbook workbook = new XSSFWorkbook();) {
			XSSFSheet sheet = workbook.createSheet("AuditLog");

			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 7000);
			sheet.setColumnWidth(5, 7000);
			sheet.setColumnWidth(6, 7000);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			XSSFFont xSSFFont = workbook.createFont();

			xSSFFont.setBold(true);
			xSSFFont.setFontHeightInPoints((short) 14);
			xSSFFont.setColor(IndexedColors.RED.getIndex());

			cellStyle.setFont(xSSFFont);

			XSSFRow rowhead = sheet.createRow((short) 0);

			XSSFCell cell0 = rowhead.createCell(0);
			cell0.setCellValue(new XSSFRichTextString("S.No."));
			cell0.setCellStyle(cellStyle);

			XSSFCell cell1 = rowhead.createCell(1);
			cell1.setCellValue(new XSSFRichTextString("User_Name"));
			cell1.setCellStyle(cellStyle);

			XSSFCell cell2 = rowhead.createCell(2);
			cell2.setCellValue(new XSSFRichTextString("Module_Name"));
			cell2.setCellStyle(cellStyle);

			XSSFCell cell3 = rowhead.createCell(3);
			cell3.setCellValue(new XSSFRichTextString("Description"));
			cell3.setCellStyle(cellStyle);

			XSSFCell cell4 = rowhead.createCell(4);
			cell4.setCellValue(new XSSFRichTextString("Creation_Date"));
			cell4.setCellStyle(cellStyle);

			XSSFCell cell5 = rowhead.createCell(5);
			cell5.setCellValue(new XSSFRichTextString("Action"));
			cell5.setCellStyle(cellStyle);

			XSSFCell cell6 = rowhead.createCell(6);
			cell6.setCellValue(new XSSFRichTextString("Type"));
			cell6.setCellStyle(cellStyle);

			XSSFCellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2 = workbook.createCellStyle();
			cellStyle2.setWrapText(true);

			int k = 1;

			for (AuditLog auditLog : auditLogs) {
				XSSFRow row = sheet.createRow((short) k);
				row.setRowStyle(cellStyle2);
				row.createCell(0).setCellValue(k);
				row.createCell(1).setCellValue(auditLog.getUserName().trim());
				row.createCell(2).setCellValue(auditLog.getModuleName().trim());
				row.createCell(3).setCellValue(auditLog.getDescription().trim());
				row.createCell(4).setCellValue(dateFormatter.format(auditLog.getCreationDate()));

				row.createCell(5).setCellValue(auditLog.getAction().trim());
				row.createCell(6).setCellValue(auditLog.getWebmobile().trim());

				k++;
			}

			try (ByteArrayOutputStream bout = new ByteArrayOutputStream();) {

				workbook.write(bout);
				workbook.close();
				return bout.toByteArray();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
