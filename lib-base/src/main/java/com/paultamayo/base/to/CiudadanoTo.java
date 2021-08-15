package com.paultamayo.base.to;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CiudadanoTo {

	private boolean asignado;

	@NonNull
	private String cedula;

	@NonNull
	private Integer edad;

	private boolean programado;

	private String mensajeError;

	private Integer peso;

	@NonNull
	private Long prioridad;

	@NonNull
	private Long tipoEnfermedadId;

	private List<Long> vacunasId;
}
