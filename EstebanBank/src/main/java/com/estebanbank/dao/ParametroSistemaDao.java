/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.dao;

import com.estebanbank.model.ParametroSistema;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yoly
 */
public class ParametroSistemaDao {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    public ParametroSistema find(Integer id) {
        try {
            return this.em
                    .createNamedQuery("parametroSistema.findById", ParametroSistema.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public ParametroSistema find(String name) {
        try {
            return this.em
                    .createNamedQuery("parametroSistema.findByName", ParametroSistema.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<ParametroSistema> findAll() {
        return this.em.createNamedQuery("parametroSistema.findAll").getResultList();
    }

    public ParametroSistema save(ParametroSistema entity) {
        this.em.persist(entity);
        return entity;
    }

    public ParametroSistema edit(ParametroSistema entity) {
        ParametroSistema ps = this.find(entity.getId());
        if (ps != null) {
            ps.setNombre(entity.getNombre());
            ps.setValor(entity.getValor());
            this.em.merge(ps);
        }
        return ps;
    }

    public ParametroSistema remove(Integer id) {
        ParametroSistema ps = this.find(id);
        this.em.remove(ps);
        return ps;
    }
}
