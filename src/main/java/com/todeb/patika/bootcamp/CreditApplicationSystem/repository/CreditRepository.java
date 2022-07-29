package com.todeb.patika.bootcamp.CreditApplicationSystem.repository;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
