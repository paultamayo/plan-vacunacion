package com.paultamayo.base.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CiudadanoTo implements Serializable {

	private static final long serialVersionUID = 6608791361515360088L;

	private boolean asignado;

	@NonNull
	private String cedula;

	@NonNull
	private LocalDate fechaNacimiento;

	private boolean programado;

	private String mensajeError;

	private Integer peso;

	@NonNull
	private Long prioridad;

	@NonNull
	private Long tipoEnfermedadId;

	private List<Long> vacunasId;

	public Long getEdad() {
		return ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());
	}
}
