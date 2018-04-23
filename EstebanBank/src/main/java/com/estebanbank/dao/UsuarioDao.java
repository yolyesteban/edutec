/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.dao;

import com.estebanbank.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yoly
 */
public class UsuarioDao {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    public UsuarioDao() {
    }

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public Usuario find(Integer id) {
        try {
            return this.em
                    .createNamedQuery("usuario.findById", Usuario.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Usuario find(String code) {
        try {
            return this.em
                    .createNamedQuery("usuario.findByCode", Usuario.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Usuario> findAll() {
        return this.em.createNamedQuery("usuario.findAll").getResultList();
    }

    public Usuario save(Usuario entity) {
        this.em.persist(entity);
        return entity;
    }

    public Usuario edit(Usuario entity) {
        Usuario usuario = this.find(entity.getId());
        if (usuario != null) {
            usuario.setCodigo(entity.getCodigo());
            usuario.setEmail(entity.getEmail());
            usuario.setNombre(entity.getNombre());
            usuario.setPassword(entity.getPassword());
            usuario.setTelefono(entity.getTelefono());
            this.em.merge(usuario);
        }
        return usuario;
    }

    public Usuario remove(Integer id) {
        Usuario usuario = this.find(id);
        this.em.remove(usuario);
        return usuario;
    }
}
