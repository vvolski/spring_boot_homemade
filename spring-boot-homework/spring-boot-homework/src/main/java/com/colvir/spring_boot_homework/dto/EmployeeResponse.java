package com.colvir.spring_boot_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String departmentName;
}