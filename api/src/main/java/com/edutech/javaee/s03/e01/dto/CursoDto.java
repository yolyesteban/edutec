/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edutech.javaee.s03.e01.dto;

/**
 *
 * @author Yoly
 */
public class CursoDto {
    private Integer id;
    private String codigo;
    private String direccion;
    private Integer ciclo;
    private Integer salon;

    public CursoDto() {
    }

    public CursoDto(Integer id, String codigo, String direccion, Integer ciclo, Integer salon) {
        this.id = id;
        this.codigo = codigo;
        this.direccion = direccion;
        this.ciclo = ciclo;
        this.salon = salon;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the ciclo
     */
    public Integer getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the salon
     */
    public Integer getSalon() {
        return salon;
    }

    /**
     * @param salon the idSalon to set
     */
    public void setIdSalon(Integer salon) {
        this.salon = salon;
    }

    
    
}
