package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.Modules;
import com.adv.repository.ModulesRepository;
import com.adv.service.ModulesService;

@Service
public class ModulesServiceImpl implements ModulesService {

	@Autowired
	ModulesRepository moduleRepository;

	public List<Modules> saveNewModules(List<String> moduleNames) {
		
		List<Modules>  modules= moduleRepository.findAll();
		
		if (modules != null && !modules.isEmpty()) {
			for (Modules module : modules) {
				if (moduleNames.contains(module.getName())) {
					moduleNames.remove(module.getName());
				}
			}
		}

		if (moduleNames != null && !moduleNames.isEmpty()) {
			List<Modules> savingModules = new ArrayList<>();

			Timestamp currentDT = new Timestamp(System.currentTimeMillis());

			for (String moduleName : moduleNames) {
				Modules newModule = new Modules();
				newModule.setName(moduleName);
				newModule.setCreationDate(currentDT);
				newModule.setUpdatationDate(currentDT);
				savingModules.add(newModule);
			}
			return moduleRepository.saveAll(savingModules);
		}
		return modules;
	}

	@Override
	public boolean saveNewModule(String moduleName) {
		boolean module = moduleRepository.existsByName(moduleName);
		if (module == false) {
			Modules newModule = new Modules();
			newModule.setName(moduleName);
			Timestamp currentDT = new Timestamp(System.currentTimeMillis());
			newModule.setCreationDate(currentDT);
			newModule.setUpdatationDate(currentDT);
			moduleRepository.save(newModule);
			return true;
		}
		return module;
	}

	@Override
	public List<Modules> fetchAll() {
		return moduleRepository.findAll();
	}

	@Override
	public Modules get(Long id) {
		return moduleRepository.findById(id).orElse(null);
	}
}
