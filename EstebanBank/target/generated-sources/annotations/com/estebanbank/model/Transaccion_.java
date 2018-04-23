package com.estebanbank.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaccion.class)
public abstract class Transaccion_ {

	public static volatile SingularAttribute<Transaccion, Float> monto;
	public static volatile SingularAttribute<Transaccion, Float> montoFinal;
	public static volatile SingularAttribute<Transaccion, Cuenta> cuenta;
	public static volatile SingularAttribute<Transaccion, Integer> id;
	public static volatile SingularAttribute<Transaccion, Operacion> operacion;
	public static volatile SingularAttribute<Transaccion, Date> fechaMovimiento;

}

