/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.CicloDto;
import com.edutech.javaee.s03.e01.model.Ciclo;
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
@Path("/ciclos")
public class CicloEndpoint {
    @PersistenceContext(unitName = "primary")
    EntityManager em;  
    
        private Ciclo buscarCiclo (Integer id){
            try {
                //Consulta JPQL
                return this.em.createQuery("SELECT c FROM Ciclo c WHERE c.id = :id ", Ciclo.class)
                .setParameter("id",id)
                .getSingleResult();
            
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    @GET
    @Produces({"application/json"})
    public List<Ciclo> findAll() {
        List<Ciclo> ciclo = em.createQuery("SELECT c FROM ciclo c", Ciclo.class)
                .getResultList();        
        return ciclo;
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Integer id) {
        //Usuario usuario = this.em.find(Usuario.class, id); 
        
        Ciclo ciclo = null;
        
        try {
                //Consulta JPQL
                ciclo = this.em.createQuery("SELECT c FROM Ciclo c WHERE c.id = :id ", Ciclo.class)
                .setParameter("id",id)
                .getSingleResult();
            
        } catch (NoResultException nre) {
        }
                
        if (ciclo == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        return Response.ok(ciclo, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(CicloDto dto) {
        Ciclo ciclo = new Ciclo( 
                dto.getId(),
                dto.getCodigo()
            );
        this.em.persist(ciclo);
        return Response.ok(ciclo).build();
    }

    @PUT
    @Path("{id}")
    @Produces({"application/json"})
    public Response update(CicloDto dto) throws RollbackException {
        
        //Usuario usuario = em.find(Usuario.class, dto.getId());
        
        Ciclo ciclo = this.buscarCiclo(dto.getId());
        
        if (ciclo == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
        
        ciclo.setCodigo(dto.getCodigo());   
        this.em.merge(ciclo);
        return Response.ok(ciclo).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        //Usuario usuario = em.find(Usuario.class, id);
        Ciclo ciclo = this.buscarCiclo(id);
        
        if (ciclo == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")                    
                    .build();
            
        this.em.remove(ciclo);
        return Response.ok(ciclo).build();
    }
    
    
}
