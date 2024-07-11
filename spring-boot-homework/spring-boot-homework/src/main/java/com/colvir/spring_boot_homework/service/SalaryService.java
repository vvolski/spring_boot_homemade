package com.colvir.spring_boot_homework.service;

import com.colvir.spring_boot_homework.dto.PayOrdListResponse;
import com.colvir.spring_boot_homework.dto.PayOrdRequest;
import com.colvir.spring_boot_homework.dto.PayOrdResponse;
import com.colvir.spring_boot_homework.exception.GeneralException;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.exception.RecordNotFoundException;
import com.colvir.spring_boot_homework.mapper.PayOrdMapper;
import com.colvir.spring_boot_homework.model.Employee;
import com.colvir.spring_boot_homework.model.PayOrd;
import com.colvir.spring_boot_homework.repository.PayOrdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryService {
    private final EmployeeService employeeService;
    private final PayOrdMapper payOrdMapper;
    private final PayOrdRepository payOrdRepository;

    public PayOrd getByIdPayOrdOrRaise(Integer id) {
        PayOrd payOrd = payOrdRepository.getById(id);
        if (payOrd == null) {
            throw new RecordNotFoundException(String.format("Выплата с идентификатором [ %s ] не найдена", id));
        }
        return payOrd;
    }

    public PayOrdListResponse getAllPayOrd() {
        return payOrdMapper.payOrdListToResponse(payOrdRepository.getAll());
    }

    public PayOrdResponse getByIdPayOrd(Integer id) {
        return payOrdMapper.payOrdToResponse(getByIdPayOrdOrRaise(id));
    }

    public PayOrdResponse insertPayOrd(PayOrdRequest request) {
        //Преобразовали запрос в документ
        PayOrd newPayOrd = payOrdMapper.requestToPayOrd(request);
        //Нашли данные по ссылке на сотрудника и проверили, что он существует
        Employee employeeOfPayOrd = employeeService.getByIdEmployeeOrRaise(request.getEmployeeId());
        if (newPayOrd.getSum() == null || newPayOrd.getSum() <= 0) {
            throw new GeneralException("Введена некорректная сумма выплаты сотруднику");
        }
        //Рассчитали первый день месяца для выплаты и заполнили поле документа
        newPayOrd.setSalaryDate(newPayOrd.getDate().withDayOfMonth(1));
        //Проверили, что такому сотруднику выплат за этот месяц не производилось
        for (PayOrd payOrds : payOrdRepository.getAll()) {
            //Если сотрудник есть в репозитории и дата выплаты репозитория равна дате новой выплаты - ругаемся
            if (employeeOfPayOrd.getId().equals(payOrds.getEmployeeId()) &&
                    payOrds.getSalaryDate().equals(newPayOrd.getSalaryDate())) {
                throw new RecordFoundException(String.format("Сотруднику [ %s %s ] уже произведена выплата за [ %s ]",
                        employeeOfPayOrd.getFirstName(),
                        employeeOfPayOrd.getLastName(),
                        newPayOrd.getSalaryDate()));
            }
        }
        return payOrdMapper.payOrdToResponse(payOrdRepository.insert(newPayOrd));
    }

    public PayOrdResponse updatePayOrd(Integer id, PayOrdRequest request) {
        PayOrd oldPayOrd = getByIdPayOrdOrRaise(id);
        PayOrd newPayOrd = payOrdMapper.requestToPayOrd(request);
        //Если сотрудник при обновлении изменился - ругаемся
        if (!oldPayOrd.getEmployeeId().equals(newPayOrd.getEmployeeId())) {
            throw new GeneralException("Запрещено изменять сотрудника в произведенной выплате");
        }
        Employee employeeOfPayOrd = employeeService.getByIdEmployeeOrRaise(newPayOrd.getEmployeeId());
        if (newPayOrd.getSum() == null || newPayOrd.getSum() <= 0) {
            throw new GeneralException("Введена некорректная сумма выплаты сотруднику");
        }
        newPayOrd.setId(id);
        newPayOrd.setSalaryDate(newPayOrd.getDate().withDayOfMonth(1));
        //Если дата выплаты при обновлении поменялась, проверяем возможные конфликты целостности данных
        if (!oldPayOrd.getSalaryDate().equals(newPayOrd.getSalaryDate())) {
            for (PayOrd payOrds : payOrdRepository.getAll()) {
                if (!newPayOrd.getId().equals(payOrds.getId())&&
                        newPayOrd.getEmployeeId().equals(payOrds.getEmployeeId())&&
                        newPayOrd.getSalaryDate().equals(payOrds.getSalaryDate())) {
                    throw new RecordFoundException(String.format("Сотруднику [ %s %s ] уже произведена выплата за [ %s ]",
                            employeeOfPayOrd.getFirstName(),
                            employeeOfPayOrd.getLastName(),
                            payOrds.getSalaryDate()));
                }
            }
        }
        return payOrdMapper.payOrdToResponse(payOrdRepository.update(newPayOrd));
    }

    public PayOrdResponse deletePayOrd(Integer id) {
        return payOrdMapper.payOrdToResponse(payOrdRepository.delete(getByIdPayOrdOrRaise(id).getId()));
    }
}
