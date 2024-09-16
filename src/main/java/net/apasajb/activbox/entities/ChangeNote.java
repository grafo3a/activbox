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
 * Entité qui représente une note de changement en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="change_notes")
public class ChangeNote implements Serializable {
	
	private static final long serialVersionUID = 4919243768827341366L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer col01IdNote;
	
	@Column(name="numero_ticket", length=20, nullable=false)
	private String col02NumeroTicket;
	
	@Column(name="moment_creation", length=30)
	private LocalDateTime col03MomentCreation;
	
	@Column(name="moment_creation_affichage", length=30)
	private String col031MomentCreationPourAffichage;
	
	@Column(name="auteur", length=50, nullable=false)
	private String col04Auteur;
	
	@Column(name="message", length=1000, nullable=false)
	private String col05Message;
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	
	public ChangeNote() {
		super();
	}
	
	public ChangeNote(String col02NumeroTicket, LocalDateTime col03MomentCreation, String col04Auteur,
			String col05Message) {
		super();
		this.col02NumeroTicket = col02NumeroTicket;
		this.col03MomentCreation = col03MomentCreation;
		this.col04Auteur = col04Auteur;
		this.col05Message = col05Message;
	}
	
	// ==== LA METHODE TOSTRING ====
	
	@Override
	public String toString() {
		return "ChangeNote [col01IdNote=" + col01IdNote + ", col02NumeroTicket=" + col02NumeroTicket
				+ ", col03MomentCreation=" + col03MomentCreation + ", col031MomentCreationPourAffichage="
				+ col031MomentCreationPourAffichage + ", col04Auteur=" + col04Auteur + ", col05Message=" + col05Message
				+ "]";
	}
	
	// ==== GETTERS & SETTERS ====
	
	public String getCol02NumeroTicket() {
		return col02NumeroTicket;
	}
	
	public void setCol02NumeroTicket(String col02NumeroTicket) {
		this.col02NumeroTicket = col02NumeroTicket;
	}
	
	public LocalDateTime getCol03MomentCreation() {
		return col03MomentCreation;
	}
	
	public void setCol03MomentCreation(LocalDateTime col03MomentCreation) {
		this.col03MomentCreation = col03MomentCreation;
	}
	
	public String getCol031MomentCreationPourAffichage() {
		return col031MomentCreationPourAffichage;
	}
	
	public void setCol031MomentCreationPourAffichage(String col031MomentCreationPourAffichage) {
		this.col031MomentCreationPourAffichage = col031MomentCreationPourAffichage;
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
