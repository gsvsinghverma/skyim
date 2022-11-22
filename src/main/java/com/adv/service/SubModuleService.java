package com.adv.service;

import java.util.List;

import com.adv.model.SubModule;
import com.adv.payloads.apirequests.SubModuleSaveRequest;
import com.adv.payloads.apirequests.UpdateSubModuleRequest;

public interface SubModuleService {
	SubModule saveSubModule(SubModuleSaveRequest submodule);

	SubModule updateSubModule(UpdateSubModuleRequest submodule);

	SubModule get(long id);

	List<SubModule> getAllSubModule();

	boolean deleteSubModule(SubModule submodule);

	SubModule get(Long attribute);

}
