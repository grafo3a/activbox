package net.apasajb.activbox.entities;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Entité qui représente un changement en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="changements")
public class Change extends Ticket implements Serializable {

	private static final long serialVersionUID = 892244685273266123L;
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	public Change() {
		super();
	}
	
	
	/* ==== LA METHODE TOSTRING ====
	 * pas necessaire ici.
	 */
	
	
	/* ==== GETTERS & SETTERS ====
	 * Uniquement pour les attributs non présents dans la classe parent */
}
