package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeRepository {
    private final SessionFactory sessionFactory;

    public List<Employee> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Employee e", Employee.class).getResultList();
    }

    public Employee getById(Integer id) {
        if (id == null) {
           return null;
        }
        return sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    public Employee insert(Employee employee) {
        sessionFactory.getCurrentSession().persist(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        Employee employeeUpd = sessionFactory.getCurrentSession().get(Employee.class, employee.getId());
        employeeUpd.setFirstName(employee.getFirstName());
        employeeUpd.setLastName(employee.getLastName());
        employeeUpd.setDepartmentName(employee.getDepartmentName());
        return employeeUpd;
    }

    public Employee delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Employee employeeDel = session.get(Employee.class, id);
        session.remove(employeeDel);
        return employeeDel;
    }
}
