package net.apasajb.activbox.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
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
		
		/* Tous les comptes d'utilisateurs sont creeh avec le role USER.
		 * Seul le compte admin creeh automatiquement au demarrage de l'appli a le role ADMIN.
		 */
		String roleParDefaut = "USER";
		compte.setRole(roleParDefaut);
		
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
			
			try {
				compte.setPassword(passwordEncoder.encode(compte.getPassword()));
				Compte newUser = compteRepository.save(compte);
				messageSucces = "Nouvel utilisateur créé avec succes: " + newUser.getUsername()
				+ " (avec le role: " + newUser.getRole() + ")";
				logger.info(messageSucces);
				modelAndView.addObject("messageSucces", messageSucces);
				
			} catch (Exception ex) {
				
				messageErreur = "Erreur: quelconque. Message Java: " + ex.getMessage();
				logger.info(messageErreur);
				modelAndView.addObject("messageErreur", messageErreur);
			}
			
		} else {
			
			messageErreur = "Erreur: les 2 mots de passe sont differents!";
			logger.info(messageErreur);
			modelAndView.addObject("messageErreur", messageErreur);
		}
		
		modelAndView.setViewName("inscription.html");
		
		return modelAndView;
	}
}
