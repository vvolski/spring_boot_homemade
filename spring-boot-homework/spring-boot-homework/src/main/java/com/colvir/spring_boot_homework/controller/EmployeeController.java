package com.colvir.spring_boot_homework.controller;

import com.colvir.spring_boot_homework.dto.EmployeeListResponse;
import com.colvir.spring_boot_homework.dto.EmployeeRequest;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeListResponse employeeGetAll() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeGetById(@PathVariable("id") Integer employeeId) {
        return employeeService.getByIdEmployee(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeInsert(@RequestBody EmployeeRequest request) {
        return employeeService.insertEmployee(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeUpdate(@PathVariable("id") Integer employeeId, @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeDelete(@PathVariable("id") Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
