package net.apasajb.activbox.entities;

import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entité qui représente un problème en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="problemes")
public class Problem {
	
	@Id
	@Column(name="id_probleme")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idProbleme;
	
	@Column(name="numero_probleme", length=20, nullable=false, unique=true)
	private String numeroProbleme;
	
	
	// Constructeurs par defaut et parametrique
	
	
	// To string
	
	
	//======================================= GETTERS & SETTERS //
	
	public Integer getIdProbleme() {
		return idProbleme;
	}
	
	
	public void setIdProbleme(Integer idProbleme) {
		this.idProbleme = idProbleme;
	}
	
	
	public String getNumeroProbleme() {
		return numeroProbleme;
	}
	
	
	public void setNumeroProbleme(String numeroProbleme) {
		this.numeroProbleme = numeroProbleme;
	}
	//===========================================================//
}
