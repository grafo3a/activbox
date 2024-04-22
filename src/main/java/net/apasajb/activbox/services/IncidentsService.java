package net.apasajb.activbox.services;

import org.springframework.stereotype.Component;


/**
 * Comporte des methodes pour les incidents.
 */
@Component
public class IncidentsService {
	
	public String genererNumeroIncident(int idIncidentEnBdd) {
		
		String numeroIncident = String.valueOf(idIncidentEnBdd);
		int longueurNumero = numeroIncident.length();
		String prefixeNumero;
		
		switch (longueurNumero) {
		case 1 -> prefixeNumero = "IN0000000";
		case 2 -> prefixeNumero = "IN000000";
		case 3 -> prefixeNumero = "IN00000";
		case 4 -> prefixeNumero = "IN0000";
		case 5 -> prefixeNumero = "IN000";
		case 6 -> prefixeNumero = "IN00";
		case 7 -> prefixeNumero = "IN0";
		case 8 -> prefixeNumero = "IN";
		default -> throw new IllegalArgumentException("Unexpected value: " + longueurNumero);
		}
		
		numeroIncident = prefixeNumero + idIncidentEnBdd;
		
		return numeroIncident;
	}
	
	/* Autres methodes */
}
