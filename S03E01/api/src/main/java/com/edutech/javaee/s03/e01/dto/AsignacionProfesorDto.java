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
public class AsignacionProfesorDto {
    private Integer id;
    private Integer profesor;
    private Integer curso;

    public AsignacionProfesorDto() {
    }

    public AsignacionProfesorDto(Integer id, Integer profesor, Integer curso) {
        this.id = id;
        this.profesor = profesor;
        this.curso = curso;
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
     * @return the profesor
     */
    public Integer getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(Integer profesor) {
        this.profesor = profesor;
    }

    /**
     * @return the curso
     */
    public Integer getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Integer curso) {
        this.curso = curso;
    } 
    
}
