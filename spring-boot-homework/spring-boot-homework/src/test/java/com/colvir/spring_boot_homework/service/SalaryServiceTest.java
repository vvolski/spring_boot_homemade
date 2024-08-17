package com.colvir.spring_boot_homework.service;

import com.colvir.spring_boot_homework.MapperConfiguration;
import com.colvir.spring_boot_homework.dto.PayOrdRequest;
import com.colvir.spring_boot_homework.dto.PayOrdResponse;
import com.colvir.spring_boot_homework.exception.GeneralException;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.mapper.PayOrdMapper;
import com.colvir.spring_boot_homework.model.Employee;
import com.colvir.spring_boot_homework.model.PayOrd;
import com.colvir.spring_boot_homework.repository.PayOrdCacheRepository;
import com.colvir.spring_boot_homework.repository.PayOrdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// Текущий тест с использованием Мок
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SalaryService.class,
        PayOrdMapper.class
})
@SpringBootTest(classes = {MapperConfiguration.class})
public class SalaryServiceTest {
    @Autowired
    private SalaryService salaryService;
    @MockBean
    private PayOrdRepository payOrdRepository;
    @MockBean
    private PayOrdCacheRepository payOrdCacheRepository;
    @MockBean
    private EmployeeService employeeService;

    private final Employee employee1 = new Employee(1, "valery", "volski", "ColvirAccountProcessing24x7");
    private final Employee employee2 = new Employee(2, "alex", "belski", "ColvirAccountProcessing24x7");
    private final PayOrd payOrd1 = new PayOrd(null, employee1.getId(), LocalDate.now(), 5000.00, LocalDate.now().withDayOfMonth(1));
    private final PayOrd payOrd1Saved = new PayOrd(1, payOrd1.getEmployeeId(), payOrd1.getDate(), payOrd1.getSum(), payOrd1.getSalaryDate());
    private final PayOrdRequest payOrd1Request = new PayOrdRequest(payOrd1.getEmployeeId(), payOrd1.getDate(), payOrd1.getSum());
    private static final List<PayOrd> payOrdList = new ArrayList<>();

    @Test
    void insertPayOrd_success() {
        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        //Нужна для того, чтобы получить payOrd с идентификатором
        when(payOrdRepository.save(payOrd1)).thenReturn(payOrd1Saved);

        PayOrdResponse expectedResponse = new PayOrdResponse(payOrd1Saved.getId(), payOrd1Saved.getEmployeeId(), payOrd1Saved.getDate(), payOrd1Saved.getSum(), payOrd1Saved.getSalaryDate());
        PayOrdResponse actualResponse = salaryService.insertPayOrd(payOrd1Request);

        assertEquals(expectedResponse, actualResponse);
        verify(payOrdRepository).save(payOrd1);
    }

    @Test
    void insertPayOrd_BadSum_exception() {
        // Имитируем некорректную сумму выплаты
        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        PayOrdRequest payOrdBadSumRequest = new PayOrdRequest(payOrd1.getEmployeeId(), payOrd1.getDate(), 0.00);
        assertThrows(GeneralException.class, () -> salaryService.insertPayOrd(payOrdBadSumRequest));
    }

    @Test
    void insertPayOrd_DupPayOrd_exception() {
        // Имитируем дублирование оплаты
        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        List<PayOrd> payOrdList = new ArrayList<>();
        payOrdList.add(payOrd1);
        when(payOrdRepository.findAll()).thenReturn(payOrdList);
        assertThrows(RecordFoundException.class, () -> salaryService.insertPayOrd(payOrd1Request));
    }

    @Test
    void updatePayOrd_success() {
        PayOrd payOrd1Updated = new PayOrd(payOrd1Saved.getId(), payOrd1Saved.getEmployeeId(), payOrd1Saved.getDate(), 7000.00, payOrd1Saved.getSalaryDate());
        PayOrdRequest payOrd1UpdatedRequest = new PayOrdRequest(payOrd1Updated.getEmployeeId(), payOrd1Updated.getDate(), payOrd1Updated.getSum());

        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        when(payOrdCacheRepository.findById(any())).thenReturn(Optional.empty());
        when(payOrdRepository.findById(payOrd1Updated.getId())).thenReturn(Optional.of(payOrd1Updated));
        when(payOrdRepository.save(payOrd1Updated)).thenReturn(payOrd1Updated);

        PayOrdResponse expectedResponse = new PayOrdResponse(payOrd1Updated.getId(), payOrd1Updated.getEmployeeId(), payOrd1Updated.getDate(), payOrd1Updated.getSum(), payOrd1Updated.getSalaryDate());
        PayOrdResponse actualResponse = salaryService.updatePayOrd(payOrd1Updated.getId(), payOrd1UpdatedRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(payOrdRepository).save(payOrd1Updated);
    }

    @Test
    void updatePayOrd_ChangeEmployee_exception() {
        // Имитируем изменение сотрудника для выплаты
        PayOrd payOrd1Updated = new PayOrd(payOrd1Saved.getId(), employee2.getId(), payOrd1Saved.getDate(), 7000.00, payOrd1Saved.getSalaryDate());
        PayOrdRequest payOrd1UpdatedRequest = new PayOrdRequest(employee2.getId(), payOrd1Saved.getDate(), 7000.00);

        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        when(payOrdCacheRepository.findById(any())).thenReturn(Optional.empty());
        when(payOrdRepository.findById(payOrd1Saved.getId())).thenReturn(Optional.of(payOrd1Saved));
        when(payOrdRepository.save(payOrd1Updated)).thenReturn(payOrd1Updated);

        assertThrows(GeneralException.class, () -> salaryService.updatePayOrd(payOrd1Updated.getId(), payOrd1UpdatedRequest));
    }

    @Test
    void updatePayOrd_BadSum_exception() {
        // Имитируем некорректную сумму выплаты
        PayOrd payOrd1Updated = new PayOrd(payOrd1Saved.getId(), payOrd1Saved.getEmployeeId(), payOrd1Saved.getDate(), 0.00, payOrd1Saved.getSalaryDate());
        PayOrdRequest payOrd1UpdatedRequest = new PayOrdRequest(payOrd1Saved.getId(), payOrd1Saved.getDate(), 0.00);

        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        when(payOrdCacheRepository.findById(any())).thenReturn(Optional.empty());
        when(payOrdRepository.findById(payOrd1Saved.getId())).thenReturn(Optional.of(payOrd1Saved));
        when(payOrdRepository.save(payOrd1Updated)).thenReturn(payOrd1Updated);

        assertThrows(GeneralException.class, () -> salaryService.updatePayOrd(payOrd1Updated.getId(), payOrd1UpdatedRequest));
    }

    @Test
    void updatePayOrd_DupPayOrd_exception() {
        // Имитируем дублирование оплаты
        payOrdList.add(payOrd1Saved);
        PayOrd payOrd2Saved = new PayOrd(2, payOrd1Saved.getEmployeeId(), payOrd1Saved.getDate().minusMonths(1), payOrd1Saved.getSum(), payOrd1Saved.getSalaryDate().minusMonths(1));
        PayOrdRequest payOrd2SavedRequest = new PayOrdRequest(payOrd2Saved.getEmployeeId(), payOrd2Saved.getDate(), payOrd2Saved.getSum());
        payOrdList.add(payOrd2Saved);

        when(employeeService.getByIdEmployeeOrRaise(employee1.getId())).thenReturn(employee1);
        when(payOrdRepository.findAll()).thenReturn(payOrdList);
        when(payOrdCacheRepository.findById(any())).thenReturn(Optional.empty());
        when(payOrdRepository.findById(payOrd1Saved.getId())).thenReturn(Optional.of(payOrd1Saved));
        when(payOrdRepository.save(payOrd1Saved)).thenReturn(payOrd1Saved);

        assertThrows(RecordFoundException.class, () -> salaryService.updatePayOrd(payOrd1Saved.getId(), payOrd2SavedRequest));
    }
}
