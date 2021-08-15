package com.paultamayo.administrador.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class VacunaTo {

	@NotBlank
	private String nombre;

	@NotNull
	@Positive
	private Long cantidad;
}
