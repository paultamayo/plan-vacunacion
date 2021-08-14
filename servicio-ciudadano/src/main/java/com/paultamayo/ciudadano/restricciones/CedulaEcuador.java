package com.paultamayo.ciudadano.restricciones;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CedulaEcuadorValidador.class)
@Documented
public @interface CedulaEcuador {

	String message() default "La cédula no tiene un formato válido.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
