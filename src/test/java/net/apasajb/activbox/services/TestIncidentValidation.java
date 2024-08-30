package net.apasajb.activbox.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.apasajb.activbox.entities.Incident;


public class TestIncidentValidation {
	
	IncidentValidation incidentValidation = new IncidentValidation();
	Incident newIncident = new Incident();
	
	@Test
	void testerValidationIncident() {
		
		newIncident.setCol01Id(1);
		newIncident.setCol02NumeroTicket("IN00000001");
		newIncident.setCol03TypeTicket("Incident");
		newIncident.setCol04AgentInitial("James");
		newIncident.setCol05Demandeur("Jack");
		newIncident.setCol06Entreprise("ActivBox");
		newIncident.setCol07Categorie("Unix");
		newIncident.setCol08Produit(null);
		newIncident.setCol10Prioriteh("4");
		newIncident.setCol11Etat("Nouveau");
		newIncident.setCol12EquipeEnCharge("Unix");
		newIncident.setCol13AgentEnCharge("Peter");
		newIncident.setCol15Sujet("Mon compte ne s'ouvre plus");
		newIncident.setCol16Description("Mon compte ne s'ouvre plus depuis hier");
		
		/*
		 * Pour etre valide, un ticket doit obligatoirement avoir les infos suivantes:
		 * Prioriteh
		 * Agent Initial
		 * Demandeur
		 * Entreprise
		 * Categorie
		 * Equipe en charge
		 * Sujet
		 * Description
		 * 
		 * Il s'agit des infos qui doivent etre fournies par l'utilisateur qui cree le ticket.
		 * */
		
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol02NumeroTicket(null);
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol04AgentInitial("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol05Demandeur("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol06Entreprise("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol07Categorie("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol10Prioriteh("7");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol12EquipeEnCharge("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol15Sujet("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
		
		newIncident.setCol16Description("");
		Assertions.assertTrue( incidentValidation.isNewIncidentValid(newIncident) );
	}
}
