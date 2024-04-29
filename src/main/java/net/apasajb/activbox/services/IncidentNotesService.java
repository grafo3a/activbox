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
 * Comporte des methodes pour manipuler les notes d'incident.
 */
@Component
public class IncidentNotesService {
	
	@Autowired
	IncidentNoteRepository incidentNoteRepository;
	
	public void ajouterNoteIncident(IncidentNote incidentNote) {
		
		//IncidentNote incidentNote = new IncidentNote();
		//incidentNote.setCol02NumeroIncident(numeroIncident);
		
		//incidentNote.setCol04Auteur("Auteur Grafo");    // A faire evoluer
		//incidentNote.setCol05Message(message);
		
		LocalDateTime momentCreation = LocalDateTime.now();
		momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
		incidentNote.setCol03MomentCreation(momentCreation);
		
		incidentNoteRepository.save(incidentNote);
	}
	
	public List<String[]> getToutesNotesPourIncident(String numeroIncident){
		
		//String[] messageAffichable = new String[2];
		List<String[]> listeMessagesAffichables = new ArrayList<String[]>();
		List<IncidentNote> listeNotesIncident = incidentNoteRepository.findByCol02NumeroIncidentIgnoreCase(numeroIncident);
		
		/* Formattage des notes dans un message exploitable */
		for (IncidentNote incidentNote : listeNotesIncident) {
			
			String[] messageAffichable = new String[2];
			
			messageAffichable[0] = incidentNote.getCol03MomentCreation()
					+ " " + incidentNote.getCol04Auteur();
			messageAffichable[1] = incidentNote.getCol05Message();
			
			listeMessagesAffichables.add(messageAffichable);
		}
		
		return listeMessagesAffichables;
	}
}
