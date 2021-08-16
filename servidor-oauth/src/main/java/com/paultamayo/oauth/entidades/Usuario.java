package com.paultamayo.oauth.entidades;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
@Table(name = "SEG_USUARIO")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 4603619298267230506L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CUENTA_NO_EXPIRADA")
	private boolean accountNonExpired;

	@Column(name = "CUENTA_NO_BLOQUEADA")
	private boolean accountNonLocked;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SEG_USUARIO_AUTORIDAD", joinColumns = @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTORIDAD_ID", referencedColumnName = "NOMBRE"))
	private List<Autoridad> autoridades;

	@Column(name = "CREDENCIAL_NO_EXPIRADA")
	private boolean credentialsNonExpired;

	@Column(name = "HABILITADO")
	private boolean enabled;

	@Column(name = "CONTRASENIA")
	private String password;

	@Column(name = "NOMBRE_USUARIO")
	private String username;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autoridades;
	}

}
