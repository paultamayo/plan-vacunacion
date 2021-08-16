package com.paultamayo.administrador.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.administrador.entidad.Ciudadano;
import com.paultamayo.administrador.servicio.CiudadanoServicio;
import com.paultamayo.base.controlador.BaseControlador;
import com.paultamayo.base.enumerador.EstadoRespuestaEnum;
import com.paultamayo.base.to.RespuestaTo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController()
@RequestMapping("Ciudadano")
public class CiudadanoControlador extends BaseControlador<Ciudadano, String> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private CiudadanoServicio servicio;

	@GetMapping("/cedula/{cedula}")
	public ResponseEntity<RespuestaTo<Ciudadano>> buscarPorCedula(String cedula) {
		try {
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, servicio.buscarPorId(cedula)),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public RespuestaTo<Map<String, String>> manejadorExcepcion(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new RespuestaTo<>(EstadoRespuestaEnum.ERROR, "Error en la validación de los parámetros", errors);
	}
}
