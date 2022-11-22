package com.adv.service;

import java.util.List;

import com.adv.model.Modules;

public interface ModulesService {

	boolean saveNewModule(String moduleName);

	List<Modules> fetchAll();

	Modules get(Long id);

	List<Modules> saveNewModules(List<String> moduleNames);
}