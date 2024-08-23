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
	
	@GetMapping("/nouvel-incident")
	public ModelAndView afficherFormulaireNouvelIncident() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("nouvel-incident.html");
		modelAndView.addObject("objetIncident", new Incident());
		
		return modelAndView;
	}
	
	@PostMapping("/nouvel-incident")
	public ModelAndView ajouterIncident(Incident newIncident) {
		
		/* D'abord validation */
		IncidentValidation incidentValidation = new IncidentValidation();
		boolean isincidentValid = incidentValidation.isNewIncidentValid(newIncident);
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		if (isincidentValid) {
			/* Si l'incident est valide */
			
			modelAndView.setViewName("details-incident.html");
			
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
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(numeroIncident);
			modelAndView.addObject("listeNotes", listeNotes);
			
			/* On informe l'utilisateur */
			String titreTicket = "Ticket créé: incident " + incidentEnBdd.getCol02NumeroTicket();
			String messageSucces = "Incident créé correctement";
			
			modelAndView.addObject("titreTicket", titreTicket);
			modelAndView.addObject("auteurActuel", "Grafo55");
			modelAndView.addObject("objetIncident", incidentEnBdd);
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
			
			modelAndView.addObject("objetIncident", newIncident);
			modelAndView.addObject("messageErreur", messageErreur);
		}
		
		return modelAndView;
	}
	
	@GetMapping("/affichage-incident-{numero}")
	public ModelAndView afficherIncident(@PathVariable("numero") String paramNumero) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		try {
			List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(paramNumero);
			
			Incident incidentTrouveh = listeIncidents.get(0);
			modelAndView.addObject("objetIncident", incidentTrouveh);
			
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumero);
			modelAndView.addObject("listeNotes", listeNotes);
			
			// A FAIRE EVOLUER
			
			modelAndView.addObject("auteurActuel", "Grafo55");
			modelAndView.addObject("titreTicket", "Ticket Incident " + incidentTrouveh.getCol02NumeroTicket());
			
		} catch (Exception ex) {
			modelAndView.addObject("messageInfo", "Info: Aucun ticket trouvé pour le numéro \"" + paramNumero + "\"");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/recherche-incident")
	public ModelAndView rechercherTicket(
			@RequestParam("motClef") String paramMotClef) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String regexIncident = "^[Ii][Nn][0-9]{8}$";			//=> IN00000000
		String regexDemandeChangement = "^[Dd][Cc][0-9]{8}$";	//=> DC00000000
		String regexDemandeInfo = "^[Dd][Ii][0-9]{8}$";			//=> DI00000000
		String regexDemandeSolution = "^[Dd][Ss][0-9]{8}$";		//=> DS00000000
		String regexTache = "^[Tt][Aa][0-9]{8}$";				//=> TA00000000
		String regexProjet = "^[Pp][Rr][0-9]{8}$";				//=> PR00000000
		
		if (paramMotClef.isBlank()) {
			// Cas d'un mot-clef vide
			
			modelAndView.setViewName("details-incident.html");
			modelAndView.addObject("messageWarning", "Avertissement: Aucun numero ou mot-clef fourni!");
			
		} else if (paramMotClef.matches(regexIncident)) {
			// Cas d'un numero de ticket valide
			
			modelAndView.setViewName("details-incident.html");
			
			try {
				List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(paramMotClef);
				
				
				Incident incidentTrouveh = listeIncidents.get(0);
				modelAndView.addObject("objetIncident", incidentTrouveh);
				
				List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramMotClef);
				modelAndView.addObject("listeNotes", listeNotes);
				/* A FAIRE EVOLUER */
				modelAndView.addObject("auteurActuel", "Grafo55");
				modelAndView.addObject("titreTicket", "Ticket Incident " + incidentTrouveh.getCol02NumeroTicket());
				
			} catch (Exception ex) {
				modelAndView.addObject("messageInfo", "Info: Aucun ticket trouvé pour le numéro \"" + paramMotClef + "\"");
			}
			
			
		/*
		 * ==== AUTRES CAS A ACTIVER SUR BESOIN ====
		
		} else if (paramMotClef.matches(regexDemandeChangement)) {
			// Cas d'une demande
			
		} else if (paramMotClef.matches(regexDemandeInfo)) {
			// Cas d'une demande d'info
			
		} else if (paramMotClef.matches(regexDemandeSolution)) {
			// Cas d'une demande d'une solution durable
			
		} else if (paramMotClef.matches(regexTache)) {
			// Cas d'une tache
			
		} else if (paramMotClef.matches(regexProjet)) {
			// cas d'un projet
		*
		*/
			
		} else {
			// Cas d'un mot-clef quelconque
			
			modelAndView.setViewName("liste-incidents.html");
			String messageInfo;
			List<Incident> listeIncidents = null;
			
			try {
				listeIncidents = incidentRepository.findByCol15SujetContaining(paramMotClef);
				
				if (listeIncidents.isEmpty() == false) {
					modelAndView.addObject("listeIncidents", listeIncidents);
				
				} else {
					messageInfo = "Info: Aucun ticket trouvé pour le mot \"" + paramMotClef + "\"";
					modelAndView.addObject("messageInfo", messageInfo);
				}
				
			} catch (Exception ex) {
				modelAndView.addObject(
						"messageErreur", "Erreur: Impossible d'afficher la liste. Message d'erreur: " + ex.getMessage());
			}
		}
		
		return modelAndView;
	}
	
	
	/* MODIFICATION DE DONNEES D'UN INCIDENT */
	
	@PostMapping("/modif-incident")
	public ModelAndView modifierDonneesIncident(
			@RequestParam("col02NumeroIncident") String paramNumeroIncident,
			@RequestParam("col04Auteur") String paramAuteur,
			@RequestParam("col10Prioriteh") String paramPrioriteh,
			@RequestParam("col11Etat") String paramEtat,
			@RequestParam("col12EquipeEnCharge") String paramEquipeEnCharge,
			@RequestParam("col13AgentEnCharge") String paramAgentEnCharge) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		String messageNote = "Modifications:";
		
		try {
			Incident incident = incidentRepository.findByCol02NumeroTicket(paramNumeroIncident).get(0);
			
			if (!paramPrioriteh.isBlank() && !incident.getCol10Prioriteh().equals(paramPrioriteh)) {
				
				messageNote = messageNote + " Priorité [" + incident.getCol10Prioriteh() + " -> " + paramPrioriteh + "];";
				incident.setCol10Prioriteh(paramPrioriteh);
			}
			
			if (!paramEtat.isBlank() && !incident.getCol11Etat().equals(paramEtat)) {
				
				messageNote = messageNote + " Etat [" + incident.getCol11Etat() + " -> " + paramEtat + "];";
				incident.setCol11Etat(paramEtat);
			}
			
			if (!paramEquipeEnCharge.isBlank() && !incident.getCol12EquipeEnCharge().equals(paramEquipeEnCharge)) {
				
				messageNote = messageNote + " Equipe en Charge [" + incident.getCol12EquipeEnCharge() + " -> " + paramEquipeEnCharge + "];";
				incident.setCol12EquipeEnCharge(paramEquipeEnCharge);
			}
			
			if (!paramAgentEnCharge.isBlank() & incident.getCol13AgentEnCharge() != paramAgentEnCharge) {
				
				messageNote = messageNote + " Agent en charge [" + incident.getCol13AgentEnCharge() + " -> " + paramAgentEnCharge + "];";
				incident.setCol13AgentEnCharge(paramAgentEnCharge);
			}
			
			/* Si au moins une donnée change, on enregistre la modification
			 * et on ajoute une note en BDD.
			 */
			if (!paramPrioriteh.isBlank() || !paramEquipeEnCharge.isBlank() ||
					!paramAgentEnCharge.isBlank() || !paramEtat.isBlank()) {
				
				incidentRepository.save(incident);
				LocalDateTime momentActuel = ticketService.getMomentActuel();
				String utilisateurActuel = ticketService.getUtilisateurActuel();
				
				IncidentNote incidentNote = new IncidentNote(paramNumeroIncident, momentActuel, utilisateurActuel, messageNote);
				incidentNotesService.ajouterNoteIncident(incidentNote);
			}
			
			modelAndView.addObject("objetIncident", incident);
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		}
		
		/* On affiche toutes les notes du ticket actuel */
		List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroIncident);
		modelAndView.addObject("listeNotes", listeNotes);
		
		return modelAndView;
	}
	
	@PostMapping("/cloture-incident")
	public ModelAndView cloreIncident(
			@RequestParam("col02NumeroIncident") String paramNumeroIncident,
			@RequestParam("col04Auteur") String paramAuteur,
			@RequestParam("col05Message") String paramMessage) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		Incident incident = incidentRepository.findByCol02NumeroTicket(paramNumeroIncident).get(0);
		
		if (!paramMessage.isBlank()) {
			
			incident.setCol18MessageResolution(paramMessage);
			incident.setCol11Etat("Clos");
			
		} else {
			modelAndView.addObject("messageErreurCloture", "Un message de fin est necessaire pour cloturer ce ticket!");
		}
		
		LocalDateTime momentActuel = ticketService.getMomentActuel();
		
		/* on enregistre les modifications dans la BDD */
		incident.setCol18MessageResolution(paramMessage);
		incident.setCol17MomentFin(momentActuel);
		incidentRepository.save(incident);
		modelAndView.addObject("objetIncident", incident);
		
		/* On ajoute une note */
		String messageNote = "[Cloture du ticket] " + paramMessage;
		
		String utilisateurActuel = ticketService.getUtilisateurActuel();
		IncidentNote incidentNote = new IncidentNote(paramNumeroIncident, momentActuel, utilisateurActuel, messageNote);
		incidentNotesService.ajouterNoteIncident(incidentNote);
		
		/* On affiche toutes les notes du ticket actuel */
		List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroIncident);
		modelAndView.addObject("listeNotes", listeNotes);
		
		return modelAndView;
	}
	
	/* MANIPULATION DE NOTES */
	
	@PostMapping("/new-note-incident")
	public ModelAndView ajouterNoteIncident(IncidentNote incidentNote) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		String numeroIncident = incidentNote.getCol02NumeroIncident();
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
			modelAndView.addObject("objetIncident", incidentTrouveh);
			modelAndView.addObject("auteurActuel", "Grafo55");
			
		} else {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + numeroIncident);
		}
		
		return modelAndView;
	}
}
