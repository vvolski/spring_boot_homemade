package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.PayOrd;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class PayOrdRepository {
    private final SessionFactory sessionFactory;

    public List<PayOrd> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select p from PayOrd p", PayOrd.class).getResultList();
    }

    public PayOrd getById(Integer id) {
        if (id == null) {
            return null;
        }
        return sessionFactory.getCurrentSession().get(PayOrd.class, id);
    }

    public PayOrd insert(PayOrd payOrd) {
        sessionFactory.getCurrentSession().persist(payOrd);
        return payOrd;
    }

    public PayOrd update(PayOrd payOrd) {
        PayOrd payOrdUpd = sessionFactory.getCurrentSession().get(PayOrd.class, payOrd.getId());
        payOrdUpd.setDate(payOrd.getDate());
        payOrdUpd.setSum(payOrd.getSum());
        payOrdUpd.setSalaryDate(payOrd.getSalaryDate());
        return payOrdUpd;
    }

    public PayOrd delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        PayOrd payOrdDel = session.get(PayOrd.class, id);
        session.remove(payOrdDel);
        return payOrdDel;
    }
}
