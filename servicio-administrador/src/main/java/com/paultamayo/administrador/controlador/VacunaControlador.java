package com.paultamayo.administrador.controlador;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.servicio.VacunaServicio;
import com.paultamayo.administrador.to.VacunaParametroTo;
import com.paultamayo.administrador.to.VacunaTo;
import com.paultamayo.base.controlador.BaseControlador;
import com.paultamayo.base.enumerador.EstadoRespuestaEnum;
import com.paultamayo.base.to.RespuestaTo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController("Vacuna")
public class VacunaControlador extends BaseControlador<Vacuna, Long> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private VacunaServicio servicio;

	@ResponseBody
	@PostMapping(path = "/modificar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<Vacuna>> modificarInventario(@RequestBody @Valid VacunaParametroTo param) {
		try {
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null,
					servicio.actualizar(param.getVacunaId(), param.getCantidad())), HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@ResponseBody
	@PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<Vacuna>> crear(@RequestBody @Valid VacunaTo vacunaTo) {
		try {
			Vacuna vacuna = new Vacuna();
			BeanUtils.copyProperties(vacunaTo, vacuna);

			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, getServicio().guardar(vacuna)),
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
