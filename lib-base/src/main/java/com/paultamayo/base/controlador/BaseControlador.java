package com.paultamayo.base.controlador;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paultamayo.base.enumerador.EstadoRespuestaEnum;
import com.paultamayo.base.servicio.BaseServicio;
import com.paultamayo.base.to.RespuestaTo;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BaseControlador<T, K> {

	protected abstract BaseServicio<T, K> getServicio();

	public <X> ResponseEntity<RespuestaTo<X>> responderError(Exception ex) {
		return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.ERROR, ex.getMessage(), null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/")
	public String index() {
		return "Servicio Activo";
	}
	
	@ResponseBody
	@PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<T>> crear(@RequestBody @Valid T t) {
		try {
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, getServicio().guardar(t)),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<RespuestaTo<Iterable<T>>> buscarTodos() {
		try {
			Iterable<T> listado = getServicio().buscarTodos();
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null, listado), HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

}
