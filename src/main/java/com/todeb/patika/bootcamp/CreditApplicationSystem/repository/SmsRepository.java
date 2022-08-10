package com.todeb.patika.bootcamp.CreditApplicationSystem.repository;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms, Long> {
}
