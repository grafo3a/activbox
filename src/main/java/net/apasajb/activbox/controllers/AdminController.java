package net.apasajb.activbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controleur pour les activités de type admin.
 */
@Controller
public class AdminController {
	
	@GetMapping("/admin")
	public ModelAndView accederZoneAdmin() {
		
		ModelAndView modelAndView = new ModelAndView("zone-admin.html");
		
		return modelAndView;
	}
}
