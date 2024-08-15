package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.PayOrd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayOrdRepository extends JpaRepository<PayOrd, Integer> {}
