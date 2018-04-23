/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.AsignacionProfesorDto;
import com.edutech.javaee.s03.e01.model.AsignacionProfesor;
import com.edutech.javaee.s03.e01.model.Curso;
import com.edutech.javaee.s03.e01.model.Profesor;
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
@Path("/asignacionProfesores")
public class AsignacionProfesorEndpoint {
    
    @PersistenceContext(unitName = "primary")
    EntityManager em;

    private AsignacionProfesor buscarAsignacionProfesor(Integer id) {
        try {
            return this.em
                    .createQuery("SELECT u FROM AsignacionProfesor u JOIN FETCH u.profesor JOIN FETCH u.curso WHERE u.id = :parametro", AsignacionProfesor.class)
                    .setParameter("parametro", id)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }
    
    @GET
    @Produces({"application/json"})
    public List<AsignacionProfesor> findAll() {
        List<AsignacionProfesor> aprofesor = this.em
                .createQuery("SELECT u FROM AsignacionProfesor u JOIN FETCH u.profesor JOIN FETCH u.curso", AsignacionProfesor.class)
                .getResultList();        
        return aprofesor;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {      
        AsignacionProfesor aprofesor = this.buscarAsignacionProfesor(id);
        
        if (aprofesor == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        return Response.ok(aprofesor, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(AsignacionProfesorDto dto) {
        AsignacionProfesor aprofesor = new AsignacionProfesor(
                this.em.find(Profesor.class, dto.getProfesor()),
                this.em.find(Curso.class, dto.getCurso())             
            );
        this.em.persist(aprofesor);
        return Response.ok(aprofesor).build();
    }

    @PUT
    @Produces({"application/json"})
    public Response update(AsignacionProfesorDto dto) throws RollbackException {
        AsignacionProfesor aprofesor = this.buscarAsignacionProfesor(dto.getId());

        if (aprofesor == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        aprofesor.setCurso(this.em.find(Curso.class, dto.getCurso()));
        aprofesor.setProfesor(this.em.find(Profesor.class, dto.getProfesor()));
        
        this.em.merge(aprofesor);
        return Response.ok(aprofesor).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        AsignacionProfesor aprofesor = this.buscarAsignacionProfesor(id);

        if (aprofesor == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
            
        this.em.remove(aprofesor);
        return Response.ok(aprofesor).build();
    }
}
