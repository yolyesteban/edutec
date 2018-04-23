/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.resources;

import com.estebanbank.dao.CuentaDao;
import com.estebanbank.dao.OperacionDao;
import com.estebanbank.dao.TransaccionDao;
import com.estebanbank.dto.ErrorMessageDto;
import com.estebanbank.dto.PagoTarjetaDto;
import com.estebanbank.dto.TransaccionDto;
import com.estebanbank.dto.TransferenciaDto;
import com.estebanbank.model.Cuenta;
import com.estebanbank.model.Operacion;
import com.estebanbank.model.Transaccion;
import com.estebanbank.service.TarjetaService;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoly
 */
@Stateless
@Path("/transacciones")
public class TransaccionEndpoint {

    final TransaccionDao trDao;
    final CuentaDao cnDao;
    final OperacionDao opDao;

    final static int DEBIT = 1;
    final static int CREDIT = 2;

    public TransaccionEndpoint() {
        this.trDao = null;
        this.cnDao = null;
        this.opDao = null;
    }

    @Inject
    public TransaccionEndpoint(TransaccionDao trDao, CuentaDao cnDao, OperacionDao opDao) {
        this.trDao = trDao;
        this.cnDao = cnDao;
        this.opDao = opDao;
    }

    /**
     * Creates a response object from an existing one
     *
     * @param current
     * @return
     */
    public Transaccion createResponseObject(Transaccion current) {
        Cuenta curCuenta = current.getCuenta();

        Operacion currentOp = current.getOperacion();

        Operacion actualOp = new Operacion(
                currentOp.getId(),
                currentOp.getNombre(),
                currentOp.getDescripcion(),
                null
        );

        return new Transaccion(
                current.getId(),
                current.getFechaMovimiento(),
                current.getMonto(),
                current.getMontoFinal(),
                new Cuenta(
                        curCuenta.getId(),
                        curCuenta.getMoneda(),
                        curCuenta.getFechaApertura(),
                        curCuenta.getEstado(),
                        null,
                        null,
                        null,
                        curCuenta.getMonto()
                ),
                actualOp
        );
    }

    /**
     * Creates a response object model from a DTO
     *
     * @param dto
     * @return
     */
    public Transaccion createFromDto(TransaccionDto dto) {
        return new Transaccion(
                dto.getId(),
                dto.getFechaMovimiento(),
                dto.getMonto(),
                dto.getMontoFinal(),
                this.cnDao.find(dto.getCuenta()),
                this.opDao.find(dto.getOperacion())
        );
    }

    public float getTotalAmount(Cuenta cnt, float amount, int type) {
        float total = cnt.getMonto();

        if (type == CREDIT) {
            return total + amount;
        }

        if (type == DEBIT) {
            return total - amount;
        }

        throw new InternalError("Debe elegir una opción válida. 1=Débito, 2=CRÉDITO");
    }

    @GET
    @Produces({"application/json"})
    public List<Transaccion> findAll() {
        List<Transaccion> actualLst = new ArrayList<>();

        this.trDao.findAll()
                .stream()
                .forEach((currentObj)
                        -> actualLst.add(createResponseObject(currentObj))
                );

        return actualLst;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") Integer id) {
        Transaccion tr = this.trDao.find(id);

        if (tr == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "Recurso no encontrado"))
                    .build();
        }

        return Response.ok(createResponseObject(tr), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(TransaccionDto dto) {
        Cuenta cnt = this.cnDao.find(dto.getCuenta());

        //Verificar si la cuenta existe
        if (cnt == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "No se encontró la cuenta"))
                    .build();
        }

        float saldo = getTotalAmount(cnt, dto.getMonto(), dto.getOperacion());

        //Verificar saldo para ver si el monto es suficiente para la transaccion
        if (saldo >= 0) {
            Transaccion tr = createFromDto(dto);

            tr.setMontoFinal(saldo);

            this.trDao.save(tr);

            cnt.setMonto(saldo);
            this.cnDao.save(cnt);

            return Response.ok(createResponseObject(tr)).build();
        }

        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorMessageDto(false, 404, "El monto no es suficiente para la transacción"))
                .build();

    }

    @PUT
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response transferencia(TransferenciaDto dto) {
        Cuenta cntOrigin = this.cnDao.find(dto.getCuentaOrigen());

        //Validar cuenta de origen
        if (cntOrigin == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "No se encontró la cuenta"))
                    .build();
        }

        Cuenta cntDestiny = this.cnDao.find(dto.getCuentaDestino());

        //Validar cuenta de destino
        if (cntDestiny == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "No se encontró la cuenta"))
                    .build();
        }

        float saldo = getTotalAmount(cntOrigin, dto.getMonto(), DEBIT);

        //Verificar saldo para ver si el monto es suficiente para la transaccion
        if (saldo >= 0) {

            float saldo2 = getTotalAmount(cntDestiny, dto.getMonto(), CREDIT);
            Transaccion tr = new Transaccion(dto.getId(), dto.getFechaMovimiento(), dto.getMonto(), (float) 0, cntOrigin, this.opDao.find(DEBIT));
            tr.setMontoFinal(saldo);
            this.trDao.save(tr);
            cntOrigin.setMonto(saldo);
            this.cnDao.save(cntOrigin);

            Transaccion tr2 = new Transaccion(dto.getId(), dto.getFechaMovimiento(), dto.getMonto(), (float) 0, cntDestiny, this.opDao.find(CREDIT));
            tr2.setMontoFinal(saldo2);
            this.trDao.save(tr2);
            cntDestiny.setMonto(saldo2);
            this.cnDao.save(cntDestiny);

            List<Transaccion> cuentas = new ArrayList<>();

            cuentas.add(createResponseObject(tr));
            cuentas.add(createResponseObject(tr2));

            return Response.ok(cuentas).build();
        }

        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorMessageDto(false, 404, "El monto no es suficiente para la transacción"))
                .build();
    }

    @POST
    @Path("pagarTarjeta") //
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response pagarTarjeta(TransaccionDto dto) { //
        Cuenta cnt = this.cnDao.find(dto.getCuenta());

        //Validamos si la cuenta existe
        if (cnt == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessageDto(false, 404, "No se encontró la cuenta"))
                    .build();
        }

        float saldo = getTotalAmount(cnt, dto.getMonto(), DEBIT);
        
        //Verificar Saldo para pagar tarjeta
        if (saldo >= 0) {
            TarjetaService srv = new TarjetaService();

            PagoTarjetaDto tcDto = new PagoTarjetaDto();

            tcDto.setMontoPago(dto.getMonto());
            tcDto.setNumeroTarjeta(dto.getTarjeta());
            
            try{

            PagoTarjetaDto actualizado = srv.pay(tcDto);

            if (actualizado != null && actualizado.getDisponible() > 0) {
                dto.setOperacion(DEBIT);
                Transaccion tr = createFromDto(dto);

                tr.setMontoFinal(saldo);

                this.trDao.save(tr);

                cnt.setMonto(saldo);
                this.cnDao.save(cnt);

                return Response.ok(createResponseObject(tr)).build();
            } else {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(new ErrorMessageDto(false, Response.Status.CONFLICT.getStatusCode(), "No se pudo pagar la tarjeta"))
                        .build();
            }
            }catch(Exception ex){
                 return Response
                        .status(Response.Status.CONFLICT)
                        .entity(new ErrorMessageDto(false, Response.Status.CONFLICT.getStatusCode(), ex.getMessage()))
                        .build();
            }
        }

        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorMessageDto(false, 404, "El monto no es suficiente para la transacción"))
                .build();

    }

    @POST
    @Path("consumirTarjeta")  
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response consumirTarjeta(TransaccionDto dto) {   
        TarjetaService srv = new TarjetaService();
        PagoTarjetaDto tcDto = new PagoTarjetaDto();

        tcDto.setMontoPago(dto.getMonto());
        tcDto.setNumeroTarjeta(dto.getTarjeta());

        try {
            PagoTarjetaDto actualizado = srv.consume(tcDto);

            //Validar si la tarjeta ingresada existe
            if (actualizado != null && !"".equals(actualizado.getNumeroTarjeta())) {
                return Response.ok(actualizado).build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorMessageDto(false, 404, "No se encontró la tarjeta ingresada"))
                        .build();
            }
        } catch (Exception ex) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(new ErrorMessageDto(false, 404, ex.getMessage()))
                    .build();
        }

    }
}

