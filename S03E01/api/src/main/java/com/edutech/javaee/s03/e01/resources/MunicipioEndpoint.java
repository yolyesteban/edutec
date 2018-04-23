/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.MunicipioDto;
import com.edutech.javaee.s03.e01.model.Departamento;
import com.edutech.javaee.s03.e01.model.Municipio;
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
@Path("/municipios")
public class MunicipioEndpoint {
    
    @PersistenceContext(unitName = "primary")
    EntityManager em;

    private Municipio buscarMunicipio(Integer id) {
        try {
            return this.em
                    .createQuery("SELECT u FROM Municipio u JOIN FETCH u.departamento WHERE u.id = :parametro", Municipio.class)
                    .setParameter("parametro", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @GET
    @Produces({"application/json"})
    public List<Municipio> findAll() {
        List<Municipio> municipio = this.em
                .createQuery("SELECT u FROM Municipio u JOIN FETCH u.departamento", Municipio.class)
                .getResultList();
        return municipio;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {
        Municipio municipio = this.buscarMunicipio(id);

        if (municipio == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        return Response.ok(municipio, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(MunicipioDto dto) {
        Municipio municipio = new Municipio(
                dto.getCodigo(),
                dto.getNombre(),
                this.em.find(Departamento.class, dto.getDepartamento())
        );

        this.em.persist(municipio);

        return Response.ok(municipio).build();
    }

    @PUT
    @Produces({"application/json"})
    public Response update(MunicipioDto dto) throws RollbackException {
        Municipio municipio = this.buscarMunicipio(dto.getId());

        if (municipio == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        municipio.setCodigo(dto.getCodigo());
        municipio.setNombre(dto.getNombre());
        municipio.setDepartamento(this.em.find(Departamento.class, dto.getDepartamento()));

        this.em.merge(municipio);
        return Response.ok(municipio).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        Municipio municipio = this.buscarMunicipio(id);

        if (municipio == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        this.em.remove(municipio);
        return Response.ok(municipio).build();
    }

}
