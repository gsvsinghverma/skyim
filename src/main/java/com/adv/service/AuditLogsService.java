package com.adv.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.adv.exception.CustomException;
import com.adv.model.AuditLog;
import com.adv.pagin.GetAllRequest;

public interface AuditLogsService {

	public List<AuditLog> getAllLogs();

	AuditLog get(long id);

	List<AuditLog> getAuditLogs(int offset, int limit);

	int getCountOfAllAuditLogs();

	int getCountOfAllAuditLogsByDate(Date from, Date to);

	public byte[] downloadAuditLogs() throws CustomException;

	byte[] exportAuditLogs(Date from, Date to) throws CustomException;

	List<AuditLog> getAllLogsByDate(Date from, Date to);

	AuditLog addAuditLog(String moduleName, String description, String userName, String action, String webmobile);

	String[] countOfAddNewOrgInBetweenLastDays();

	byte[] exportExcel( Date dateFrom, Date dateTo);

	byte[] exportExcelByUsername(String username, Date dateFrom, Date dateTo);

	Page<AuditLog> getAllAuditLogs(GetAllRequest pagingRequest);
}
