package net.apasajb.activbox.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.apasajb.activbox.entities.IncidentNote;


/**
 * Un repository pour les notes d'incident.
 * @author ApasaJB
 */
public interface IncidentNoteRepository extends JpaRepository<IncidentNote, Integer> {
	
	/* On ne fait rien ici sauf si necessaire.
	 * On peut toutefois ajouter des methodes personalisees
	 */
	
	/**
	 * Recherche de toutes les notes d'incident sur base d'un numero de ticket.
	 */
	List<IncidentNote> findByCol02NumeroIncidentIgnoreCase(String numeroIncident);
}
