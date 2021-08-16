package com.paultamayo.administrador.repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;

@Repository
public interface AsignacionVacunaRepositorio extends CrudRepository<AsignacionVacuna, AsignacionVacunaID> {

	@Query(value = "select fecha_programada\\:\\:date, count(1) from asignacion_vacuna av "
			+ "where fecha_programada\\:\\:date = (select max(fecha_programada\\:\\:date) from asignacion_vacuna av) "
			+ "group by fecha_programada\\:\\:date;", nativeQuery = true)
	Optional<Object[]> buscarAsignacion();
	
	List<AsignacionVacuna> findByFechaProgramadaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
