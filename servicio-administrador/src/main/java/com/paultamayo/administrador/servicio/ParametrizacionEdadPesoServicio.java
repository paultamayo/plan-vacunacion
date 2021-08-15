package com.paultamayo.administrador.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.administrador.repositorio.ParametrizacionEdadPesoRepository;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class ParametrizacionEdadPesoServicio extends BaseServicio<ParametrizacionEdadPeso, Long> {

	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	private ParametrizacionEdadPesoRepository repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ParametrizacionEdadPeso> buscarPorActivo(ActivoEnum activo) {
		return repositorio.findByActivo(activo);
	}

}
