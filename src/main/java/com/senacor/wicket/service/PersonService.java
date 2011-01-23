package com.senacor.wicket.service;

import com.senacor.wicket.domain.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PersonService {
    @PersistenceContext
    private EntityManager em;


    public void savePerson(Person p) {
        em.persist(p);
    }

    public List<Person> listPersons() {
        System.out.println("listing persons");
        return em.createQuery("select p from Person p order by p.id desc").getResultList();
    }
}
