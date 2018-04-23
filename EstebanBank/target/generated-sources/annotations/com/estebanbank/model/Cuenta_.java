package com.estebanbank.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cuenta.class)
public abstract class Cuenta_ {

	public static volatile SingularAttribute<Cuenta, Cliente> cliente;
	public static volatile SingularAttribute<Cuenta, Integer> estado;
	public static volatile SingularAttribute<Cuenta, Float> monto;
	public static volatile SingularAttribute<Cuenta, Date> fechaApertura;
	public static volatile ListAttribute<Cuenta, Transaccion> transacciones;
	public static volatile SingularAttribute<Cuenta, String> moneda;
	public static volatile SingularAttribute<Cuenta, TipoCuenta> tipoCuenta;
	public static volatile SingularAttribute<Cuenta, Integer> id;

}

