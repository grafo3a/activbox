package net.apasajb.activbox.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.apasajb.activbox.entities.Incident;
import net.apasajb.activbox.repositories.IncidentRepository;


/**
 * Controleur pour les incidents
 */
@Controller
public class IncidentsController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	/* LES 2 METHODES SUIVANTES CONSTITUENT UNE PAIRE
	 * ------------------------------------------------ */
	
	@GetMapping("/nouvel-incident")
	public String initIncidentForm(Model model) {
		
		model.addAttribute("incidentAller", new Incident());
		
		return "nouvel-incident.html";
	}
	
	// Ici les donnees proviennent d'un formulaire qui provient aussi de la methode get ci-dessus.
	@PostMapping("/nouvel-incident")
	@ResponseBody    // Ici on retourne des donnees brutes (au lieu d'un template)
	public String ajouterIncident(Model model, @ModelAttribute("incidentRetour") Incident newIncident) {
		
		// On ecrit l'entiteh en BDD
		incidentRepository.save(newIncident);
		System.out.println("\nAjout nouvel incident OK");
		
		List<Incident> listeIncidents = new ArrayList<Incident>();
		listeIncidents = incidentRepository.findAll();
		
		listeIncidents.forEach((p) -> System.out.println(
				"\n---------------------------------------------"
				+ "\n-- Prioriteh: " + p.getCol03Prioriteh()
				+ "\n-- Agent: " + p.getCol04AgentInitial()
				+ "\n-- Demandeur: " + p.getCol05Demandeur()
				+ "\n-- Etat: " + p.getCol06Etat()
				+ "\n-- Categorie: " + p.getCol07Categorie()
				+ "\n-- Equipe en charge: " + p.getCol08EquipeEnCharge()
				+ "\n-- Agent en charge: " + p.getCol09AgentEnCharge()
				+ "\n-- Nom produit: " + p.getCol10NomProduit()
				+ "\n-- Sujet: " + p.getCol11Sujet()
				+ "\n-- Description: " + p.getCol12Description())
		);
		System.out.println("---------------------------------------------\n");
		
		return "Super, tout s'est bien passé.";
	}
}
