package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adv.exception.CustomException;
import com.adv.model.AuditLog;
import com.adv.pagin.GetAllRequest;
import com.adv.repository.AuditLogsRepository;
import com.adv.service.AuditLogsService;
import com.adv.util.ExcelUtil;
import com.adv.util.Util;

@Service
public class AuditLogsServiceImpl implements AuditLogsService {

	@Autowired
	AuditLogsRepository auditLogRepository;

	@Override
	public AuditLog addAuditLog(String moduleName, String description, String userName, String action,
			String webmobile) {

		AuditLog auditLog = new AuditLog();

		auditLog.setModuleName(moduleName);
		auditLog.setDescription(description);
		auditLog.setUserName(userName);
		auditLog.setAction(action);
		auditLog.setWebmobile(webmobile);

		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		auditLog.setCreationDate(currentDT);

		return auditLogRepository.save(auditLog);
	}

	@Override
	public List<AuditLog> getAllLogs() {
		return auditLogRepository.findAll();
	}

	@Override
	public AuditLog get(long id) {
		return auditLogRepository.findById(id).orElse(null);

	}



	@Override
	public int getCountOfAllAuditLogsByDate(Date from, Date to) {
		try {
			return (int) auditLogRepository.countByCreationDateBetween(from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<AuditLog> getAuditLogs(int offset, int limit) {
		List<AuditLog> auditLog = new ArrayList<>();
		try {
			Pageable paging = PageRequest.of(offset, limit, Sort.by("creationDate").descending());
			return auditLogRepository.findAll(paging).getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auditLog;
	}

	@Override
	public int getCountOfAllAuditLogs() {
		try {
			return (int) auditLogRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public byte[] downloadAuditLogs() throws CustomException {
		return ExcelUtil.downloadAuditLogsFile(getAllLogs());
	}

	@Override
	public byte[] exportAuditLogs(Date from, Date to) throws CustomException {
		return ExcelUtil.downloadAuditLogsFile(getAllLogsByDate(from, to));
	}

	@Override
	public List<AuditLog> getAllLogsByDate(Date from, Date to) {
		return auditLogRepository.findByCreationDateBetween(from, to);
	}

	@Override
	public String[] countOfAddNewOrgInBetweenLastDays() {

		String dayDateValue = "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";
		String countOfOrgByDay = "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat dayfor = new SimpleDateFormat("dd-MMMM");

			long[] arrCountOfOrgByDay = new long[15];
			StringBuffer orgString = new StringBuffer();

			String[] arrDayDate = new String[15];
			StringBuffer orgDayDate = new StringBuffer();

			for (int i = 0; i < 15; i++) {
				Date dateobj = (new Date(new Date().getTime() - 24 * 3600 * 1000 * i));

				String sDate = sdf.format(dateobj);
				sDate = sDate.substring(0, 10);

				arrDayDate[i] = "\"" + dayfor.format(dateobj) + "\"";

				String sDate1 = sDate.concat(" 00:00:00");
				String sDate2 = sDate.concat(" 23:59:59");
				Date date1 = df.parse(sDate1);
				Date date2 = df.parse(sDate2);
				arrCountOfOrgByDay[i] = auditLogRepository.countByCreationDateBetween(date1, date2);

				orgString.append(arrCountOfOrgByDay[i]);
				orgDayDate.append(arrDayDate[i]);
			}
			countOfOrgByDay = Arrays.toString(arrCountOfOrgByDay);
			dayDateValue = Arrays.toString(arrDayDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] dayValue = { countOfOrgByDay, dayDateValue };
		return dayValue;
	}

	@Override
	public byte[] exportExcel(Date dateFrom, Date dateTo) {
		return ExcelUtil.downloadAuditLog(userForExport(dateFrom, dateTo));
	}

	private List<AuditLog> userForExport(Date dateFrom, Date dateTo) {
		if (dateFrom != null && dateTo != null) {
			return null;
		} else {
			return auditLogRepository.findAllByOrderByCreationDateDesc();
		}
	}

	@Override
	public byte[] exportExcelByUsername(String username, Date dateFrom, Date dateTo) {
		return null;
	}


	@Override
	public Page<AuditLog> getAllAuditLogs(GetAllRequest pagingRequest) {
		Page<AuditLog> auditlog = null;
		String sortColumn = "creationDate";
		Sort sort = Sort.by(sortColumn).descending();

		Pageable paging = null;

		if (pagingRequest.getSort() != null) {

			if (pagingRequest.getSort().getColumn() != null && pagingRequest.getSort().getDirection() != null) {

				sortColumn = pagingRequest.getSort().getColumn();
				String dir = pagingRequest.getSort().getDirection();

				if (dir.equalsIgnoreCase("ASC")) {
					sort = Sort.by(sortColumn).ascending();
				} else {
					sort = Sort.by(sortColumn).descending();
				}

				paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);
			}
		} else {

			paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);
		}

		if (pagingRequest.getSearch() != null) {

			String UserName = "";
			String ModuleName = "";

			int size = pagingRequest.getSearch().size();
			for (int i = 0; i < size; i++) {
				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("userName")) {
					UserName = pagingRequest.getSearch().get(i).getValue();

				}

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("moduleName")) {
					ModuleName = pagingRequest.getSearch().get(i).getValue();
				}

			}
			paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);

			auditlog = auditLogRepository.findBySearch(UserName, ModuleName, paging);

			return auditlog;

		}

		if (pagingRequest.getDate() != null) {
			if (pagingRequest.getDate().getFrom() != null && pagingRequest.getDate().getTo() != null) {
				String from = pagingRequest.getDate().getFrom();
				String to = pagingRequest.getDate().getTo();

				Date fromDate = Util.getSearchDate(from);
				Date toDate = Util.getSearchDate(to);

				return auditLogRepository.findByCreationDateBetween(fromDate, toDate, paging);
			} else {
				auditlog = auditLogRepository.findAll(paging);
			}
		} else {
			auditlog = auditLogRepository.findAll(paging);
		}

		if (auditlog != null && !auditlog.isEmpty() && auditlog.getContent() != null) {
			return auditlog;

		} else {
			return auditlog;
		}

	}

}