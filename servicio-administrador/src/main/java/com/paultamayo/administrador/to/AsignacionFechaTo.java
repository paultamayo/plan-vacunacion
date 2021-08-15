package com.paultamayo.administrador.to;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AsignacionFechaTo {

	private Integer cantidad;

	private LocalDate fecha;
}
