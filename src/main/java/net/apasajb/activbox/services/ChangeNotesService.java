package net.apasajb.activbox.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.ChangeNote;
import net.apasajb.activbox.repositories.ChangeNoteRepository;


/**
 * Comporte des methodes permettant de manipuler les notes de changement.
 */
@Component
public class ChangeNotesService {
	
	@Autowired
	ChangeNoteRepository changeNoteRepository;
	
	@Autowired
	TicketService ticketService;
	
	public void ajouterNoteChangement(ChangeNote changeNote) {
		
		LocalDateTime momentCreation = LocalDateTime.now();
		momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
		
		String momentCreationPourAffichage = ticketService.formatterDateHeurePourAffichage(momentCreation);
		
		changeNote.setCol03MomentCreation(momentCreation);
		changeNote.setCol031MomentCreationPourAffichage(momentCreationPourAffichage);
		changeNoteRepository.save(changeNote);
	}
	
	public List<String[]> getToutesNotesPourChangement(String numeroChangement){
		
		List<String[]> listeMessagesAffichables = new ArrayList<String[]>();
		List<ChangeNote> listeNotesChangement = changeNoteRepository.findByCol02NumeroTicketIgnoreCase(numeroChangement);
		
		/* Formattage des notes dans un message exploitable */
		for (ChangeNote changeNote : listeNotesChangement) {
			
			String[] messageAffichable = new String[3];
			messageAffichable[0] = changeNote.getCol031MomentCreationPourAffichage();
			messageAffichable[1] = changeNote.getCol04Auteur();
			messageAffichable[2] = changeNote.getCol05Message();
			
			listeMessagesAffichables.add(messageAffichable);
		}
		
		return listeMessagesAffichables;
	}
}
