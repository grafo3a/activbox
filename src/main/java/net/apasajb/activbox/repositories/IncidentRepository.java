package net.apasajb.activbox.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.apasajb.activbox.entities.Incident;


/**
 * Un repository pour les incidents.
 * @author ApasaJB
 */
public interface IncidentRepository extends JpaRepository<Incident, Integer> {
	
	/* On ne fait rien ici sauf si necessaire.
	 * On peut toutefois ajouter des methodes personalisees
	 */
	
	/**
	 * Recherche un incident sur base d'un numero de ticket.
	 */
	List<Incident> findByCol02NumeroTicket(String col02NumeroTicket);
	
	/**
	 * Recherche une liste d'incidents sur base d'un mot-clef.<br/>
	 * La recherche s'effectue sur la colonne du sujet.
	 */
	List<Incident> findByCol15SujetContaining(String motClef);
}
