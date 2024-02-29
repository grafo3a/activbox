package net.apasajb.activbox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import net.apasajb.activbox.entities.Incident;


public interface IncidentRepository extends JpaRepository<Incident, Integer> {
	
	/* On ne fait rien ici sauf si necessaire.
	 * On peut toutefois ajouter une methode personalisee
	 */
}
