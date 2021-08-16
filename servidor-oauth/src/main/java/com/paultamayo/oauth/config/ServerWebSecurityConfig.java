package com.paultamayo.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.paultamayo.oauth.servicios.UsuarioServicio;

@Configuration
public class ServerWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioServicio servicio;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(servicio).passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
