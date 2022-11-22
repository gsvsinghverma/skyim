package com.adv.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.adv.model.GroupManagement;
import com.adv.pagin.GetAllRequest;

public interface GroupManagementService {

	GroupManagement saveGroup(GroupManagement groupmanagement);

	GroupManagement updateGroup(GroupManagement groupmanagement);

	Page<GroupManagement> getAllGroup(GetAllRequest pagingRequest);

	Boolean deleteById(Long id);

	byte[] exportExcel(String src, String dst, Date dateFrom, Date dateTo);

	byte[] exportExcelByUsername(String username, String src, String dst, Date dateFrom, Date dateTo);

}
