package com.adv.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adv.exception.CustomException;
import com.adv.model.AuditLog;
import com.adv.pagin.GetAllRequest;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.repository.AuditLogsRepository;
import com.adv.service.AuditLogsService;
import com.adv.util.AuditLogPDFExporter;
import com.adv.util.Constant;
import com.adv.util.Util;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/auditLogs")
@SecurityRequirement(name = "Bearer Authentication")
public class AuditLogsController {


	@Autowired
	AuditLogsService auditLogsService;
	
	
	@Autowired
	AuditLogsRepository auditLogsRepository;

	
	@PostMapping(value = "/getAll")
	public ResponseEntity<BasicApiResponse> getAllAuditLog(@RequestBody GetAllRequest pagingRequest) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			Page<AuditLog> audit = auditLogsService.getAllAuditLogs(pagingRequest);
			if (audit != null && !audit.isEmpty()) {
				response.setData(audit);
				response.setStatus(true);
				response.setMessage("AuditLog Fetched Successffully!");

			} else {

				response.setMessage("AuditLog Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}
	

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> exportExcel(@RequestParam String serachType,
			HttpServletResponse httpServletResponse)
			throws CustomException, DocumentException, IOException {
		serachType=serachType.toLowerCase();
		if (serachType.equals("excel")) {
			try {
				String startdate = "";
				String enddate = "";
				String username = null;

				Date dateFrom = null;
				Date dateTo = null;

				if (!startdate.equalsIgnoreCase("") && !enddate.equalsIgnoreCase("")) {
					dateFrom = Util.getSearchDateFromString(startdate);
					dateTo = Util.getSearchDateFromString(enddate);
				}
				byte[] data;
				if (username == null) {
					data = auditLogsService.exportExcel( dateFrom, dateTo);
				} else {
					data = auditLogsService.exportExcelByUsername(username, dateFrom, dateTo);
				}
				Date currentDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_HH-mm-ss");
				String strDate = formatter.format(currentDate);

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "attachment; filename=AuditLogs_" + strDate + ".xlsx");

				return ResponseEntity.ok().headers(headers)
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						.body(new InputStreamResource(new ByteArrayInputStream(data)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} 
		
		if (serachType.equals("pdf")) {
			httpServletResponse.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=AuditLogs_" + currentDateTime + ".pdf";
			httpServletResponse.setHeader(headerKey, headerValue);

			List<AuditLog> sorting = auditLogsRepository.findAll();

			AuditLogPDFExporter exporter = new AuditLogPDFExporter(sorting);
			exporter.export(httpServletResponse);
		}
		return null;
	}
	
	

}