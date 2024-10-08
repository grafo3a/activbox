package net.apasajb.activbox.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.IncidentNote;
import net.apasajb.activbox.repositories.IncidentNoteRepository;


/**
 * Comporte des methodes permettant de manipuler les notes d'incident.
 */
@Component
public class IncidentNotesService {
	
	@Autowired
	IncidentNoteRepository incidentNoteRepository;
	
	@Autowired
	TicketService ticketService;
	
	public void ajouterNoteIncident(IncidentNote incidentNote) {
		
		LocalDateTime momentCreation = LocalDateTime.now();
		momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
		
		String momentCreationPourAffichage = ticketService.formatterDateHeurePourAffichage(momentCreation);
		
		incidentNote.setCol03MomentCreation(momentCreation);
		incidentNote.setCol031MomentCreationPourAffichage(momentCreationPourAffichage);
		incidentNoteRepository.save(incidentNote);
	}
	
	public List<String[]> getToutesNotesPourIncident(String numeroIncident){
		
		List<String[]> listeMessagesAffichables = new ArrayList<String[]>();
		List<IncidentNote> listeNotesIncident = incidentNoteRepository.findByCol02NumeroTicketIgnoreCase(numeroIncident);
		
		/* Formattage des notes dans un message exploitable */
		for (IncidentNote incidentNote : listeNotesIncident) {
			
			String[] messageAffichable = new String[3];
			messageAffichable[0] = incidentNote.getCol031MomentCreationPourAffichage();
			messageAffichable[1] = incidentNote.getCol04Auteur();
			messageAffichable[2] = incidentNote.getCol05Message();
			
			listeMessagesAffichables.add(messageAffichable);
		}
		
		return listeMessagesAffichables;
	}
}
