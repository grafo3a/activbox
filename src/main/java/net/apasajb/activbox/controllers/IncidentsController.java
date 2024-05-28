package net.apasajb.activbox.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.apasajb.activbox.entities.Incident;
import net.apasajb.activbox.entities.IncidentNote;
import net.apasajb.activbox.repositories.IncidentRepository;
import net.apasajb.activbox.services.IncidentNotesService;
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
		modelAndView.addObject("incidentAller", new Incident());
		
		return modelAndView;
	}
	
	@PostMapping("/nouvel-incident")
	public ModelAndView ajouterIncident(Incident newIncident) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		/* On ajoute la date & l'heure de création */
		LocalDateTime momentCreation = LocalDateTime.now();
		momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
		newIncident.setCol09MomentCreation(momentCreation);
		
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
		String messageNoteInitial = "Nouvel incident [" + numeroIncident
				+ "] créé par " + auteurNote;
		
		IncidentNote incidentNote = new IncidentNote(numeroIncident, momentCreation, auteurNote, messageNoteInitial);
		incidentNotesService.ajouterNoteIncident(incidentNote);
		List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(numeroIncident);
		modelAndView.addObject("listeNotes", listeNotes);
		
		/* On informe l'utilisateur */
		modelAndView.addObject("auteurActuel", "Grafo55");
		modelAndView.addObject("incidentAller", incidentEnBdd);
		modelAndView.addObject("messageSucces", "Incident créé correctement: " + numeroIncident);
		
		return modelAndView;
	}
	
	/* LES 2 METHODES SUIVANTES CONSTITUENT UNE PAIRE GET & POST */
	
	@GetMapping("/details-incident")
	public ModelAndView afficherFormulaireDetailsIncident() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		return modelAndView;
	}
	
	@PostMapping("/details-incident")
	public ModelAndView rechercher1Incident(
			@RequestParam("numeroTicket") String paramNumeroTicket) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("details-incident.html");
		
		try {
			List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(paramNumeroTicket);
			Incident incidentTrouveh = listeIncidents.get(0);
			modelAndView.addObject("incidentAller", incidentTrouveh);
			
			List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroTicket);
			modelAndView.addObject("listeNotes", listeNotes);
			modelAndView.addObject("auteurActuel", "Grafo55");
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + paramNumeroTicket);
		}
		
		return modelAndView;
	}
	
	/* LES 2 METHODES SUIVANTES CONSTITUENT UNE PAIRE GET & POST */
	
	@GetMapping("/liste-incidents")
	public ModelAndView afficherFormulaireListeIncidents() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("liste-incidents.html");
		
		return modelAndView;
	}
	
	@PostMapping("/liste-incidents")
	public ModelAndView rechercherListeIncidents(
			@RequestParam("motClef") String paramMotClef) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("liste-incidents.html");
		
		try {
			List<Incident> listeIncidents = incidentRepository.findByCol15SujetContaining(paramMotClef);
			modelAndView.addObject("listeIncidents", listeIncidents);
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + paramMotClef);
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
			
			modelAndView.addObject("incidentAller", incident);
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "ERREUR: " + ex.getMessage());
		}
		
		/* On affiche toutes les notes du ticket actuel */
		List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramNumeroIncident);
		modelAndView.addObject("listeNotes", listeNotes);
		
		return modelAndView;
	}
	
	// @PostMapping("/cloture-incident")
	
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
			modelAndView.addObject("incidentAller", incidentTrouveh);
			modelAndView.addObject("auteurActuel", "Grafo55");
			
		} else {
			modelAndView.addObject("messageErreur", "INFO: Aucun ticket trouvé pour " + numeroIncident);
		}
		
		return modelAndView;
	}
}
