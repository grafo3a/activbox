package net.apasajb.activbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.apasajb.activbox.repositories.IncidentRepository;


@Controller
public class TicketController {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	@GetMapping("/")
	public String commencer() {
		return "accueil.html";
	}
}
