package com.colvir.spring_boot_homework.mapper;

import com.colvir.spring_boot_homework.dto.EmployeeListResponse;
import com.colvir.spring_boot_homework.dto.EmployeeRequest;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    Employee requestToEmployee(EmployeeRequest request);
    EmployeeRequest employeeToRequest(Employee employee);
    EmployeeResponse employeeToResponse(Employee employee);
    List<EmployeeResponse> employeeListToResponseList(List<Employee> employees);
    default EmployeeListResponse employeeListToResponse(List<Employee> employees) {
        return new EmployeeListResponse(employeeListToResponseList(employees));
    }
}
