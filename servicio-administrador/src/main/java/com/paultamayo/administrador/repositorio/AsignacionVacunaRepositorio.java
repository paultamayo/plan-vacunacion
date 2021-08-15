package com.paultamayo.administrador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;
import com.paultamayo.administrador.to.AsignacionFechaTo;

@Repository
public interface AsignacionVacunaRepositorio extends CrudRepository<AsignacionVacuna, AsignacionVacunaID> {

	@Query(value = "select NEW com.paultamayo.administrador.to.AsignacionFechaTo(count(1), fecha_registro::date) from asignacion_vacuna av "
			+ "where fecha_registro::date = (select max(fecha_registro::date) from asignacion_vacuna av) "
			+ "group by fecha_registro::date", nativeQuery = true)
	Optional<AsignacionFechaTo> buscarAsignacion();
}
