package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
