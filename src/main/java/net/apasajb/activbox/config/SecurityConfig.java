package net.apasajb.activbox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import net.apasajb.activbox.services.AuthenticationSuccessHandler;


/**
 * Configuration manuelle de Spring security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				// On active la prise en charge de requetes post
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(registry -> {
					
					// Les URLs accessibles sans authentification
					registry.requestMatchers(
							"/inscription",
							"/nouveau-compte",
							"/h2-console/**",
							"/css/**",
							"/images/**",
							"/js/**"
							).permitAll();
					
					// Les URLs accessibles avec authentification
					registry.requestMatchers("/admin/**").hasRole("ADMIN");
					registry.anyRequest().authenticated();
				})
				
				// On definit une page de login manuellement.
				// L'Url /auth est definie dans le controleur RegistrationController
				.formLogin(httpSecurityFormLoginConfigurer -> { httpSecurityFormLoginConfigurer
					.loginPage("/login")
					.successHandler(new AuthenticationSuccessHandler())
					//.failureHandler(new CustomAuthenticationFailureHandler())
					.permitAll();
				})
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
