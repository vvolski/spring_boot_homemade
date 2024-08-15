package com.colvir.spring_boot_homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payords")
public class PayOrd {
    @Id
    @SequenceGenerator(name = "payords_seq", sequenceName = "payords_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payords_seq")
    private Integer id;
    private Integer employeeId;
    private LocalDate date;
    private Double sum;
    private LocalDate salaryDate;
}
