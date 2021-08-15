package com.paultamayo.administrador.entidad;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.paultamayo.base.enumerador.ActivoEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "param_edad_peso")
public class ParametrizacionEdadPeso {

	@Enumerated(EnumType.STRING)
	private ActivoEnum activo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer maximo;

	private Integer minimo;

	private Integer valor;
}
