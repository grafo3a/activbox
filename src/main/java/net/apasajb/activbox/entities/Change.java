package net.apasajb.activbox.entities;

import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entité qui représente un changement en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="changements")
public class Change {
	
	@Id
	@Column(name="id_changement")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idChangement;
	
	@Column(name="numero_changement", length=20, nullable=false, unique=true)
	private String numeroChangement;
	
	
	
	// Constructeurs par defaut et parametrique
	
	
	// To string
	
	
	//======================================= GETTERS & SETTERS //
	
	public Integer getIdChangement() {
		return idChangement;
	}
	
	
	public void setIdChangement(Integer idChangement) {
		this.idChangement = idChangement;
	}
	
	
	public String getNumeroChangement() {
		return numeroChangement;
	}
	
	
	public void setNumeroChangement(String numeroChangement) {
		this.numeroChangement = numeroChangement;
	}
	//===========================================================//
}
