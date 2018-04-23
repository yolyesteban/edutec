package com.estebanbank.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Departamento.class)
public abstract class Departamento_ {

	public static volatile ListAttribute<Departamento, Municipio> municipios;
	public static volatile SingularAttribute<Departamento, String> codigo;
	public static volatile SingularAttribute<Departamento, Integer> id;
	public static volatile SingularAttribute<Departamento, String> nombre;

}

