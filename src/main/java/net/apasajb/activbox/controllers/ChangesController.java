package net.apasajb.activbox.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.apasajb.activbox.entities.Change;
import net.apasajb.activbox.entities.ChangeNote;
import net.apasajb.activbox.repositories.ChangeRepository;
import net.apasajb.activbox.services.ChangeNotesService;
import net.apasajb.activbox.services.ChangeValidation;
import net.apasajb.activbox.services.ChangesService;
import net.apasajb.activbox.services.TicketService;


/**
 * Controleur pour les changements.
 */
@Controller
public class ChangesController {
	
	@Autowired
	ChangeRepository changeRepository;
	
	@Autowired
	ChangesService changesService;
	
	@Autowired
	ChangeNotesService changeNotesService;
	
	@Autowired
	TicketService ticketService;
	
	String statutInitial = "Nouveau";
	
	/* LES 2 METHODES SUIVANTES CONSTITUENT UNE PAIRE GET & POST */
	
	/**
	 * Affiche le formulaire de création d'un changement.
	 */
	@GetMapping("/nouveau-changement")
	public ModelAndView afficherFormulaireNouveauChangement() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("nouveau-changement.html");
		modelAndView.addObject("objetTicket", new Change());
		
		return modelAndView;
	}
	
	/**
	 * Gère la création d'un nouveau changement.
	 */
	@PostMapping("/nouveau-changement")
	public ModelAndView ajouterChangement(Change newChange) {
		
		/* D'abord validation */
		ChangeValidation changeValidation = new ChangeValidation();
		boolean isChangeValid = changeValidation.isNewChangeValid(newChange);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if (isChangeValid) {
			/* Si le changement est valide */
			
			modelAndView.setViewName("details-changement.html");
			newChange.setCol03TypeTicket("Changement");
			
			/* On ajoute la date & l'heure de création */
			LocalDateTime momentCreation = LocalDateTime.now();
			momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
			newChange.setCol09MomentCreation(momentCreation);
			
			/* On ajouter le meme moment au format compact pour affichage */
			String momentCreationPourAffichage = ticketService.formatterDateHeurePourAffichage(momentCreation);
			newChange.setCol091MomentCreationPourAffichage(momentCreationPourAffichage);
			
			// On ajoute un statut initial
			newChange.setCol11Etat(statutInitial);
			
			// On écrit l'entité en BDD
			Change changementEnBdd = changeRepository.save(newChange);
			
			/* On met à jour le numéro de ticket en BDD */
			int idChangement = changementEnBdd.getCol01Id();
			String numeroChangement = changesService.genererNumeroChangement(idChangement);
			changementEnBdd.setCol02NumeroTicket(numeroChangement);
			changeRepository.save(changementEnBdd);
			
			/* On enregistre une note initiale */
			String auteurNote = changementEnBdd.getCol04AgentInitial();
			String messageNoteInitial = "Nouveau changement [" + numeroChangement + "] créé par " + auteurNote;
			
			ChangeNote changeNote = new ChangeNote(numeroChangement, momentCreation, auteurNote, messageNoteInitial);
			changeNotesService.ajouterNoteChangement(changeNote);
			
			/* On recupere toutes les notes disponibles pour le changement */
			List<String[]> listeNotes = changeNotesService.getToutesNotesPourChangement(numeroChangement);
			modelAndView.addObject("listeNotes", listeNotes);
			
			/* On repond à l'utilisateur */
			String titreTicket = "Ticket " + changementEnBdd.getCol02NumeroTicket() + " (changement)";
			String messageSucces = "Changement créé correctement";
			
			modelAndView.addObject("titreTicket", titreTicket);
			modelAndView.addObject("auteurActuel", "Grafo55");    //////// point d'attention
			modelAndView.addObject("objetTicket", changementEnBdd);
			modelAndView.addObject("messageSucces", messageSucces);
			
		} else {
			/* Si le changement n'est pas valide */
			
			modelAndView.setViewName("nouveau-changement.html");
			
			String messageErreur = changeValidation.getMessageErreurValidation();
			boolean isPrioritehValid = changeValidation.isPrioritehValid();    // choix multiple
			boolean isAgentInitialValid = changeValidation.isAgentInitialValid();
			boolean isDemandeurValid = changeValidation.isDemandeurValid();
			boolean isEntrepriseValid = changeValidation.isEntrepriseValid();
			boolean isCategorieValid = changeValidation.isCategorieValid();    // choix multiple
			boolean isEquipeEnChargeValid = changeValidation.isEquipeEnChargeValid();    // choix multiple
			boolean isSujetValid = changeValidation.isSujetValid();
			boolean isDescriptionValid = changeValidation.isDescriptionValid();
			
			modelAndView.addObject("isPrioritehValid", isPrioritehValid);
			modelAndView.addObject("isAgentInitialValid", isAgentInitialValid);
			modelAndView.addObject("isDemandeurValid", isDemandeurValid);
			modelAndView.addObject("isEntrepriseValid", isEntrepriseValid);
			modelAndView.addObject("isCategorieValid", isCategorieValid);
			modelAndView.addObject("isEquipeEnChargeValid", isEquipeEnChargeValid);
			modelAndView.addObject("isSujetValid", isSujetValid);
			modelAndView.addObject("isDescriptionValid", isDescriptionValid);
			
			modelAndView.addObject("objetTicket", newChange);
			modelAndView.addObject("messageErreur", messageErreur);
		}
		
		return modelAndView;
	}
	
	/**
	 * Affiche les détails d'un changement.
	 */
	@GetMapping("/affichage-changement-{numero}")
	public ModelAndView afficherChangement(@PathVariable("numero") String paramNumero) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-changement.html");
		
		try {
			List<Change> listeChangements = changeRepository.findByCol02NumeroTicket(paramNumero);
			
			Change changementTrouveh = listeChangements.get(0);
			modelAndView.addObject("objetTicket", changementTrouveh);
			
			List<String[]> listeNotes = changeNotesService.getToutesNotesPourChangement(paramNumero);
			modelAndView.addObject("listeNotes", listeNotes);
			
			// A FAIRE EVOLUER
			modelAndView.addObject("auteurActuel", "Grafo55");    //////// point d'attention
			modelAndView.addObject("titreTicket", "Ticket " + changementTrouveh.getCol02NumeroTicket() + " (changement)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageInfo", "Info: Aucun ticket trouvé pour le numéro \"" + paramNumero + "\"");
		}
		
		return modelAndView;
	}
	
	/**
	 * Gère la modification de données d'un changement.
	 */
	@PostMapping("/modif-changement")
	public ModelAndView modifierDonneesChangement(
			@RequestParam("col02NumeroTicket") String paramNumeroChangement,
			@RequestParam("col10Prioriteh") String paramPrioriteh,
			@RequestParam("col11Etat") String paramEtat,
			@RequestParam("col12EquipeEnCharge") String paramEquipeEnCharge,
			@RequestParam("col13AgentEnCharge") String paramAgentEnCharge) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-changement.html");
		
		String messageNote = "Modifications:";
		boolean isPrioritehNew = false;
		boolean isEtatNew = false;
		boolean isEquipeEnChargeNew = false;
		boolean isAgentEnChargeNew = false;
		boolean isAgentEnChargeValid = false;
		
		try {
			Change change = changeRepository.findByCol02NumeroTicket(paramNumeroChangement).get(0);
			
			if (!change.getCol10Prioriteh().equals(paramPrioriteh)) {
				// Si la valeur de la prioriteh change
				
				isPrioritehNew = true;
				messageNote = messageNote + " Priorité [" + change.getCol10Prioriteh() + " -> " + paramPrioriteh + "];";
				change.setCol10Prioriteh(paramPrioriteh);
			}
			
			if (!change.getCol11Etat().equals(paramEtat)) {
				// Si la valeur de l'Etat change
				
				isEtatNew = true;
				messageNote = messageNote + " Etat [" + change.getCol11Etat() + " -> " + paramEtat + "];";
				change.setCol11Etat(paramEtat);
			}
			
			if (!change.getCol12EquipeEnCharge().equals(paramEquipeEnCharge)) {
				// Si la valeur de l'Equipe en charge change
				
				isEquipeEnChargeNew = true;
				messageNote = messageNote + " Equipe en Charge [" + change.getCol12EquipeEnCharge() + " -> " + paramEquipeEnCharge + "];";
				change.setCol12EquipeEnCharge(paramEquipeEnCharge);
			}
			
			if (change.getCol13AgentEnCharge() != paramAgentEnCharge) {
				// Si la valeur de l'Agent en charge change
				
				isAgentEnChargeNew = true;
				messageNote = messageNote + " Agent en charge [" + change.getCol13AgentEnCharge() + " -> " + paramAgentEnCharge + "];";
				change.setCol13AgentEnCharge(paramAgentEnCharge);
			}
			
			if (!paramAgentEnCharge.isBlank()) {
				isAgentEnChargeValid = true;
			}
			
			/* Si au moins une donnée change, on enregistre la modification
			 * et on ajoute une note en BDD.
			 */
			if (isPrioritehNew || isEquipeEnChargeNew || isAgentEnChargeNew || isEtatNew) {
				
				if (isAgentEnChargeValid) {
					
					changeRepository.save(change);
					LocalDateTime momentActuel = ticketService.getMomentActuel();
					String utilisateurActuel = ticketService.getUtilisateurActuel();
					ChangeNote changeNote = new ChangeNote(paramNumeroChangement,
							momentActuel, utilisateurActuel, messageNote);
					changeNotesService.ajouterNoteChangement(changeNote);
					modelAndView.addObject("messageSucces", "Données modifiées correctement.");
					
				} else {
					
					modelAndView.addObject("messageErreur", "ERREUR. Valeur vide pour l'agent en charge.");
					isAgentEnChargeValid = false;
				}
				
			} else {
				modelAndView.addObject("messageErreur", "ERREUR. Aucune valeur ne change, rien à sauvegarder.");
			}
			
			modelAndView.addObject("objetTicket", change);
			modelAndView.addObject("titreTicket", "Ticket " + paramNumeroChangement + " (changement)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		
		} finally {
			modelAndView.addObject("isAgentEnChargeValid", isAgentEnChargeValid);
		}
		
		/* On affiche toutes les notes du ticket actuel */
		List<String[]> listeNotes = changeNotesService.getToutesNotesPourChangement(paramNumeroChangement);
		modelAndView.addObject("listeNotes", listeNotes);
		
		return modelAndView;
	}
	
	/**
	 * Gère la cloture d'un changement.
	 */
	@PostMapping("/cloture-changement")
	public ModelAndView cloreChangement(
			@RequestParam("col02NumeroTicket") String paramNumeroChangement,
			@RequestParam("col05Message") String paramMessage) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-changement.html");
		
		try {
			Change change = changeRepository.findByCol02NumeroTicket(paramNumeroChangement).get(0);
			
			if (!paramMessage.isBlank()) {
				
				change.setCol18MessageResolution(paramMessage);
				change.setCol11Etat("clos");
				
			} else {
				modelAndView.addObject("messageErreurCloture", "Un message de fin est necessaire pour cloturer ce ticket!");
			}
			
			LocalDateTime momentActuel = ticketService.getMomentActuel();
			
			/* on enregistre les modifications dans la BDD */
			change.setCol18MessageResolution(paramMessage);
			change.setCol17MomentFin(momentActuel);
			changeRepository.save(change);
			modelAndView.addObject("objetTicket", change);
			
			/* On ajoute une note */
			String messageNote = "[Cloture du ticket] " + paramMessage;
			String utilisateurActuel = ticketService.getUtilisateurActuel();
			ChangeNote changeNote = new ChangeNote(paramNumeroChangement, momentActuel, utilisateurActuel, messageNote);
			changeNotesService.ajouterNoteChangement(changeNote);
			
			/* On affiche toutes les notes du ticket actuel */
			List<String[]> listeNotes = changeNotesService.getToutesNotesPourChangement(paramNumeroChangement);
			modelAndView.addObject("listeNotes", listeNotes);
			
			// On repond à l'utilisateur
			modelAndView.addObject("titreTicket", "Ticket " + paramNumeroChangement + " (changement)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		}
		
		return modelAndView;
	}
	
	/**
	 * Gère l'ajout d'une note à un changement.
	 */
	@PostMapping("/new-note-changement")
	public ModelAndView ajouterNoteChangement(ChangeNote changeNote) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-changement.html");
		
		String utilisateurActuel = ticketService.getUtilisateurActuel();
		changeNote.setCol04Auteur(utilisateurActuel);
		
		String numeroChangement = changeNote.getCol02NumeroTicket();
		boolean isChangementPresent = false;
		int idChangement = 0;
		
		/* Vérif si changement présent en BDD */
		try {
			idChangement = changeRepository.findByCol02NumeroTicket(numeroChangement).get(0).getCol01Id();
			isChangementPresent = (idChangement > 0) ? true : false;
			
		} catch (Exception ignore) {}
		
		if (isChangementPresent) {
			/* Si l'entité est présente en BDD */
			
			changeNotesService.ajouterNoteChangement(changeNote);
			List<String[]> listeNotes = changeNotesService.getToutesNotesPourChangement(numeroChangement);
			modelAndView.addObject("listeNotes", listeNotes);
			
			List<Change> listeChangements = changeRepository.findByCol02NumeroTicket(numeroChangement);
			Change changementTrouveh = listeChangements.get(0);
			modelAndView.addObject("objetTicket", changementTrouveh);
			modelAndView.addObject("titreTicket", "Ticket " + numeroChangement + " (changement)");
			
		} else {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + numeroChangement);
		}
		
		return modelAndView;
	}
}
