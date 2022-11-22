package com.adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.SubModule;

public interface SubModuleRepository extends JpaRepository<SubModule, Long> {

	List<SubModule> findAllByIdIn(List<Long> submoduleIds);

}