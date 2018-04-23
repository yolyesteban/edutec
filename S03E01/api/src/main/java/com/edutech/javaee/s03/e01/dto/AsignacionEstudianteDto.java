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
public class AsignacionEstudianteDto {
    private Integer id;
    private Integer estudiante;
    private Integer curso;
    private Float zona;
    private Float examenFinal;
    private Float notaFinal;

    public AsignacionEstudianteDto() {
    }

    public AsignacionEstudianteDto(Integer id, Integer estudiante, Integer curso, Float zona, Float examenFinal, Float notaFinal) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
        this.zona = zona;
        this.examenFinal = examenFinal;
        this.notaFinal = notaFinal;
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
     * @return the estudiante
     */
    public Integer getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(Integer estudiante) {
        this.estudiante = estudiante;
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

    /**
     * @return the zona
     */
    public Float getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(Float zona) {
        this.zona = zona;
    }

    /**
     * @return the examenFinal
     */
    public Float getExamenFinal() {
        return examenFinal;
    }

    /**
     * @param examenFinal the examenFinal to set
     */
    public void setExamenFinal(Float examenFinal) {
        this.examenFinal = examenFinal;
    }

    /**
     * @return the notaFinal
     */
    public Float getNotaFinal() {
        return notaFinal;
    }

    /**
     * @param notaFinal the notaFinal to set
     */
    public void setNotaFinal(Float notaFinal) {
        this.notaFinal = notaFinal;
    }
    
    
    
}
