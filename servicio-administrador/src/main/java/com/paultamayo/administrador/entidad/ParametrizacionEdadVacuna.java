package com.paultamayo.administrador.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.paultamayo.base.enumerador.ActivoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "param_edad_vacuna")
public class ParametrizacionEdadVacuna {

	@Enumerated(EnumType.STRING)
	private ActivoEnum activo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer maximo;

	private Integer minimo;

	@Column(name = "vacuna_id")
	private Long vacunaId;

}
