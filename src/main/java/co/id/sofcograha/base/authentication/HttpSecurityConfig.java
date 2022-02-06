package co.id.sofcograha.base.authentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import co.id.sofcograha.base.authentication.filters.AnonymousAuthenticationFilter;
import co.id.sofcograha.base.authentication.filters.CorsFilter;

@EnableWebSecurity
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.anyRequest()
				.fullyAuthenticated();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(new CorsFilter(),AbstractPreAuthenticatedProcessingFilter.class);
		http.addFilterBefore(new AnonymousAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
	}
}
