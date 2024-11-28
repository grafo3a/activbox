package net.apasajb.activbox.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.Incident;


/**
 * Comporte des methodes permettant de valider un nouvel incident au moment de la creation de l'incident.
 */
@Component
public class IncidentValidation {
	
	private static final Logger logger = LoggerFactory.getLogger(IncidentValidation.class);
	String messageErreurValidation;
	boolean isIncidentValid = false;
	boolean isPrioritehValid = false;    // choix multiple
	boolean isAgentInitialValid = false;
	boolean isDemandeurValid = false;
	boolean isEntrepriseValid = false;
	boolean isCategorieValid = false;    // choix multiple
	boolean isEquipeEnChargeValid = false;    // choix multiple
	boolean isSujetValid = false;
	boolean isDescriptionValid = false;
	
	public boolean isNewIncidentValid(Incident newIncident) {
		
		String infosIncorrectes = null;
		
		/* Validation de la prioriteh */
		try {
			int prioriteh = Integer.parseInt(newIncident.getCol10Prioriteh());
			
			if (prioriteh >= 1 && prioriteh <= 5) {
				isPrioritehValid = true;
				
			} else {
				infosIncorrectes = (infosIncorrectes == null)? "Priorité" : infosIncorrectes + ", Priorité";
			}
			
		} catch (Exception ex) {
			
			infosIncorrectes = (infosIncorrectes == null)? "Priorité" : infosIncorrectes + ", Priorité";
			ex.printStackTrace();
		}
		
		/* Validation de l'Agent Initial */
		if (newIncident.getCol04AgentInitial().isBlank() == false) {
			isAgentInitialValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Agent initial" : infosIncorrectes + ", Agent initial";
		}
		
		/* Validation du demandeur */
		if (newIncident.getCol05Demandeur().isBlank() == false) {
			isDemandeurValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Demandeur" : infosIncorrectes + ", Demandeur";
		}
		
		/* Validation de l'entreprise */
		if (newIncident.getCol06Entreprise().isBlank() == false) {
			isEntrepriseValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Entreprise" : infosIncorrectes + ", Entreprise";
		}
		
		/* Validation de la categorie */
		if (newIncident.getCol07Categorie().isBlank() == false) {
			isCategorieValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Categorie" : infosIncorrectes + ", Categorie";
		}
		
		/* Validation de l'equipe en charge */
		if (newIncident.getCol12EquipeEnCharge().isBlank() == false) {
			isEquipeEnChargeValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Equipe en charge" : infosIncorrectes + ", Equipe en charge";
		}
		
		/* Validation du sujet */
		if (newIncident.getCol15Sujet().isBlank() == false) {
			isSujetValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Sujet" : infosIncorrectes + ", Sujet";
		}
		
		/* Validation de la description */
		if (newIncident.getCol16Description().isBlank() == false) {
			isDescriptionValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Description" : infosIncorrectes + ", Description";
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
			messageErreurValidation = "Erreur. Infos non valides: " + infosIncorrectes + ".";
			logger.error(messageErreurValidation);
		}
		
		return isIncidentValid;
	}
	
	
	/*==== GETTERS & SETTERS ====*/
	
	public String getMessageErreurValidation() {
		return messageErreurValidation;
	}
	
	public void setMessageErreurValidation(String messageErreurValidation) {
		this.messageErreurValidation = messageErreurValidation;
	}
	
	public boolean isIncidentValid() {
		return isIncidentValid;
	}
	
	public void setIncidentValid(boolean isIncidentValid) {
		this.isIncidentValid = isIncidentValid;
	}
	
	public boolean isPrioritehValid() {
		return isPrioritehValid;
	}
	
	public void setPrioritehValid(boolean isPrioritehValid) {
		this.isPrioritehValid = isPrioritehValid;
	}
	
	public boolean isAgentInitialValid() {
		return isAgentInitialValid;
	}
	
	public void setAgentInitialValid(boolean isAgentInitialValid) {
		this.isAgentInitialValid = isAgentInitialValid;
	}
	
	public boolean isDemandeurValid() {
		return isDemandeurValid;
	}
	
	public void setDemandeurValid(boolean isDemandeurValid) {
		this.isDemandeurValid = isDemandeurValid;
	}
	
	public boolean isEntrepriseValid() {
		return isEntrepriseValid;
	}
	
	public void setEntrepriseValid(boolean isEntrepriseValid) {
		this.isEntrepriseValid = isEntrepriseValid;
	}
	
	public boolean isCategorieValid() {
		return isCategorieValid;
	}
	
	public void setCategorieValid(boolean isCategorieValid) {
		this.isCategorieValid = isCategorieValid;
	}
	
	public boolean isEquipeEnChargeValid() {
		return isEquipeEnChargeValid;
	}
	
	public void setEquipeEnChargeValid(boolean isEquipeEnChargeValid) {
		this.isEquipeEnChargeValid = isEquipeEnChargeValid;
	}
	
	public boolean isSujetValid() {
		return isSujetValid;
	}
	
	public void setSujetValid(boolean isSujetValid) {
		this.isSujetValid = isSujetValid;
	}
	
	public boolean isDescriptionValid() {
		return isDescriptionValid;
	}
	
	public void setDescriptionValid(boolean isDescriptionValid) {
		this.isDescriptionValid = isDescriptionValid;
	}
	/*===============================*/
}
