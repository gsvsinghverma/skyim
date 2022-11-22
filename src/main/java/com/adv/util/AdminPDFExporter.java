package com.adv.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.adv.model.Admin;
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

public class AdminPDFExporter {
	private List<Admin> listAdmins;

	public AdminPDFExporter(List<Admin> listAdmins) {
		this.listAdmins = listAdmins;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		document.open();

		Paragraph p = new Paragraph("shieldcryptAdminManagement Details", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);
		PdfPTable table = new PdfPTable(7);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.setWidthPercentage(105f);
		table.setWidths(new float[] { 2.5f, 3.5f, 3.5f, 3.6f, 3.5f, 3.5f, 3.8f });
		table.setSpacingBefore(15);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}

	private void writeTableHeader(PdfPTable table) {

		PdfPCell cell = new PdfPCell();

		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(8);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("S_NO", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("User_Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("First_Name", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Last_Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Mobile_No", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Active", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {
		int count = 1;
		for (Admin admin : listAdmins) {
			table.addCell(String.valueOf(count));
			table.addCell(admin.getUsername());

			table.addCell(admin.getFirstName());
			table.addCell(admin.getLastName());
			table.addCell(admin.getMobileNumber());
			table.addCell(admin.getEmail());

			if (admin.isActive()) {
				table.addCell("Yes");
			} else {
				table.addCell("No");
			}

			count++;
		}
	}

}
