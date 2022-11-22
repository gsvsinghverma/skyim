package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adv.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{

}
