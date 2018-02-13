package com.edutech.javaee.s03.e01.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author nahum
 */
@Entity
@Table(name="PROFESOR")
public class Profesor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profesorGen")
    @SequenceGenerator(name="profesorGen", sequenceName = "profesor_seq", initialValue = 10)
    private Integer id;
    
    private String carnet;
    private String nombre;
    private String direccion;

    public Profesor() {
    }

    public Profesor(Integer id, String carnet, String nombre, String direccion) {
        this.id = id;
        this.carnet = carnet;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Profesor(String carnet, String nombre, String direccion) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.direccion = direccion;
    }       
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
