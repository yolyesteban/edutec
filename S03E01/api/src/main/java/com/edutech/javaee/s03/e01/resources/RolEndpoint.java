/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.RolDto;
import com.edutech.javaee.s03.e01.model.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoly
 */
@Stateless
@Path("/roles")
public class RolEndpoint {
    @PersistenceContext(unitName = "primary")
    EntityManager em;

    private Rol buscarRol(Integer id) {
        try {
            return this.em
                    .createQuery("SELECT u FROM Rol u WHERE u.id = :param", Rol.class)
                    .setParameter("param", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @GET
    @Produces({"application/json"})
    public List<Rol> findAll() {
        List<Rol> roles = this.em
                .createQuery("SELECT u FROM Rol u", Rol.class)
                .getResultList();
        return roles;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {
        Rol rol = this.buscarRol(id);

        if (rol == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        return Response.ok(rol, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(RolDto dto) {
        Rol rol = new Rol(
                dto.getNombre(),
                dto.getDescripcion()
        );
        this.em.persist(rol);
        return Response.ok(rol).build();
    }

    @PUT
    @Produces({"application/json"})
    public Response update(RolDto dto) throws RollbackException {
        Rol rol = this.buscarRol(dto.getId());

        if (rol == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        rol.setDescripcion(dto.getDescripcion());
        rol.setNombre(dto.getNombre());

        this.em.merge(rol);
        return Response.ok(rol).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        Rol rol = this.buscarRol(id);

        if (rol == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        this.em.remove(rol);
        return Response.ok(rol).build();
    }

}

