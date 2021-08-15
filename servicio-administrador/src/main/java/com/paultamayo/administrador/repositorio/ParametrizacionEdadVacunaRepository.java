package com.paultamayo.administrador.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.base.enumerador.ActivoEnum;

@Repository
public interface ParametrizacionEdadVacunaRepository extends CrudRepository<ParametrizacionEdadVacuna, Long> {

	List<ParametrizacionEdadVacuna> findByActivo(ActivoEnum activo);
}
