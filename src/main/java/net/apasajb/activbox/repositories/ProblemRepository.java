package net.apasajb.activbox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import net.apasajb.activbox.entities.Problem;


public interface ProblemRepository extends JpaRepository<Problem, Integer> {
	
	/* On ne fait rien ici sauf si necessaire.
	 * On peut toutefois ajouter une methode personalisee
	 */
}
