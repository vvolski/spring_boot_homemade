package com.colvir.spring_boot_homework.controller;

import com.colvir.spring_boot_homework.dto.EmployeeListResponse;
import com.colvir.spring_boot_homework.dto.EmployeeRequest;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/getAll/")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeListResponse employeeGetAll() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/getById/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeGetById(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.getByIdEmployee(employeeId);
    }

    @PostMapping("/insert/")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeInsert(@RequestBody EmployeeRequest request) {
        return employeeService.insertEmployee(request);
    }

    @PutMapping("/update/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeUpdate(@PathVariable("employeeId") Integer employeeId, @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @PutMapping("/changeDepartment/{employeeId}/{departmentName}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse changeDepartment(@PathVariable("employeeId") Integer employeeId, @PathVariable("departmentName") String departmentName) {
        return employeeService.updateEmployeeDepartment(employeeId, departmentName);
    }

    @DeleteMapping("/delete/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse employeeDelete(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
