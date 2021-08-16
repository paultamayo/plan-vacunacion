package com.paultamayo.oauth.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import lombok.Data;

@Entity
@Data
@Table(name = "SEG_CLIENTE")
public class Cliente implements ClientDetails {

	private static final long serialVersionUID = 4547107013080110857L;

	@Id
	@Column(name = "ID")
	private String clientId;

	@Column(name = "RECURSOS_IDS")
	private String recursoIds;

	@Column(name = "CLAVE_SECRETA")
	private String clientSecret;

	@Column(name = "SCOPE")
	private String scope;

	@Column(name = "TIPO_DE_AUTORIDADES")
	private String tipoAutoridades;

	@Column(name = "VALIDAR_TOKEN_ACCESO")
	public Integer accessTokenValiditySeconds;

	@Column(name = "VALIDAR_TOKEN_ACTUALIZACION")
	public Integer refreshTokenValiditySeconds;

	@Column(name = "REDIRECCION_URL")
	public String redireccionamientoUrl;

	@Column(name = "AUTORIDADES")
	private String autoridades;

	@Override
	public Set<String> getResourceIds() {
		String[] ids = recursoIds.split(",");
		return new HashSet<>(Arrays.asList(ids));
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public Set<String> getScope() {
		String[] ids = scope.split(",");
		return new HashSet<>(Arrays.asList(ids));
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		String[] ids = tipoAutoridades.split(",");
		return new HashSet<>(Arrays.asList(ids));
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return new HashSet<>(Arrays.asList(redireccionamientoUrl));
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<String> listado = new ArrayList<>(Arrays.asList(autoridades.split(",")));

		return listado.stream().map(m -> new Autoridad(m, null)).collect(Collectors.toList());
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return new HashMap<>();
	}

}
