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

import net.apasajb.activbox.entities.Incident;
import net.apasajb.activbox.entities.IncidentNote;
import net.apasajb.activbox.repositories.IncidentRepository;
import net.apasajb.activbox.services.IncidentNotesService;
import net.apasajb.activbox.services.IncidentValidation;
import net.apasajb.activbox.services.IncidentsService;
import net.apasajb.activbox.services.TicketService;


/**
 * Controleur pour les incidents.
 */
@Controller
public class IncidentsController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	@Autowired
	IncidentsService incidentsService;
	
	@Autowired
	IncidentNotesService incidentNotesService;
	
	@Autowired
	TicketService ticketService;
	
	String statutInitial = "Nouveau";
	
	/* LES 2 METHODES SUIVANTES CONSTITUENT UNE PAIRE GET & POST */
	
	/**
	 * Affiche le formulaire de création d'un incident.
	 */
	@GetMapping("/nouvel-incident")
	public ModelAndView afficherFormulaireNouvelIncident() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("nouvel-incident.html");
		modelAndView.addObject("objetTicket", new Incident());
		
		return modelAndView;
	}
	
	/**
	 * Gère la création d'un nouvel incident.
	 */
	@PostMapping("/nouvel-incident")
	public ModelAndView ajouterIncident(Incident newIncident) {
		
		/* D'abord validation */
		IncidentValidation incidentValidation = new IncidentValidation();
		boolean isincidentValid = incidentValidation.isNewIncidentValid(newIncident);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if (isincidentValid) {
			/* Si l'incident est valide */
			
			modelAndView.setViewName("details-incident.html");
			newIncident.setCol03TypeTicket("Incident");
			
			/* On ajoute la date & l'heure de création */
			LocalDateTime momentCreation = LocalDateTime.now();
			momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
			newIncident.setCol09MomentCreation(momentCreation);
			
			/* On ajouter le meme moment au format compact pour affichage */
			String momentCreationPourAffichage = ticketService.formatterDateHeurePourAffichage(momentCreation);
			newIncident.setCol091MomentCreationPourAffichage(momentCreationPourAffichage);
			
			// On ajoute un statut initial
			newIncident.setCol11Etat(statutInitial);
			
			// On écrit l'entité en BDD
			Incident incidentEnBdd = incidentRepository.save(newIncident);
			
			/* On met à jour le numéro de ticket en BDD */
			int idIncident = incidentEnBdd.getCol01Id();
			String numeroIncident = incidentsService.genererNumeroIncident(idIncident);
			incidentEnBdd.setCol02NumeroTicket(numeroIncident);
			incidentRepository.save(incidentEnBdd);
			
			/* On enregistre une note initiale */
			String auteurNote = incidentEnBdd.getCol04AgentInitial();
			String messageNoteInitial = "Nouvel incident [" + numeroIncident + "] créé par " + auteurNote;
			
			IncidentNote incidentNote = new IncidentNote(numeroIncident, momentCreation, auteurNote, messageNoteInitial);
			incidentNotesService.ajouterNoteIncident(incidentNote);
			
			/* On recupere toutes les notes disponibles pour l'incident */
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(numeroIncident);
			modelAndView.addObject("listeNotes", listeNotes);
			
			/* On repond à l'utilisateur */
			String titreTicket = "Ticket créé: incident " + incidentEnBdd.getCol02NumeroTicket();
			String messageSucces = "Incident créé correctement";
			
			modelAndView.addObject("titreTicket", titreTicket);
			modelAndView.addObject("auteurActuel", "Grafo55");
			modelAndView.addObject("objetTicket", incidentEnBdd);
			modelAndView.addObject("messageSucces", messageSucces);
			
		} else {
			/* Si l'incident n'est pas valide */
			
			modelAndView.setViewName("nouvel-incident.html");
			
			String messageErreur = incidentValidation.getMessageErreurValidation();
			boolean isPrioritehValid = incidentValidation.isPrioritehValid();    // choix multiple
			boolean isAgentInitialValid = incidentValidation.isAgentInitialValid();
			boolean isDemandeurValid = incidentValidation.isDemandeurValid();
			boolean isEntrepriseValid = incidentValidation.isEntrepriseValid();
			boolean isCategorieValid = incidentValidation.isCategorieValid();    // choix multiple
			boolean isEquipeEnChargeValid = incidentValidation.isEquipeEnChargeValid();    // choix multiple
			boolean isSujetValid = incidentValidation.isSujetValid();
			boolean isDescriptionValid = incidentValidation.isDescriptionValid();
			
			modelAndView.addObject("isPrioritehValid", isPrioritehValid);
			modelAndView.addObject("isAgentInitialValid", isAgentInitialValid);
			modelAndView.addObject("isDemandeurValid", isDemandeurValid);
			modelAndView.addObject("isEntrepriseValid", isEntrepriseValid);
			modelAndView.addObject("isCategorieValid", isCategorieValid);
			modelAndView.addObject("isEquipeEnChargeValid", isEquipeEnChargeValid);
			modelAndView.addObject("isSujetValid", isSujetValid);
			modelAndView.addObject("isDescriptionValid", isDescriptionValid);
			
			modelAndView.addObject("objetTicket", newIncident);
			modelAndView.addObject("messageErreur", messageErreur);
		}
		
		return modelAndView;
	}
	
	/**
	 * Affiche les détails d'un incident.
	 */
	@GetMapping("/affichage-incident-{numero}")
	public ModelAndView afficherIncident(@PathVariable("numero") String paramNumero) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		try {
			List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(paramNumero);
			
			Incident incidentTrouveh = listeIncidents.get(0);
			modelAndView.addObject("objetTicket", incidentTrouveh);
			
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumero);
			modelAndView.addObject("listeNotes", listeNotes);
			
			// A FAIRE EVOLUER
			modelAndView.addObject("auteurActuel", "Grafo55");
			modelAndView.addObject("titreTicket", "Ticket " + incidentTrouveh.getCol02NumeroTicket() + " (Incident)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageInfo", "Info: Aucun ticket trouvé pour le numéro \"" + paramNumero + "\"");
		}
		
		return modelAndView;
	}
	
	/**
	 * Gère la modification de données d'un incident.
	 */
	@PostMapping("/modif-incident")
	public ModelAndView modifierDonneesIncident(
			@RequestParam("col02NumeroTicket") String paramNumeroIncident,
			@RequestParam("col10Prioriteh") String paramPrioriteh,
			@RequestParam("col11Etat") String paramEtat,
			@RequestParam("col12EquipeEnCharge") String paramEquipeEnCharge,
			@RequestParam("col13AgentEnCharge") String paramAgentEnCharge) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		String messageNote = "Modifications:";
		boolean isPrioritehNew = false;
		boolean isEtatNew = false;
		boolean isEquipeEnChargeNew = false;
		boolean isAgentEnChargeNew = false;
		boolean isAgentEnChargeValid = false;
		
		try {
			Incident incident = incidentRepository.findByCol02NumeroTicket(paramNumeroIncident).get(0);
			
			if (!incident.getCol10Prioriteh().equals(paramPrioriteh)) {
				// Si la valeur de la prioriteh change
				
				isPrioritehNew = true;
				messageNote = messageNote + " Priorité [" + incident.getCol10Prioriteh() + " -> " + paramPrioriteh + "];";
				incident.setCol10Prioriteh(paramPrioriteh);
			}
			
			if (!incident.getCol11Etat().equals(paramEtat)) {
				// Si la valeur de l'Etat change
				
				isEtatNew = true;
				messageNote = messageNote + " Etat [" + incident.getCol11Etat() + " -> " + paramEtat + "];";
				incident.setCol11Etat(paramEtat);
			}
			
			if (!incident.getCol12EquipeEnCharge().equals(paramEquipeEnCharge)) {
				// Si la valeur de l'Equipe en charge change
				
				isEquipeEnChargeNew = true;
				messageNote = messageNote + " Equipe en Charge [" + incident.getCol12EquipeEnCharge() + " -> " + paramEquipeEnCharge + "];";
				incident.setCol12EquipeEnCharge(paramEquipeEnCharge);
			}
			
			if (incident.getCol13AgentEnCharge() != paramAgentEnCharge) {
				// Si la valeur de l'Agent en charge change
				
				isAgentEnChargeNew = true;
				messageNote = messageNote + " Agent en charge [" + incident.getCol13AgentEnCharge() + " -> " + paramAgentEnCharge + "];";
				incident.setCol13AgentEnCharge(paramAgentEnCharge);
			}
			
			if (!paramAgentEnCharge.isBlank()) {
				isAgentEnChargeValid = true;
			}
			
			/* Si au moins une donnée change, on enregistre la modification
			 * et on ajoute une note en BDD.
			 */
			if (isPrioritehNew || isEquipeEnChargeNew || isAgentEnChargeNew || isEtatNew) {
				
				if (isAgentEnChargeValid) {
					
					incidentRepository.save(incident);
					LocalDateTime momentActuel = ticketService.getMomentActuel();
					String utilisateurActuel = ticketService.getUtilisateurActuel();
					IncidentNote incidentNote = new IncidentNote(paramNumeroIncident,
							momentActuel, utilisateurActuel, messageNote);
					incidentNotesService.ajouterNoteIncident(incidentNote);
					modelAndView.addObject("messageSucces", "Données modifiées correctement.");
				
				} else {
					
					modelAndView.addObject("messageErreur", "ERREUR. Valeur vide pour l'agent en charge.");
					isAgentEnChargeValid = false;
				}
				
			} else {
				modelAndView.addObject("messageErreur", "ERREUR. Aucune valeur ne change, rien à sauvegarder.");
			}
			
			modelAndView.addObject("objetTicket", incident);
			modelAndView.addObject("titreTicket", "Ticket " + paramNumeroIncident + " (Incident)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		
		} finally {
			modelAndView.addObject("isAgentEnChargeValid", isAgentEnChargeValid);
		}
		
		/* On affiche toutes les notes du ticket actuel */
		List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroIncident);
		modelAndView.addObject("listeNotes", listeNotes);
		
		return modelAndView;
	}
	
	/**
	 * Gère la cloture d'un incident.
	 */
	@PostMapping("/cloture-incident")
	public ModelAndView cloreIncident(
			@RequestParam("col02NumeroTicket") String paramNumeroIncident,
			@RequestParam("col05Message") String paramMessage) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		try {
			Incident incident = incidentRepository.findByCol02NumeroTicket(paramNumeroIncident).get(0);
			
			if (!paramMessage.isBlank()) {
				
				incident.setCol18MessageResolution(paramMessage);
				incident.setCol11Etat("clos");
				
			} else {
				modelAndView.addObject("messageErreurCloture", "Un message de fin est necessaire pour cloturer ce ticket!");
			}
			
			LocalDateTime momentActuel = ticketService.getMomentActuel();
			
			/* on enregistre les modifications dans la BDD */
			incident.setCol18MessageResolution(paramMessage);
			incident.setCol17MomentFin(momentActuel);
			incidentRepository.save(incident);
			modelAndView.addObject("objetTicket", incident);
			
			/* On ajoute une note */
			String messageNote = "[Cloture du ticket] " + paramMessage;
			String utilisateurActuel = ticketService.getUtilisateurActuel();
			IncidentNote incidentNote = new IncidentNote(paramNumeroIncident, momentActuel, utilisateurActuel, messageNote);
			incidentNotesService.ajouterNoteIncident(incidentNote);
			
			/* On affiche toutes les notes du ticket actuel */
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroIncident);
			modelAndView.addObject("listeNotes", listeNotes);
			
			// On repond à l'utilisateur
			modelAndView.addObject("titreTicket", "Ticket " + paramNumeroIncident + " (Incident)");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		}
		
		return modelAndView;
	}
	
	/**
	 * Gère l'ajout d'une note à un incident.
	 */
	@PostMapping("/new-note-incident")
	public ModelAndView ajouterNoteIncident(IncidentNote incidentNote) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		String utilisateurActuel = ticketService.getUtilisateurActuel();
		incidentNote.setCol04Auteur(utilisateurActuel);
		
		String numeroIncident = incidentNote.getCol02NumeroTicket();
		boolean isIncidentPresent = false;
		int idIncident = 0;
		
		/* Vérif si incident présent en BDD */
		try {
			idIncident = incidentRepository.findByCol02NumeroTicket(numeroIncident).get(0).getCol01Id();
			isIncidentPresent = (idIncident > 0) ? true : false;
			
		} catch (Exception ignore) {}
		
		if (isIncidentPresent) {
			/* Si l'entité est présente en BDD */
			
			incidentNotesService.ajouterNoteIncident(incidentNote);
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(numeroIncident);
			modelAndView.addObject("listeNotes", listeNotes);
			
			List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(numeroIncident);
			Incident incidentTrouveh = listeIncidents.get(0);
			modelAndView.addObject("objetTicket", incidentTrouveh);
			modelAndView.addObject("titreTicket", "Ticket " + numeroIncident + " (Incident)");
			
		} else {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + numeroIncident);
		}
		
		return modelAndView;
	}
}
