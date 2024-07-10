package com.colvir.spring_boot_homework.controller;

import com.colvir.spring_boot_homework.dto.*;
import com.colvir.spring_boot_homework.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/getAll/")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdListResponse payOrdGetAll() {
        return salaryService.getAllPayOrd();
    }

    @GetMapping("/getById/{payOrdId}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdGetById(@PathVariable("payOrdId") Integer payOrdId) {
        return salaryService.getByIdPayOrd(payOrdId);
    }

    @PostMapping("/insert/")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdInsert(@RequestBody PayOrdRequest request) {
        return salaryService.insertPayOrd(request);
    }

    @PutMapping("/update/{payOrdId}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdUpdate(@PathVariable("payOrdId") Integer payOrdId, @RequestBody PayOrdRequest request) {
        return salaryService.updatePayOrd(payOrdId, request);
    }

    @DeleteMapping("/delete/{payOrdId}")
    @ResponseStatus(HttpStatus.OK)
    public PayOrdResponse payOrdDelete(@PathVariable("payOrdId") Integer payOrdId) {
        return salaryService.deletePayOrd(payOrdId);
    }
}
