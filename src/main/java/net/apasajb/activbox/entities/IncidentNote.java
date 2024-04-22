package net.apasajb.activbox.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entité qui représente une note d'incident en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="incident_notes")
public class IncidentNote implements Serializable {
	
	private static final long serialVersionUID = -7625636603025445168L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer col01IdNoteIncident;
	
	@Column(name="numero", length=20)
	private String col02NumeroIncident;
	
	@Column(name="moment_creation", length=30)
	private LocalDateTime col03MomentCreation;
	
	@Column(name="auteur", length=50, nullable=false)
	private String col04Auteur;
	
	@Column(name="message", length=1000, nullable=false)
	private String col05Message;
	
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	public IncidentNote() {
		super();
	}
	
	public IncidentNote(String col02NumeroIncident, LocalDateTime col03MomentCreation, String col04Auteur,
			String col05Message) {
		
		super();
		this.col02NumeroIncident = col02NumeroIncident;
		this.col03MomentCreation = col03MomentCreation;
		this.col04Auteur = col04Auteur;
		this.col05Message = col05Message;
	}
	
	// ==== LA METHODE TOSTRING ====
	@Override
	public String toString() {
		return "IncidentEvent [col01IdEventIncident=" + col01IdNoteIncident + ", col02NumeroIncident="
				+ col02NumeroIncident + ", col03MomentCreation=" + col03MomentCreation + ", col04Auteur=" + col04Auteur
				+ ", col05Message=" + col05Message + "]";
	}
	
	// ==== GETTERS & SETTERS ====
	
	public String getCol02NumeroIncident() {
		return col02NumeroIncident;
	}
	
	public void setCol02NumeroIncident(String col02NumeroIncident) {
		this.col02NumeroIncident = col02NumeroIncident;
	}
	
	public LocalDateTime getCol03MomentCreation() {
		return col03MomentCreation;
	}
	
	public void setCol03MomentCreation(LocalDateTime col03MomentCreation) {
		this.col03MomentCreation = col03MomentCreation;
	}
	
	public String getCol04Auteur() {
		return col04Auteur;
	}
	
	public void setCol04Auteur(String col04Auteur) {
		this.col04Auteur = col04Auteur;
	}
	
	public String getCol05Message() {
		return col05Message;
	}
	
	public void setCol05Message(String col05Message) {
		this.col05Message = col05Message;
	}
}
