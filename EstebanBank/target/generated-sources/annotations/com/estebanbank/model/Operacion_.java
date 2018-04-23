package com.estebanbank.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Operacion.class)
public abstract class Operacion_ {

	public static volatile SingularAttribute<Operacion, String> descripcion;
	public static volatile ListAttribute<Operacion, Transaccion> transacciones;
	public static volatile SingularAttribute<Operacion, Integer> id;
	public static volatile SingularAttribute<Operacion, String> nombre;

}

