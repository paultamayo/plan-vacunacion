package com.paultamayo.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStorage());
		resources.resourceId("086dd508-5ace-4724-aa5f-21eff662be9b");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/seguridad/oauth/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/ciudadano/crear").permitAll()
				.antMatchers(HttpMethod.POST, "/api/ciudadano/**").hasAnyAuthority("USER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/ciudadano/**").hasAnyAuthority("USER", "ADMIN").anyRequest()
				.authenticated();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter token = new JwtAccessTokenConverter();
		token.setSigningKey("b20007df-c091-4261-9b62-d61e2879ddfd");

		return token;
	}

	@Bean
	public JwtTokenStore tokenStorage() {
		return new JwtTokenStore(accessTokenConverter());
	}

}
