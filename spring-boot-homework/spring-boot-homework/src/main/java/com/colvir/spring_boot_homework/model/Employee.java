package com.colvir.spring_boot_homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @SequenceGenerator(name = "emp_seq", sequenceName = "employees_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    private Integer id;
    private String firstName;
    private String lastName;
    private String departmentName;
}
