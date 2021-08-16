package com.paultamayo.administrador.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.servicio.AdministracionVacunasServicio;
import com.paultamayo.administrador.servicio.AsignacionVacunaServicio;
import com.paultamayo.base.enumerador.EstadoRespuestaEnum;
import com.paultamayo.base.to.CiudadanoTo;
import com.paultamayo.base.to.RespuestaTo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController()
@RequestMapping("AsignacionVacuna")
public class AsignacionVacunaControlador {

	@Autowired
	private AsignacionVacunaServicio asignacionVacunaServicio;

	@Autowired
	private AdministracionVacunasServicio administracionServicio;

	@ResponseBody
	@GetMapping(path = "/asignar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<List<CiudadanoTo>>> asignar() {
		try {
			return new ResponseEntity<>(
					new RespuestaTo<>(EstadoRespuestaEnum.OK, null, administracionServicio.asignarPrioridadVacunas()),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	@ResponseBody
	@GetMapping(path = "/listar/{fechaInicio}/{fechaFin}")
	public ResponseEntity<RespuestaTo<List<AsignacionVacuna>>> listarAsignaciones(@PathVariable String fechaInicio,
			@PathVariable String fechaFin) {
		try {
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			return new ResponseEntity<>(
					new RespuestaTo(EstadoRespuestaEnum.OK, null, asignacionVacunaServicio.buscarPorFechaProgramada(
							LocalDate.parse(fechaInicio, formateador), LocalDate.parse(fechaFin, formateador))),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}

	public <X> ResponseEntity<RespuestaTo<X>> responderError(Exception ex) {
		return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.ERROR, ex.getMessage(), null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
