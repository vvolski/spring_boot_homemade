package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.PayOrd;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PayOrdRepository {
    private final Set<PayOrd> payOrds = new HashSet<>();

    private final Random randomId = new Random();

    public Integer genId() {
        // Эмуляция сиквенса
        return randomId.nextInt(1, Integer.MAX_VALUE);
    }

    public List<PayOrd> getAll() {
        return new ArrayList<>(payOrds);
    }

    public PayOrd getById(Integer id) {
        return payOrds.stream()
                .filter(payOrd -> payOrd.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public PayOrd insert(PayOrd payOrd) {
        payOrd.setId(genId());
        payOrds.add(payOrd);
        return payOrd;
    }

    public PayOrd update(PayOrd payOrd) {
        PayOrd payOrdUpd = getById(payOrd.getId());
        payOrdUpd.setDate(payOrd.getDate());
        payOrdUpd.setSum(payOrd.getSum());
        payOrdUpd.setSalaryDate(payOrd.getSalaryDate());
        return payOrdUpd;
    }

    public PayOrd delete(Integer id) {
        PayOrd payOrdDel = payOrds.stream()
                .filter(payOrd -> payOrd.getId().equals(id))
                .findFirst().get();
        payOrds.remove(payOrdDel);
        return payOrdDel;
    }
}
