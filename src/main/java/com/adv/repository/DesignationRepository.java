package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adv.model.Designation;
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

}
