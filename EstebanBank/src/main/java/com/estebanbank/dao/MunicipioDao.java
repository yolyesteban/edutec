/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.dao;

import com.estebanbank.model.Municipio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yoly
 */
public class MunicipioDao {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    public Municipio find(Integer id) {
        try {
            return this.em
                    .createNamedQuery("municipio.findById", Municipio.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Municipio find(String nombre) {
        try {
            return this.em
                    .createNamedQuery("municipio.findByName", Municipio.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Municipio> findAll() {
        return this.em.createNamedQuery("municipio.findAll").getResultList();
    }

    public Municipio save(Municipio entity) {
        this.em.persist(entity);
        return entity;
    }

    public Municipio edit(Municipio entity) {
        Municipio mn = this.find(entity.getId());
        if (mn != null) {
            mn.setCodigo(entity.getCodigo());
            mn.setDepartamento(mn.getDepartamento());
            mn.setNombre(entity.getNombre());
            this.em.merge(mn);
        }
        return mn;
    }

    public Municipio remove(Integer id) {
        Municipio mn = this.find(id);
        this.em.remove(mn);
        return mn;
    }
}