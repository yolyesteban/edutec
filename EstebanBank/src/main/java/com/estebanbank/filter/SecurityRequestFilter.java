/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.filter;

import com.estebanbank.dao.DepartamentoDao;
import com.estebanbank.dao.UsuarioDao;
import java.io.IOException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Yoly
 */
@Provider
public class SecurityRequestFilter implements ContainerRequestFilter {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    @Inject
    UsuarioDao usuarioDao;

    @Inject
    DepartamentoDao departamentoDao;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        System.out.println("JAXRS Filter dice: Estoy filtrando ;)");

        boolean debug = false;

        String[] nonSecurePaths = new String[]{
            "usuarios/login",
            "departamentos",
            "municipios"
        };

        System.out.println(em);
        System.out.println(usuarioDao);
        System.out.println(departamentoDao);
    }
}
