package net.apasajb.activbox.services;

import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.Incident;


/**
 * Comporte des methodes permettant de valider un nouvel incident au moment de la creation de l'incident.
 */
@Component
public class IncidentValidation {
	
	String messageErreurValidation;
	
	public boolean isNewIncidentValid(Incident newIncident) {
		
		boolean isIncidentValid = false;
		
		boolean isPrioritehValid = false;    // choix multiple
		boolean isAgentInitialValid = false;
		boolean isDemandeurValid = false;
		boolean isEntrepriseValid = false;
		boolean isCategorieValid = false;    // choix multiple
		boolean isEquipeEnChargeValid = false;    // choix multiple
		boolean isSujetValid = false;
		boolean isDescriptionValid = false;
		
		/* Validation de la prioriteh */
		try {
			int prioriteh = Integer.parseInt(newIncident.getCol10Prioriteh());
			
			if (prioriteh >= 1 && prioriteh <= 5) {
				isPrioritehValid = true;
				
			} else {
				messageErreurValidation = "ERREUR. Priorité non valide!";
				System.out.println("\nERREUR. Priorité non valide!\n");
			}
			
		} catch (Exception ex) {
			
			messageErreurValidation = "ERREUR. Priorité non valide!";
			System.out.println("\nERREUR. Priorité non valide! (" + ex.getMessage() + ")\n");
			ex.printStackTrace();
		}
		
		/* Validation de l'Agent Initial */
		if (messageErreurValidation == null &&
				newIncident.getCol04AgentInitial().isBlank() == false) {
			isAgentInitialValid = true;
		}
		
		/* Validation du demandeur */
		if (messageErreurValidation == null &&
				newIncident.getCol05Demandeur().isBlank() == false) {
			isDemandeurValid = true;
		}
		
		/* Validation de l'entreprise */
		if (messageErreurValidation == null &&
				newIncident.getCol06Entreprise().isBlank() == false) {
			isEntrepriseValid = true;
		}
		
		/* Validation de la categorie */
		if (messageErreurValidation == null &&
				newIncident.getCol07Categorie().isBlank() == false) {
			isCategorieValid = true;
		}
		
		/* Validation de l'equipe en charge */
		if (messageErreurValidation == null &&
				newIncident.getCol12EquipeEnCharge().isBlank() == false) {
			isEquipeEnChargeValid = true;
		}
		
		/* Validation du sujet */
		if (messageErreurValidation == null &&
				newIncident.getCol15Sujet().isBlank() == false) {
			isSujetValid = true;
		}
		
		/* Validation de la description */
		if (messageErreurValidation == null &&
				newIncident.getCol16Description().isBlank() == false) {
			isDescriptionValid = true;
		}
		
		if (isPrioritehValid && 
				isAgentInitialValid &&
				isDemandeurValid &&
				isEntrepriseValid &&
				isCategorieValid &&
				isEquipeEnChargeValid &&
				isSujetValid &&
				isDescriptionValid) {
			
			isIncidentValid = true;
			
		} else {
			messageErreurValidation = "ERREUR. Incident non valide!";
		}
		
		return isIncidentValid;
	}
}
