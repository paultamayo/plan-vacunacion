package com.paultamayo.oauth.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Table(name = "SEG_AUTORIDAD")
public class Autoridad implements GrantedAuthority {

	private static final long serialVersionUID = 3381790968358364227L;

	@Id
	@Column(name = "NOMBRE")
	private String authority;

	@Column(name = "DESCRIPCION")
	private String description;
}
