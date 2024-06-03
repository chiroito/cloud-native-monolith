package org.example.jakartasample.transaction;

import org.example.jakartasample.transaction.jpa.RdbmsData;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class SuccessfulBean {

    @PersistenceContext
    private EntityManager em;

    public void invoke() {
        RdbmsData data = em.find(RdbmsData.class, "key");
        data.setCount(data.getCount() + 1);
        em.merge(data);
    }
}
