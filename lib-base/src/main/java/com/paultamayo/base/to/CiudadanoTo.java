package com.paultamayo.base.to;

import lombok.Data;

@Data
public class CiudadanoTo {

	private boolean asignado;

	private String cedula;

	private Long edad;

	private String mensajeError;

	private Integer peso;

	private Long prioridad;

	private Long tipoEnfermedadId;

	private Long vacunaId;
}
