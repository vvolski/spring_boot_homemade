package com.colvir.spring_boot_homework.service;

import com.colvir.spring_boot_homework.MapperConfiguration;
import com.colvir.spring_boot_homework.dto.EmployeeRequest;
import com.colvir.spring_boot_homework.dto.EmployeeResponse;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.exception.RecordNotFoundException;
import com.colvir.spring_boot_homework.mapper.EmployeeMapper;
import com.colvir.spring_boot_homework.model.Employee;
import com.colvir.spring_boot_homework.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Текущий тест с использованием Мок
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        EmployeeService.class,
        EmployeeMapper.class
})
@SpringBootTest(classes = {MapperConfiguration.class})
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private  EmployeeRepository employeeRepository;


    private final Employee employee1 = new Employee(null, "valery", "volski", "ColvirAccountProcessing24x7");
    private final Employee employee1Saved = new Employee(1, employee1.getFirstName(), employee1.getLastName(), employee1.getDepartmentName());
    private final EmployeeRequest employee1Request = new EmployeeRequest(employee1.getFirstName(), employee1.getLastName(), employee1.getDepartmentName());

    @Test
    void insertEmployee_success() {
        // Успешная вставка, имитируем добавление нового сотрудника
        when(employeeRepository.insert(employee1)).thenReturn(employee1Saved);

        EmployeeResponse expectedResponse = new EmployeeResponse(1, employee1Saved.getFirstName(), employee1Saved.getLastName(), employee1Saved.getDepartmentName());
        EmployeeResponse actualResponse = employeeService.insertEmployee(employee1Request);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeRepository).insert(employee1);
    }

   @Test
    void insertEmployee_exception() {
       // Вставка с ошибкой, имитируем дублирование сотрудника
       List<Employee> employeeList = new ArrayList<>();
       employeeList.add(employee1);
       when(employeeRepository.getAll()).thenReturn(employeeList);

       assertThrows(RecordFoundException.class, () -> employeeService.insertEmployee(employee1Request));
    }

   @Test
    void updateEmployee_success() {
        // Успешное обновление
        Employee employeeUpdated = new Employee(1, employee1.getFirstName(), "Belskiy", employee1.getDepartmentName());
        EmployeeRequest employeeUpdatedRequest = new EmployeeRequest(employeeUpdated.getFirstName(), employeeUpdated.getLastName(), employeeUpdated.getDepartmentName());

        when(employeeRepository.getById(employeeUpdated.getId())).thenReturn(employeeUpdated);
        when(employeeRepository.update(employeeUpdated)).thenReturn(employeeUpdated);

        EmployeeResponse expectedResponse = new EmployeeResponse(employeeUpdated.getId(), employeeUpdated.getFirstName(), employeeUpdated.getLastName(), employeeUpdated.getDepartmentName());
        EmployeeResponse actualResponse = employeeService.updateEmployee(employeeUpdated.getId(), employeeUpdatedRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateEmployee_exception() {
        //Обновление с ошибкой, не найден пользователь
        Employee employeeUpdated = new Employee(2, employee1.getFirstName(), "Belskiy", employee1.getDepartmentName());
        EmployeeRequest employeeUpdatedRequest = new EmployeeRequest(employeeUpdated.getFirstName(), employeeUpdated.getLastName(), employeeUpdated.getDepartmentName());

        assertThrows(RecordNotFoundException.class, () -> employeeService.updateEmployee(employeeUpdated.getId(), employeeUpdatedRequest));
    }

    @Test
    void deleteEmployee_success() {
        // Успешное удаление
        when(employeeRepository.delete(employee1Saved.getId())).thenReturn(employee1Saved);
        when(employeeRepository.getById(employee1Saved.getId())).thenReturn(employee1Saved);

        EmployeeResponse expectedResponse = new EmployeeResponse(employee1Saved.getId(), employee1Saved.getFirstName(), employee1Saved.getLastName(), employee1Saved.getDepartmentName());
        EmployeeResponse actualResponse = employeeService.deleteEmployee(employee1Saved.getId());

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteEmployee_exception() {
        // Удаление с ошибкой
        Employee employeeDeleted = new Employee(2, employee1.getFirstName(), employee1.getLastName(), employee1.getDepartmentName());

        when(employeeRepository.delete(employee1Saved.getId())).thenReturn(employee1Saved);
        when(employeeRepository.getById(employee1Saved.getId())).thenReturn(employee1Saved);

        assertThrows(RecordNotFoundException.class, () -> employeeService.deleteEmployee(employeeDeleted.getId()));
    }
}
