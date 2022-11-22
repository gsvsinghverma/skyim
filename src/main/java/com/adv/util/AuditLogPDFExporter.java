package com.adv.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.adv.model.AuditLog;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class AuditLogPDFExporter {
	private List<AuditLog> listAuditLog;

	public AuditLogPDFExporter(List<AuditLog> listAuditLog) {
		this.listAuditLog = listAuditLog;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		document.open();

		Paragraph p = new Paragraph("shieldcryptAuditLog", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(7);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.5f, 3.5f,3.5f,3.5f});
		table.setSpacingBefore(15);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}

	private void writeTableHeader(PdfPTable table) {

		PdfPCell cell = new PdfPCell();

		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(7);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("S_NO", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("User_Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Description", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("module_Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Creation_Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Type", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Action", font));
		table.addCell(cell);
		

	}

	private void writeTableData(PdfPTable table) {
		int count = 1;
		for (AuditLog auditLog : listAuditLog) {
			table.addCell(String.valueOf(count));
			table.addCell(auditLog.getUserName());
			table.addCell(auditLog.getDescription());
			table.addCell(auditLog.getModuleName());
			table.addCell(String.valueOf(auditLog.getCreationDate()));
			table.addCell(auditLog.getWebmobile());
			table.addCell(auditLog.getAction());
			count++;
		}
	}

}
