package com.paultamayo.administrador.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;
import com.paultamayo.administrador.repositorio.AsignacionVacunaRepositorio;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class AsignacionVacunaServicio extends BaseServicio<AsignacionVacuna, AsignacionVacunaID> {

	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	private AsignacionVacunaRepositorio repositorio;

}
