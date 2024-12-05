package net.apasajb.activbox.listeners;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.apasajb.activbox.config.AdminZoneConfig;
import net.apasajb.activbox.entities.Compte;
import net.apasajb.activbox.repositories.CompteRepository;


/**
 * Offre des methodes permettant d'executer un code quand l'appli demarre ou<br/>
 * quand un autre evenement applicatif se produit.
 */
@Component
public class AppStartupListenerBean {
	
	@Autowired
	AdminZoneConfig adminZoneConfig;
	
	@Autowired
	CompteRepository compteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ConfigurableApplicationContext confAppContext;
	
	private static final Logger logger = LoggerFactory.getLogger(AppStartupListenerBean.class);
	Compte compteAdminEnBDD;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		try {
			Compte nouveauCompteAdmin = this.initialiserCompteAdmin();
			logger.info("Fin initialisation du compte: " + nouveauCompteAdmin.getUsername());
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			logger.error("**** ERREUR: Echec de creation du compte initial admin. Message Java: " + ex.getMessage() + ".");
			
			confAppContext.close();
			logger.info("Tous les objets JPA & Spring sont maintenant arretés, l'application est arretée.");
			logger.info("On arrete l'instance JVM actuelle.");
			System.exit(0);
		}
	}
	
	private Compte initialiserCompteAdmin() {
		
		String adminUsername = "admin";
		String adminPassword = adminZoneConfig.getAdminPassword();
		String adminRole = "ADMIN";
		adminPassword = passwordEncoder.encode(adminPassword);
		
		Optional<Compte> compteEnBDD = compteRepository.findByUsername("admin");
		compteAdminEnBDD = compteEnBDD.orElse(null);
		
		if (compteAdminEnBDD == null) {
			// Le compte admin est absent en BDD, on le cree
			
			logger.info("Le compte admin est absent en BDD, on le crée.");
			compteAdminEnBDD = new Compte();
			compteAdminEnBDD.setPassword(adminPassword);
			compteAdminEnBDD.setUsername(adminUsername);
			compteAdminEnBDD.setRole(adminRole);
			compteAdminEnBDD = compteRepository.save(compteAdminEnBDD);
			logger.info("Compte créé avec succès: " + compteAdminEnBDD.getUsername());
			
		} else {
			// Le compte admin est deja present en BDD, on met à jour le mot de passe seulement
			
			logger.info("Le compte admin est deja present en BDD, on met à jour le mot de passe seulement.");
			compteAdminEnBDD = compteRepository.save(compteAdminEnBDD);
			compteAdminEnBDD.setPassword(adminPassword);
			logger.info("Mot de passe mis à jour avec succès pour le compte: " + compteAdminEnBDD.getUsername());
		}
		
		return compteAdminEnBDD;
	}
}
