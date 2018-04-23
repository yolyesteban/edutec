package com.edutech.javaee.s03.e01.resources;

import com.edutech.javaee.s03.e01.dto.DepartamentoDto;
import com.edutech.javaee.s03.e01.model.Departamento;
import com.edutech.javaee.s03.e01.model.Municipio;
import java.util.ArrayList;
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
 * @author yoly
 */
@Stateless
@Path("/departamentos")
public class DepartamentoEndpoint {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    private Departamento buscarDepartamento(Integer id) {
        try {
            return this.em
                    .createQuery("SELECT u FROM Departamento u JOIN FETCH u.municipios WHERE u.id = :id", Departamento.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    private List<Municipio> buscarMunicipios(List<Integer> ids) {
        List<Municipio> municipios = new ArrayList<Municipio>();

        for (Integer municipio : ids) {
            municipios.add(this.em.find(Municipio.class, municipio));
        }

        return municipios;
    }

    @GET
    @Produces({"application/json"})
    public List<Departamento> findAll() {
        List<Departamento> departamento = this.em
                .createQuery("SELECT u FROM Departamento u JOIN FETCH u.municipios", Departamento.class)
                .getResultList();
        return departamento;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {
        Departamento departamento = this.buscarDepartamento(id);

        if (departamento == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        return Response.ok(departamento, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(DepartamentoDto dto) {
        List<Municipio> municipios = buscarMunicipios(dto.getMunicipios());

        Departamento departamento = new Departamento(
                dto.getCodigo(),
                dto.getNombre(),
                municipios
        );

        this.em.persist(departamento);

        return Response.ok(departamento).build();
    }

    @PUT
    @Produces({"application/json"})
    public Response update(DepartamentoDto dto) throws RollbackException {
        Departamento dp = this.buscarDepartamento(dto.getId());

        if (dp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        dp.setCodigo(dto.getCodigo());
        dp.setNombre(dto.getNombre());
        dp.setMunicipios(buscarMunicipios(dto.getMunicipios()));

        this.em.merge(dp);
        return Response.ok(dp).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        Departamento dp = this.buscarDepartamento(id);

        if (dp == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_HTML)
                    .entity("Recurso no encontrado")
                    .build();
        }

        this.em.remove(dp);
        return Response.ok(dp).build();
    }
}
