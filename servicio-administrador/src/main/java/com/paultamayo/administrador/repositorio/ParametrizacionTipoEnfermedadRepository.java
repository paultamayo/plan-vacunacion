package com.paultamayo.administrador.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;

@Repository
public interface ParametrizacionTipoEnfermedadRepository extends CrudRepository<ParametrizacionTipoEnfermedad, Long> {

}
