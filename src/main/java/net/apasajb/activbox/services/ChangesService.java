package net.apasajb.activbox.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


/**
 * Comporte des methodes pour les changements.
 */
@Service
public class ChangesService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	public String genererNumeroChangement(int idChangementEnBdd) {
		
		String numeroChangement = String.valueOf(idChangementEnBdd);
		int longueurNumero = numeroChangement.length();
		String prefixeNumero;
		
		switch (longueurNumero) {
		case 1 -> prefixeNumero = "CH0000000";
		case 2 -> prefixeNumero = "CH000000";
		case 3 -> prefixeNumero = "CH00000";
		case 4 -> prefixeNumero = "CH0000";
		case 5 -> prefixeNumero = "CH000";
		case 6 -> prefixeNumero = "CH00";
		case 7 -> prefixeNumero = "CH0";
		case 8 -> prefixeNumero = "CH";
		default -> throw new IllegalArgumentException("Unexpected value: " + longueurNumero);
		}
		
		numeroChangement = prefixeNumero + idChangementEnBdd;
		
		return numeroChangement;
	}
	
	/* Autres methodes */
}
