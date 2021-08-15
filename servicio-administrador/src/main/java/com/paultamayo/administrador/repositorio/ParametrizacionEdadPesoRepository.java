package com.paultamayo.administrador.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.base.enumerador.ActivoEnum;

@Repository
public interface ParametrizacionEdadPesoRepository extends CrudRepository<ParametrizacionEdadPeso, Long> {

	
	List<ParametrizacionEdadPeso> findByActivo(ActivoEnum activo);
}
