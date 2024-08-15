package net.apasajb.activbox.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import net.apasajb.activbox.entities.Incident;
import net.apasajb.activbox.repositories.IncidentRepository;


@Controller
public class TicketController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	String equipeUtilisateur;
	
	@GetMapping("/")
	public ModelAndView commencer() {
		
		ModelAndView modelAndView = new ModelAndView("accueil.html");
		
		/*
		// A ADAPTER: l'equipe de l'utilisateur actuel.
		equipeUtilisateur = "Linux";
		List<Incident> listeIncidentsOuvertsEquipeActuelle = incidentRepository.findListeIncidentsEtatOuvert(equipeUtilisateur);
		*/
		
		List<Incident> listeIncidentsOuverts = incidentRepository.findListeIncidentsEtatOuvert();
		
		if (listeIncidentsOuverts.size() > 0) {
			modelAndView.addObject("listeIncidents", listeIncidentsOuverts);
		}
		
		return modelAndView;
	}
}
