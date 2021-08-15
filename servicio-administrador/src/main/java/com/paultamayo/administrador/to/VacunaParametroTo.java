package com.paultamayo.administrador.to;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class VacunaParametroTo {

	@NotNull
	@Positive
	private Long cantidad;

	@NotNull
	@Positive
	private Long vacunaId;

}
