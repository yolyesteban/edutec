/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.CursoDto;
import com.edutech.javaee.s03.e01.model.Ciclo;
import com.edutech.javaee.s03.e01.model.Curso;
import com.edutech.javaee.s03.e01.model.Salon;
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
@Path("/cursos")
public class CursoEndpoint {
    
    @PersistenceContext(unitName = "primary")
    EntityManager em;
    
    private Curso buscarCurso (Integer id){
            try {
                //Consulta JPQL
                return this.em.createQuery("SELECT c FROM Curso c JOIN FETCH c.ciclo JOIN FETCH c.salon WHERE c.id = :id ", Curso.class)
                .setParameter("id",id)
                .getSingleResult();
            
        } catch (NoResultException nre) {
            return null;
        }
    }
        
    @GET
    @Produces({"application/json"})
    public List<Curso> findAll() {
        List<Curso> cursos = em.createQuery("SELECT c FROM Curso c JOIN FETCH c.ciclo JOIN FETCH c.salon", Curso.class)
                .getResultList();        
        return cursos;
    } 
    
    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Integer id) {

        Curso curso = this.buscarCurso(id);
                
        if (curso == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        return Response.ok(curso, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(CursoDto dto) {
        Curso curso = new Curso(
                dto.getCodigo(),
                dto.getDireccion(),
                this.em.find(Ciclo.class, dto.getCiclo()),
                this.em.find(Salon.class, dto.getSalon())
            );
        this.em.persist(curso);
        return Response.ok(curso).build();
    }

    @PUT
    @Path("{id}")
    @Produces({"application/json"})
    public Response update(CursoDto dto) throws RollbackException {
        
        //Usuario usuario = em.find(Usuario.class, dto.getId());
        
        //Usuario usuario = this.buscarUsuario(dto.getId());
        Curso curso = this.buscarCurso(dto.getId());
        
        if (curso == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        curso.setCodigo(dto.getCodigo());
        curso.setCiclo(this.em.find(Ciclo.class, dto.getCiclo()));
        curso.setSalon(this.em.find(Salon.class, dto.getSalon()));        
        this.em.merge(curso);
        return Response.ok(curso).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        //Usuario usuario = em.find(Usuario.class, id);
        Curso curso = this.buscarCurso(id);
        
        if (curso == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
            
        this.em.remove(curso);
        return Response.ok(curso).build();
    }
    
}
