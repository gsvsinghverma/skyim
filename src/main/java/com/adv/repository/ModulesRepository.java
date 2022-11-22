package com.adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.Modules;

public interface ModulesRepository extends JpaRepository<Modules, Long> {

	boolean existsByName(String moduleNames);

	List<Modules> findByNameIn(List<String> moduleNames);

	List<Modules> findAllByIdIn(List<Long> list);

}