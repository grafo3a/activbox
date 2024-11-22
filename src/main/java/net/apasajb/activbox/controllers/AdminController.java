package net.apasajb.activbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controleur pour les activités de type admin.
 */
@Controller
public class AdminController {
	
	@GetMapping("/admin")
	@ResponseBody
	public String accederZoneAdmin() {
		return "==== Bonjour les gens. Ici l'adresse /admin ====";
	}
}
