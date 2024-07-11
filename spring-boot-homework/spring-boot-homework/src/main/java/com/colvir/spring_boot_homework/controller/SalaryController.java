package com.colvir.spring_boot_homework.controller;

import com.colvir.spring_boot_homework.dto.*;
import com.colvir.spring_boot_homework.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salaries")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PayOrdListResponse payOrdGetAll() {
        return salaryService.getAllPayOrd();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdGetById(@PathVariable("id") Integer payOrdId) {
        return salaryService.getByIdPayOrd(payOrdId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdInsert(@RequestBody PayOrdRequest request) {
        return salaryService.insertPayOrd(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdUpdate(@PathVariable("id") Integer payOrdId, @RequestBody PayOrdRequest request) {
        return salaryService.updatePayOrd(payOrdId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdDelete(@PathVariable("id") Integer payOrdId) {
        return salaryService.deletePayOrd(payOrdId);
    }
}
