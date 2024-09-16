package net.apasajb.activbox.services;

import org.springframework.stereotype.Component;

import net.apasajb.activbox.entities.Change;


/**
 * Comporte des méthodes permettant de valider un nouveau changement au moment de la création du changement.
 */
@Component
public class ChangeValidation {
	
	String messageErreurValidation;
	boolean isChangeValid = false;
	boolean isPrioritehValid = false;    // choix multiple
	boolean isAgentInitialValid = false;
	boolean isDemandeurValid = false;
	boolean isEntrepriseValid = false;
	boolean isCategorieValid = false;    // choix multiple
	boolean isEquipeEnChargeValid = false;    // choix multiple
	boolean isSujetValid = false;
	boolean isDescriptionValid = false;
	
	public boolean isNewChangeValid(Change newChange) {
		
		String infosIncorrectes = null;
		
		/* Validation de la prioriteh */
		try {
			int prioriteh = Integer.parseInt(newChange.getCol10Prioriteh());
			
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
		if (newChange.getCol04AgentInitial().isBlank() == false) {
			isAgentInitialValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Agent initial" : infosIncorrectes + ", Agent initial";
		}
		
		/* Validation du demandeur */
		if (newChange.getCol05Demandeur().isBlank() == false) {
			isDemandeurValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Demandeur" : infosIncorrectes + ", Demandeur";
		}
		
		/* Validation de l'entreprise */
		if (newChange.getCol06Entreprise().isBlank() == false) {
			isEntrepriseValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Entreprise" : infosIncorrectes + ", Entreprise";
		}
		
		/* Validation de la categorie */
		if (newChange.getCol07Categorie().isBlank() == false) {
			isCategorieValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Categorie" : infosIncorrectes + ", Categorie";
		}
		
		/* Validation de l'equipe en charge */
		if (newChange.getCol12EquipeEnCharge().isBlank() == false) {
			isEquipeEnChargeValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Equipe en charge" : infosIncorrectes + ", Equipe en charge";
		}
		
		/* Validation du sujet */
		if (newChange.getCol15Sujet().isBlank() == false) {
			isSujetValid = true;
			
		} else {
			infosIncorrectes = (infosIncorrectes == null)? "Sujet" : infosIncorrectes + ", Sujet";
		}
		
		/* Validation de la description */
		if (newChange.getCol16Description().isBlank() == false) {
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
			
			isChangeValid = true;
			
		} else {
			messageErreurValidation = "Erreur. Infos non valides: " + infosIncorrectes + ".";
			System.out.println("\n" + messageErreurValidation);
		}
		
		return isChangeValid;
	}
	
	
	/*==== GETTERS & SETTERS ====*/
	
	public String getMessageErreurValidation() {
		return messageErreurValidation;
	}
	
	public void setMessageErreurValidation(String messageErreurValidation) {
		this.messageErreurValidation = messageErreurValidation;
	}
	
	public boolean isChangeValid() {
		return isChangeValid;
	}
	
	public void setChangeValid(boolean isChangeValid) {
		this.isChangeValid = isChangeValid;
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
