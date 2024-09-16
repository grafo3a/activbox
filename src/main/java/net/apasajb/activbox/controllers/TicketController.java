package net.apasajb.activbox.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.apasajb.activbox.entities.Incident;
import net.apasajb.activbox.repositories.IncidentRepository;
import net.apasajb.activbox.services.IncidentNotesService;
import net.apasajb.activbox.services.TicketService;


@Controller
public class TicketController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	IncidentNotesService incidentNotesService;
	
	String equipeUtilisateur;
	
	/**
	 * Affiche la page d'accueil.
	 */
	@GetMapping("/")
	public ModelAndView commencer() {
		
		ModelAndView modelAndView = new ModelAndView("accueil.html");
		
		/*
		 * A ADAPTER: l'equipe de l'utilisateur actuel.
		 * equipeUtilisateur = "Linux";
		 * List<Incident> listeIncidentsOuvertsEquipeActuelle = incidentRepository.findListeIncidentsEtatOuvert(equipeUtilisateur);
		 */
		
		List<Incident> listeIncidentsOuverts = incidentRepository.findListeIncidentsEtatOuvert();
		
		if (listeIncidentsOuverts.size() > 0) {
			modelAndView.addObject("listeIncidents", listeIncidentsOuverts);
		}
		
		return modelAndView;
	}
	
	/**
	 * Gère la recherche de tickets.
	 */
	@PostMapping("/recherche-incident")
	public ModelAndView rechercherTicket(
			@RequestParam("motClef") String paramMotClef) {
		
		ModelAndView modelAndView = new ModelAndView();
		String regexIncident = ticketService.getRegexIncident();    //=> IN00000000
		
		if (paramMotClef.isBlank()) {
			// Cas d'un mot-clef vide
			
			modelAndView.setViewName("accueil.html");
			modelAndView.addObject("messageWarning", "Avertissement: Aucun numero ou mot-clef fourni!");
			
		} else if (paramMotClef.matches(regexIncident)) {
			// Cas d'un numero de ticket valide
			
			modelAndView.setViewName("details-incident.html");
			
			try {
				List<Incident> listeIncidents = incidentRepository.findByCol02NumeroTicket(paramMotClef);
				
				Incident incidentTrouveh = listeIncidents.get(0);
				modelAndView.addObject("objetTicket", incidentTrouveh);
				
				List<String[]> listeNotes = incidentNotesService.getToutesNotesPourIncident(paramMotClef);
				modelAndView.addObject("listeNotes", listeNotes);
				/* A FAIRE EVOLUER */
				
				modelAndView.addObject("auteurActuel", "Grafo55");
				modelAndView.addObject("titreTicket", "Ticket " + incidentTrouveh.getCol02NumeroTicket() + " (Incident)");
				
			} catch (Exception ex) {
				modelAndView.addObject("messageInfo", "Info: Aucun ticket trouvé pour le numéro \"" + paramMotClef + "\"");
			}
			
			/*
			 * ==== AUTRES CAS A ACTIVER SUR BESOIN ====
			 * Cas d'une demande
			 * Cas d'une demande d'info
			 * Cas d'une demande d'une solution durable
			 * Cas d'une tache
			 * cas d'un projet
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
}
