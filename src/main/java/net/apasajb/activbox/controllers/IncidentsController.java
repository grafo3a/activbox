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
import net.apasajb.activbox.repositories.IncidentRepository;
import net.apasajb.activbox.services.IncidentsService;


/**
 * Controleur pour les incidents
 */
@Controller
public class IncidentsController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	@Autowired
	IncidentsService incidentsService;
	
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
		
		LocalDateTime momentCreation = LocalDateTime.now();
		momentCreation = momentCreation.truncatedTo(ChronoUnit.SECONDS);
		newIncident.setCol13MomentCreation(momentCreation);
		
		// On ecrit l'entité en BDD
		Incident incidentEnBdd = incidentRepository.save(newIncident);
		
		/* On met à jour le numero de ticket en BDD */
		int idIncident = incidentEnBdd.getCol01IdIncident();
		String numeroIncident = incidentsService.genererNumeroIncident(idIncident);
		incidentEnBdd.setCol02NumeroIncident(numeroIncident);
		incidentRepository.save(incidentEnBdd);
		
		modelAndView.addObject("incidentAller", incidentEnBdd);
		modelAndView.addObject("messageSucces", "-- Incident créé correctement: " + numeroIncident);
		
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
			List<Incident> listeIncidents = incidentRepository.findByCol02NumeroIncident(paramNumeroTicket);
			Incident incidentTrouveh = listeIncidents.get(0);
			modelAndView.addObject("incidentAller", incidentTrouveh);
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "-- INFO: Aucun ticket trouvé pour " + paramNumeroTicket);
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
			List<Incident> listeIncidents = incidentRepository.findByCol11SujetContaining(paramMotClef);
			
			modelAndView.addObject("listeIncidents", listeIncidents);
			
		} catch (Exception ex) {
			modelAndView.addObject("messageErreur", "-- INFO: Aucun ticket trouvé pour " + paramMotClef);
		}
		
		return modelAndView;
	}
}
