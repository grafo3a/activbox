package net.apasajb.activbox.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	public String formatterDateHeurePourAffichage(LocalDateTime moment) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String momentCompact = moment.format(formatter);
		//LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
		
		return momentCompact;
	}
	
	public String getUtilisateurActuel() {
		// A adapter
		return "User_01";
	}
}
