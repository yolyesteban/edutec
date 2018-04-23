/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.dao;

import com.estebanbank.model.TipoCuenta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yoly
 */
public class TipoCuentaDao {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    public TipoCuenta find(Integer id) {
        try {
            return this.em
                    .createNamedQuery("tipoCuenta.findById", TipoCuenta.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public TipoCuenta find(String name) {
        try {
            return this.em
                    .createNamedQuery("tipoCuenta.findByName", TipoCuenta.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<TipoCuenta> findAll() {
        return this.em.createNamedQuery("tipoCuenta.findAll").getResultList();
    }

    public TipoCuenta save(TipoCuenta entity) {
        this.em.persist(entity);
        return entity;
    }

    public TipoCuenta edit(TipoCuenta entity) {
        TipoCuenta tc = this.find(entity.getId());
        if (tc != null) {
            tc.setDescripcion(entity.getDescripcion());
            tc.setNombre(entity.getNombre());
            tc.setTasaInteres(entity.getTasaInteres());
            this.em.merge(tc);
        }
        return tc;
    }

    public TipoCuenta remove(Integer id) {
        TipoCuenta tc = this.find(id);
        this.em.remove(tc);
        return tc;
    }
}

