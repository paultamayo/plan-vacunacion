package com.paultamayo.administrador.entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;

import lombok.Data;

@Data
@Entity
@Table(name = "asignacion_vacuna")
@IdClass(AsignacionVacunaID.class)
public class AsignacionVacuna {

	@Id
	private String ciudadanoId;

	private LocalDate fechaProgramada;

	private LocalDateTime fechaRegistro;

	@Id
	private Long vacunaId;
}
