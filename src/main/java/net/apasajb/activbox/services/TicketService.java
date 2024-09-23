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
	
	String regexIncident = "^[Ii][Nn][0-9]{8}$";			//=> IN00000000
	String regexChangement = "^[Cc][Hh][0-9]{8}$";			//=> CH00000000
	/*
	String regexDemandeInfo = "^[Dd][Ii][0-9]{8}$";			//=> DI00000000
	String regexDemandeSolution = "^[Dd][Ss][0-9]{8}$";		//=> DS00000000
	String regexTache = "^[Tt][Aa][0-9]{8}$";				//=> TA00000000
	String regexProjet = "^[Pp][Rr][0-9]{8}$";				//=> PR00000000
	*/
	
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
	
	public String getRegexIncident() {
		return regexIncident;
	}
	
	public String getRegexChangement() {
		return regexChangement;
	}
	
	/*
	 * Quand ce sera necessaire, ajouter d'autres methodes getRegex ici.
	 */
	
	public String getUtilisateurActuel() {
		// A adapter
		return "User_01";
	}
}
