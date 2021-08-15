package com.paultamayo.ciudadano.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.enumerador.EstadoRespuestaEnum;
import com.paultamayo.ciudadano.servicios.CiudadanoServicio;
import com.paultamayo.ciudadano.to.RespuestaTo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class CiudadanoControlador {

	private static <T> ResponseEntity<RespuestaTo<T>> responderError(Exception ex) {
		return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.ERROR, ex.getMessage(), null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

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

	@GetMapping("/todos")
	public ResponseEntity<RespuestaTo<Iterable<Ciudadano>>> buscarTodos() {
		try {
			Iterable<Ciudadano> listado = servicio.buscarTodos();
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, listado), HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@GetMapping("/buscar/{estado}")
	public ResponseEntity<RespuestaTo<List<Ciudadano>>> buscarPorEstados(EstadoCiudadanoEnum estado) {
		try {
			List<Ciudadano> listado = servicio.buscarPorEstado(estado);
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, listado), HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@ResponseBody
	@PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<Ciudadano>> crear(@RequestBody @Valid Ciudadano ciudadano) {
		try {
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, servicio.guardar(ciudadano)),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@GetMapping("/")
	public String index() {
		return "Servicios de Ciudadanos";
	}

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
