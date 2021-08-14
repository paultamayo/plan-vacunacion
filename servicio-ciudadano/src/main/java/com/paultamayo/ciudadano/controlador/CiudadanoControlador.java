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
import com.paultamayo.ciudadano.servicios.CiudadanoServicio;

@RestController
public class CiudadanoControlador {

	@Autowired
	private CiudadanoServicio servicio;

	@GetMapping("/todos")
	public ResponseEntity<List<Ciudadano>> buscarTodos() {
		return new ResponseEntity<>(servicio.buscarTodos(), HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ciudadano> crear(@RequestBody @Valid Ciudadano ciudadano) {
		return new ResponseEntity<>(servicio.guardar(ciudadano), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@GetMapping("/")
	public String index() {
		return "Servicios de Ciudadanos";
	}
}
