package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class EmployeeRepository {
    private final Set<Employee> employees = new HashSet<>();

    private final Random randomId = new Random();

    public Integer genId() {
        // Эмуляция сиквенса
        return randomId.nextInt(1, Integer.MAX_VALUE);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    public Employee getById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Employee insert(Employee employee) {
        employee.setId(genId());
        employees.add(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        Employee employeeUpd = getById(employee.getId());
        employeeUpd.setFirstName(employee.getFirstName());
        employeeUpd.setLastName(employee.getLastName());
        employeeUpd.setDepartmentName(employee.getDepartmentName());
        return employeeUpd;
    }

    public Employee delete(Integer id) {
        Employee employeeDel = employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().get();
        employees.remove(employeeDel);
        return employeeDel;
    }
}
