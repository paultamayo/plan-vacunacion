package com.paultamayo.administrador.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.administrador.repositorio.ParametrizacionEdadVacunaRepository;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class ParametrizacionEdadVacunaServicio extends BaseServicio<ParametrizacionEdadVacuna, Long> {

	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	private ParametrizacionEdadVacunaRepository repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ParametrizacionEdadVacuna> buscarPorActivo(ActivoEnum activo) {
		return repositorio.findByActivo(activo);
	}

}
