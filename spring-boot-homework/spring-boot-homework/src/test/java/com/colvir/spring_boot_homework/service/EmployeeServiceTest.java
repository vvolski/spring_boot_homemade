package com.colvir.spring_boot_homework.service;

import com.colvir.spring_boot_homework.MapperConfiguration;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.exception.RecordNotFoundException;
import com.colvir.spring_boot_homework.mapper.EmployeeMapper;
import com.colvir.spring_boot_homework.model.Employee;
import com.colvir.spring_boot_homework.repository.EmployeeRepository;
import com.colvir.spring_boot_homework.repository.PayOrdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Подготовка для работы с тест-контейнерами в базе (не только сервис, но и репозиторий)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SalaryService.class,
        PayOrdRepository.class,
        EmployeeMapper.class,
        EmployeeService.class,
        EmployeeRepository.class
})
@SpringBootTest(classes = {MapperConfiguration.class})
public class EmployeeServiceTest {
    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeService employeeService;

    private static final Employee employee1 = new Employee(1, "valery", "volski", "ColvirAccountProcessing24x7");
    private static final Employee employee2 = new Employee(2, "alex", "belski", "ColvirAccountProcessing24x7");
    private static final Employee employee3 = new Employee(3, "gena", "rix", "RegulatoryReporting");
    private static final Employee employee4 = new Employee(4, "vladimir", "rabik", "RegulatoryReporting");
    private static final Employee employee5 = new Employee(5, "kuka", "kabra", "RegulatoryReporting");
    private static final Employee employee5_1 = new Employee(5, "updFirstName", "updLastName", "updDepartment");
    private static final List<EmployeeResponse> employeeResponses = new ArrayList<>();

    private void genDefaultData() {
        // Добавляем сразу и список сотрудников и отдельных сотрудников в хранилище
        if (employeeResponses.isEmpty()) {
            EmployeeResponse employee1Response = employeeService.insertEmployee(employeeMapper.employeeToRequest(employee1));
            employee1.setId(employee1Response.getId());
            employeeResponses.add(employee1Response);

            EmployeeResponse employee2Response = employeeService.insertEmployee(employeeMapper.employeeToRequest(employee2));
            employee2.setId(employee2Response.getId());
            employeeResponses.add(employee2Response);
        }
    }

    @Test
    void getByIdEmployee_success() {
        genDefaultData();
        EmployeeResponse expectedResponse = employeeMapper.employeeToResponse(employee1);
        EmployeeResponse actualResponse = employeeService.getByIdEmployee(employee1.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getByIdEmployee_exception() {
        genDefaultData();
        assertThrows(RecordNotFoundException.class, () -> employeeService.getByIdEmployee(employee4.getId()));
    }

    @Test
    void insertEmployee_success() {
        genDefaultData();
        //Полученный ответ от сервиса
        EmployeeResponse actualResponse = employeeService.insertEmployee(employeeMapper.employeeToRequest(employee3));
        employee3.setId(actualResponse.getId());
        //Данные ответа по сотруднику 1
        EmployeeResponse expectedResponse =  employeeMapper.employeeToResponse(employee3);
        //В локальный список всех сотрудников
        employeeResponses.add(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void insertEmployee_exception() {
        genDefaultData();
        assertThrows(RecordFoundException.class, () -> employeeService.insertEmployee(employeeMapper.employeeToRequest(employee1)));
    }

    @Test
    void updateEmployee_success() {
        genDefaultData();
        //Добавляем нового сотрудника
        EmployeeResponse employee5Response = employeeService.insertEmployee(employeeMapper.employeeToRequest(employee5));
        employee5.setId(employee5Response.getId());
        //В локальный список всех сотрудников
        employeeResponses.add(employee5Response);

        //Готовим ожидаемый ответ
        employee5_1.setId(employee5Response.getId());
        EmployeeResponse expectedResponse = employeeMapper.employeeToResponse(employee5_1);
        //Обновляем нового сотрудника
        EmployeeResponse actualResponse = employeeService.updateEmployee(employee5Response.getId(), employeeMapper.employeeToRequest(employee5_1));
        //Сравниваем предполагаемый и полученный ответ
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateEmployee_exception() {
        genDefaultData();
        assertThrows(RecordNotFoundException.class, () -> employeeService.updateEmployee(employee4.getId(), employeeMapper.employeeToRequest(employee4)));
    }

    @Test
    void deleteEmployee_success() {
        genDefaultData();
        EmployeeResponse actualResponse = employeeService.deleteEmployee(employeeMapper.employeeToResponse(employee1).getId());
        EmployeeResponse expectedResponse = employeeMapper.employeeToResponse(employee1);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteEmployee_exception() {
        genDefaultData();
        assertThrows(RecordNotFoundException.class, () -> employeeService.deleteEmployee(employeeMapper.employeeToResponse(employee4).getId()));
    }
}
