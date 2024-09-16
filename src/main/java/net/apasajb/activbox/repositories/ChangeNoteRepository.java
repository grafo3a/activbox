package net.apasajb.activbox.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.apasajb.activbox.entities.ChangeNote;


/**
 * Un repository pour les notes de changement.
 * @author ApasaJB
 */
public interface ChangeNoteRepository extends JpaRepository<ChangeNote, Integer> {
	
	/**
	 * Recherche de toutes les notes de changement sur base d'un numero de ticket.
	 */
	List<ChangeNote> findByCol02NumeroTicketIgnoreCase(String numeroTicket);
}
