package com.colvir.spring_boot_homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayOrd {
    private Integer id;
    private Integer employeeId;
    private LocalDate date;
    private Double sum;
    private LocalDate salaryDate;
}
