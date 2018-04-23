/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.resources;

import com.estebanbank.dao.DepartamentoDao;
import com.estebanbank.dao.MunicipioDao;
import com.estebanbank.dto.DepartamentoDto;
import com.estebanbank.dto.ErrorMessageDto;
import com.estebanbank.model.Departamento;
import com.estebanbank.model.Municipio;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoly
 */
@Stateless
@Path("/departamentos")
public class DepartamentoEndpoint {

    DepartamentoDao dpDao;
    MunicipioDao mnDao;

    /**
     * Empty constructor
     */
    public DepartamentoEndpoint() {
        this.dpDao = null;
        this.mnDao = null;
    }

    /**
     * Constructor with DI
     *
     * @param dpDao
     * @param mnDao
     */
    @Inject
    public DepartamentoEndpoint(DepartamentoDao dpDao, MunicipioDao mnDao) {
        this.dpDao = dpDao;
        this.mnDao = mnDao;
    }

    /**
     * Creates a Departamento object from an existing one
     *
     * @param currentDep
     * @return
     */
    public Departamento createDepartmentObject(Departamento currentDep) {
        List<Municipio> actualMns = new ArrayList<>();

        currentDep.getMunicipios()
                .stream()
                .forEach((currentMun) -> actualMns.add(
                new Municipio(
                        currentMun.getId(),
                        currentMun.getCodigo(),
                        currentMun.getNombre(),
                        null,
                        null)
        ));

        return new Departamento(
                currentDep.getId(),
                currentDep.getCodigo(),
                currentDep.getNombre(),
                actualMns
        );
    }

    /**
     * Creates a Departamento object model from a DTO
     *
     * @param dto
     * @return
     */
    public Departamento createFromDto(DepartamentoDto dto) {
        List<Municipio> actualMns = new ArrayList<>();

        if (dto.getMunicipios() != null && dto.getMunicipios().size() > 0) {
            dto.getMunicipios()
                    .stream()
                    .forEach((mnId)
                            -> actualMns.add(this.mnDao.find(mnId))
                    );
        }

        return new Departamento(
                dto.getId(),
                dto.getCodigo(),
                dto.getNombre(),
                actualMns
        );
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {
        Departamento departamento = this.dpDao.find(id);
        if (departamento == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "Recurso no encontrado"))
                    .build();
        }

        return Response.ok(createDepartmentObject(departamento)).build();
    }

    @GET
    @Produces({"application/json"})
    public List<Departamento> findAll() {
        List<Departamento> actualDeps = new ArrayList<>();

        this.dpDao.findAll()
                .stream()
                .forEach((currentDep)
                        -> actualDeps.add(createDepartmentObject(currentDep))
                );

        return actualDeps;
    }

    @POST
    @Path("/")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(DepartamentoDto dto) {
        String name = dto.getNombre();

        Departamento existent = this.dpDao.find(name);

        if (existent != null) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(new ErrorMessageDto(false, Response.Status.CONFLICT.getStatusCode(), "Recurso ya existe"))
                    .build();
        }

        Departamento actualDep = createFromDto(dto);
        this.dpDao.save(actualDep);
        return Response.ok(createDepartmentObject(actualDep)).build();
    }

    @PUT
    @Produces({"application/json"})
    public Response update(DepartamentoDto dto) throws RollbackException {
        Departamento currentDep = createFromDto(dto);

        Departamento updatedDep = this.dpDao.edit(currentDep);
        if (updatedDep == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "Recurso no encontrado"))
                    .build();
        }

        return Response.ok(createDepartmentObject(updatedDep)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({"application/json"})
    public Response delete(@PathParam("id") Integer id) {
        Departamento dep = this.dpDao.remove(id);

        if (dep == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "Recurso no encontrado"))
                    .build();
        }

        return Response.ok(createDepartmentObject(dep)).build();
    }
}
