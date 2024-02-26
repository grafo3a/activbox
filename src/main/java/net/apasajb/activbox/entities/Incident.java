package net.apasajb.activbox.entities;

import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entité qui représente un incident en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="incidents")
public class Incident {
	
	@Id
	@Column(name="id_incident")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idIncident;
	
	@Column(name="numero_incident", length=20, nullable=false, unique=true)
	private String numeroIncident;
	
	
	// Constructeurs par defaut et parametrique
	
	
	// To string
	
	
	//======================================= GETTERS & SETTERS //
	
	public Integer getIdIncident() {
		return idIncident;
	}
	
	
	public void setIdIncident(Integer idIncident) {
		this.idIncident = idIncident;
	}
	
	
	public String getNumeroIncident() {
		return numeroIncident;
	}
	
	
	public void setNumeroIncident(String numeroIncident) {
		this.numeroIncident = numeroIncident;
	}
	//===========================================================//
}
