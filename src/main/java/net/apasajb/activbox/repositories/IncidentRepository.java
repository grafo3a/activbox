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
	List<Incident> findByCol02NumeroIncident(String col02NumeroIncident);
	
}
