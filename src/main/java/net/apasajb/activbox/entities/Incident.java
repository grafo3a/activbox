package net.apasajb.activbox.entities;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Entité qui représente un incident en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="incidents")
public class Incident extends Ticket implements Serializable {
	
	private static final long serialVersionUID = -2201437939013157749L;
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	public Incident() {
		super();
	}
	
	
	
	// ==== LA METHODE TOSTRING ====
	
	
	
	/* ==== GETTERS & SETTERS ====
	 * Uniquement pour les attributs non présents dans la classe parent */
}
