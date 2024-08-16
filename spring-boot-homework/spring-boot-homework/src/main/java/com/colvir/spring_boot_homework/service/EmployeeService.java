package com.colvir.spring_boot_homework.service;

import com.colvir.spring_boot_homework.dto.EmployeeListResponse;
import com.colvir.spring_boot_homework.dto.EmployeeRequest;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.exception.RecordNotFoundException;
import com.colvir.spring_boot_homework.mapper.EmployeeMapper;
import com.colvir.spring_boot_homework.model.Employee;
import com.colvir.spring_boot_homework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public Employee getByIdEmployeeOrRaise(Integer id) {
        return employeeRepository.findById(id).
                orElseThrow(()-> new RecordNotFoundException(
                        String.format("Сотрудник с идентификатором [ %s ] не найден", id)));
    }

    public EmployeeResponse getByIdEmployee(Integer id) {
        return employeeMapper.employeeToResponse(getByIdEmployeeOrRaise(id));
    }

    public EmployeeListResponse getAllEmployee() {
        return employeeMapper.employeeListToResponse(employeeRepository.findAll());
    }

    public EmployeeResponse insertEmployee(EmployeeRequest request) {
        Employee insEmployee = employeeMapper.requestToEmployee(request);
        for (Employee employees : employeeRepository.findAll()) {
            if (employees.getFirstName().equals(insEmployee.getFirstName()) &&
                    employees.getLastName().equals(insEmployee.getLastName())) {
                throw new RecordFoundException(String.format("Сотрудник [ %s %s ] уже работает в подразделении [ %s ]",
                        insEmployee.getFirstName(),
                        insEmployee.getLastName(),
                        insEmployee.getDepartmentName()));
            }
        }
        return employeeMapper.employeeToResponse(employeeRepository.save(insEmployee));
    }

    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        Employee updEmployee = employeeMapper.requestToEmployee(request);
        updEmployee.setId(getByIdEmployeeOrRaise(id).getId());
        return employeeMapper.employeeToResponse(employeeRepository.save(updEmployee));
    }

    public EmployeeResponse deleteEmployee(Integer id) {
        Employee delEmployee = getByIdEmployeeOrRaise(id);
        employeeRepository.deleteById(delEmployee.getId());
        return employeeMapper.employeeToResponse(delEmployee);
    }
}
