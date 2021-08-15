package com.paultamayo.administrador.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;
import com.paultamayo.administrador.servicio.AdministracionVacunasServicio;
import com.paultamayo.administrador.servicio.AsignacionVacunaServicio;
import com.paultamayo.administrador.to.VacunaParametroTo;
import com.paultamayo.base.controlador.BaseControlador;
import com.paultamayo.base.enumerador.EstadoRespuestaEnum;
import com.paultamayo.base.to.RespuestaTo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController("AsignacionVacuna")
public class AsignacionVacunaControlador extends BaseControlador<AsignacionVacuna, AsignacionVacunaID> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private AsignacionVacunaServicio servicio;
	
	@Autowired
	private AdministracionVacunasServicio administracionServicio;
	
	@ResponseBody
	@PostMapping(path = "/asignar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaTo<Vacuna>> modificarInventario(@RequestBody @Valid VacunaParametroTo param) {
		try {
			return new ResponseEntity<>(new RespuestaTo<>(EstadoRespuestaEnum.OK, null,
					servicio.actualizar(param.getVacunaId(), param.getCantidad())), HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return responderError(ex);
		}
	}
}
