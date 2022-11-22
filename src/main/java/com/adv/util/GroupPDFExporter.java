package com.adv.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.adv.model.GroupManagement;
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

public class GroupPDFExporter {
	private List<GroupManagement> listgroups;

	public GroupPDFExporter(List<GroupManagement> listAdmins) {
		this.listgroups = listAdmins;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		document.open();

		Paragraph p = new Paragraph("IAF", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(4);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.5f });
		table.setSpacingBefore(15);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}

	private void writeTableHeader(PdfPTable table) {

		PdfPCell cell = new PdfPCell();

		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(3);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("S_NO", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Group_Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Creation_Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Updation_Date", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {
		int count = 1;
		for (GroupManagement admin : listgroups) {
			table.addCell(String.valueOf(count));
			table.addCell(admin.getGroupName());
			table.addCell(String.valueOf(admin.getCreationDate()));
			table.addCell(String.valueOf(admin.getUpdationDate()));
			count++;
		}
	}

}
