package com.colvir.spring_boot_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class PayOrdResponse {
    private Integer id;
    private Integer employeeId;
    private LocalDate date;
    private Double sum;
    private LocalDate salaryDate;
}
