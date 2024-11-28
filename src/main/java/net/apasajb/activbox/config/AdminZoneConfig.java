package net.apasajb.activbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:admin_zone.properties")
public class AdminZoneConfig {
	
	@Value("${passeport_admin}")
	private String adminPassword;
	
	// ==== GETTERS & SETTERS
	
	public String getAdminPassword() {
		return adminPassword;
	}
	
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}
