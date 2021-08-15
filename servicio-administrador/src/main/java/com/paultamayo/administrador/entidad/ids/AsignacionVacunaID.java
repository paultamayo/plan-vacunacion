package com.paultamayo.administrador.entidad.ids;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsignacionVacunaID implements Serializable {

	private static final long serialVersionUID = 1688503345365235502L;

	private String ciudadanoId;

	private Long vacunaId;
}
