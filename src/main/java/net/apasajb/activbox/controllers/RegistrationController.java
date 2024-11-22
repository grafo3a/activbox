package net.apasajb.activbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.apasajb.activbox.entities.Compte;
import net.apasajb.activbox.repositories.CompteRepository;


@Controller
public class RegistrationController {
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String tryLogin() {
		return "authentification.html";
	}
	
	/*
	 * La connection d'un utilisateur est geree par Spring.
	 * Le formulaire de login doit pointer sur /login.
	 * L'URL @PostMapping("/login") est geree par Spring.
	 */
	
	@GetMapping("/inscription")
	public String register() {
		return "inscription.html";
	}
	
	@PostMapping("/nouveau-compte")
	@ResponseBody
	public ModelAndView creerCompte(Compte compte) {
		
		ModelAndView modelAndView = new ModelAndView();
		boolean isCompteValid = false;
		String messageSucces;
		String messageErreur;
		
		String paramPassword = compte.getPassword();
		String paramPasswordx2 = compte.getPasswordx2();
		
		if (paramPassword.equals(paramPasswordx2)) {
			// A AMELIORER
			isCompteValid = true;
		}
		
		if (isCompteValid) {
			
			compte.setPassword(passwordEncoder.encode(compte.getPassword()));
			Compte newUser = compteRepository.save(compte);
			messageSucces = "New user added successfully: " + newUser.getUsername() + " (" + newUser.getRole() + ")";
			
			modelAndView.addObject("messageSucces", messageSucces);
			
		} else {
			
			messageErreur = "Password 1 & password 2 are different";
			modelAndView.addObject("messageErreur", messageErreur);
		}
		
		modelAndView.setViewName("inscription.html");
		
		return modelAndView;
	}
}
