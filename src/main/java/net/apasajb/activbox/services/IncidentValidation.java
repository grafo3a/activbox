package net.apasajb.activbox.services;

import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.Incident;


/**
 * Comporte des methodes permettant de valider un nouvel incident au moment de la creation de l'incident.
 */
@Component
public class IncidentValidation {
	
	public boolean isNewIncidentValid(Incident newIncident) {
		
		boolean isIncidentValid = false;
		
		/*
		boolean isPrioritehValid = false;    // choix multiple
		boolean isAgentInitialValid = false;
		boolean isDemandeurValid = false;
		boolean isEntrepriseValid = false;
		boolean isCategorieValid = false;    // choix multiple
		boolean isEquipeEnChargeValid = false;    // choix multiple
		boolean isSujetValid = false;
		boolean isDescriptionValid = false;
		
		try {
			int prioriteh = Integer.parseInt(newIncident.getCol10Prioriteh());
			
			if (prioriteh >= 1 && prioriteh <= 5) {
				isPrioritehValid = true;
				
			} else {
				System.out.println("\nERREUR. Priorité non valide!\n");
			}
			
		} catch (Exception ex) {
			
			System.out.println("\nERREUR. Priorité non valide! (" + ex.getMessage() + ")\n");
			ex.printStackTrace();
		}
		*/
		
		return isIncidentValid;
	}
}
