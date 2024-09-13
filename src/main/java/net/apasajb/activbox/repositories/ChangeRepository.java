package net.apasajb.activbox.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.apasajb.activbox.entities.Change;


/**
 * Un repository pour les changements.
 * @author ApasaJB
 */
public interface ChangeRepository extends JpaRepository<Change, Integer> {
	
	/**
	 * Recherche un changement sur base d'un numero de ticket.
	 */
	List<Change> findByCol02NumeroTicket(String col02NumeroTicket);
	
	/**
	 * Recherche une liste de changements sur base d'un mot-clef.<br/>
	 * La recherche s'effectue sur la colonne du sujet.
	 */
	List<Change> findByCol15SujetContaining(String motClef);
	
	/**
	 * Recherche la liste des changements actifs.
	 */
	@Query("FROM Change WHERE col11Etat != 'clos'")
	List<Change> findListeChangementsEtatOuvert();
	
	/**
	 * Recherche la liste des changements actifs dans une equipe.
	 */
	@Query("FROM Change WHERE col12EquipeEnCharge =?1 AND col11Etat != 'clos'")
	List<Change> findListeChangementsEtatOuvertEquipeActuelle(String equipeEnCharge);
}
