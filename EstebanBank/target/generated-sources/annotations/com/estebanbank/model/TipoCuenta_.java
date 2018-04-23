package com.estebanbank.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoCuenta.class)
public abstract class TipoCuenta_ {

	public static volatile SingularAttribute<TipoCuenta, String> descripcion;
	public static volatile ListAttribute<TipoCuenta, Cuenta> cuentas;
	public static volatile SingularAttribute<TipoCuenta, Integer> id;
	public static volatile SingularAttribute<TipoCuenta, String> nombre;
	public static volatile SingularAttribute<TipoCuenta, Float> tasaInteres;

}

