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
public class SalonDto {
    
    private Integer id;
    private String codigo;
    private Integer sede;

    public SalonDto() {
    }

    public SalonDto(Integer id, String codigo, Integer sede) {
        this.id = id;
        this.codigo = codigo;
        this.sede = sede;
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
     * @return the sede
     */
    public Integer getSede() {
        return sede;
    }

    /**
     * @param sede the sede to set
     */
    public void setSede(Integer sede) {
        this.sede = sede;
    }        
    
}
