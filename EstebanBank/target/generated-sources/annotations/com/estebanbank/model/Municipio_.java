package com.estebanbank.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Municipio.class)
public abstract class Municipio_ {

	public static volatile SingularAttribute<Municipio, String> codigo;
	public static volatile SingularAttribute<Municipio, Departamento> departamento;
	public static volatile SingularAttribute<Municipio, Integer> id;
	public static volatile ListAttribute<Municipio, Cliente> clientes;
	public static volatile SingularAttribute<Municipio, String> nombre;

}

