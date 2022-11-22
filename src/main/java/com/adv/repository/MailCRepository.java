package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.MailConfiguration;

public interface MailCRepository extends JpaRepository<MailConfiguration, Long>{

}
