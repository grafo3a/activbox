package net.apasajb.activbox.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;


/**
 * Comporte des methodes generiques utilisables par tous les tickets.
 */
@Component
public class TicketService {
	
	public LocalDateTime getMomentActuel() {
		
		LocalDateTime momentActuel = LocalDateTime.now();
		momentActuel = momentActuel.truncatedTo(ChronoUnit.SECONDS);
		
		return momentActuel;
	}
	
	public String getUtilisateurActuel() {
		// A adapter
		return "User_01";
	}
}
