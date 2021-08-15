package com.paultamayo.administrador.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.servicio.VacunaServicio;
import com.paultamayo.base.controlador.BaseControlador;

import lombok.AccessLevel;
import lombok.Getter;

@RestController
public class VacunaControlador extends BaseControlador<Vacuna, Long> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private VacunaServicio servicio;



	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> manejadorExcepcion(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
