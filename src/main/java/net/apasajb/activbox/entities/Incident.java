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
 * Entité qui représente un incident en BDD
 * @author ApasaJB
 */
@Component
@Entity
@Table(name="incidents")
public class Incident implements Serializable {
	
	private static final long serialVersionUID = -2201437939013157749L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer col01IdIncident;
	
	@Column(name="numero", length=20, unique=true)
	private String col02NumeroIncident;
	
	@Column(name="prioriteh", length=1, nullable=false)
	private String col03Prioriteh;
	
	@Column(name="agent_initial", length=50, nullable=false)
	private String col04AgentInitial;
	
	@Column(name="demandeur", length=50, nullable=false)
	private String col05Demandeur;
	
	@Column(name="etat", length=20, nullable=false)
	private String col06Etat;
	
	@Column(name="categorie", length=50, nullable=false)
	private String col07Categorie;
	
	@Column(name="equipe_en_charge", length=20, nullable=false)
	private String col08EquipeEnCharge;
	
	@Column(name="agent_en_charge", length=50)
	private String col09AgentEnCharge;
	
	@Column(name="nom_produit", length=20)
	private String col10NomProduit;
	
	@Column(name="sujet", length=100, nullable=false)
	private String col11Sujet;
	
	@Column(name="description", length=1000, nullable=false)
	private String col12Description;
	
	@Column(name="moment_creation", length=30)
	private LocalDateTime col13MomentCreation;
	
	@Column(name="moment_sla", length=100)
	private LocalDateTime col14MomentSLA;
	
	@Column(name="moment_fin", length=100)
	private LocalDateTime col15MomentFin;
	
	@Column(name="message_resolution", length=100)
	private String col16MessageResolution;
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	public Incident() {
		super();
	}
	
	public Incident(
			String col03Prioriteh,
			String col04AgentInitial, String col05Demandeur, String col06Etat, String col07Categorie,
			String col08EquipeEnCharge, String col11Sujet, String col12Description) {
		
		super();
		this.col01IdIncident = null;
		//this.col02NumeroIncident = col02NumeroIncident;
		this.col03Prioriteh = col03Prioriteh;
		this.col04AgentInitial = col04AgentInitial;
		this.col05Demandeur = col05Demandeur;
		this.col06Etat = col06Etat;
		this.col07Categorie = col07Categorie;
		this.col08EquipeEnCharge = col08EquipeEnCharge;
		this.col11Sujet = col11Sujet;
		this.col12Description = col12Description;
		this.col13MomentCreation = null;
	}
	
	// ==== LA METHODE TOSTRING ====
	@Override
	public String toString() {
		return "Incident [col01IdIncident=" + col01IdIncident + ", col02NumeroIncident=" + col02NumeroIncident
				+ ", col03Prioriteh=" + col03Prioriteh + ", col04AgentInitial=" + col04AgentInitial
				+ ", col05Demandeur=" + col05Demandeur + ", col06Etat=" + col06Etat + ", col07Categorie="
				+ col07Categorie + ", col08EquipeEnCharge=" + col08EquipeEnCharge + ", col09AgentEnCharge="
				+ col09AgentEnCharge + ", col10NomProduit=" + col10NomProduit + ", col11Sujet=" + col11Sujet
				+ ", col12Description=" + col12Description + ", col13MomentCreation=" + col13MomentCreation
				+ ", col14MomentSLA=" + col14MomentSLA + ", col15MomentFin=" + col15MomentFin
				+ ", col16MessageResolution=" + col16MessageResolution + "]";
	}
	
	// ==== GETTERS & SETTERS ====
	public Integer getCol01IdIncident() {
		return col01IdIncident;
	}
	
	public void setCol01IdIncident(Integer col01IdIncident) {
		this.col01IdIncident = col01IdIncident;
	}
	
	public String getCol02NumeroIncident() {
		return col02NumeroIncident;
	}
	
	public void setCol02NumeroIncident(String col02NumeroIncident) {
		this.col02NumeroIncident = col02NumeroIncident;
	}
	
	public String getCol03Prioriteh() {
		return col03Prioriteh;
	}
	
	public void setCol03Prioriteh(String col03Prioriteh) {
		this.col03Prioriteh = col03Prioriteh;
	}
	
	public String getCol04AgentInitial() {
		return col04AgentInitial;
	}
	
	public void setCol04AgentInitial(String col04AgentInitial) {
		this.col04AgentInitial = col04AgentInitial;
	}
	
	public String getCol05Demandeur() {
		return col05Demandeur;
	}
	
	public void setCol05Demandeur(String col05Demandeur) {
		this.col05Demandeur = col05Demandeur;
	}
	
	public String getCol06Etat() {
		return col06Etat;
	}
	
	public void setCol06Etat(String col06Etat) {
		this.col06Etat = col06Etat;
	}
	
	public String getCol07Categorie() {
		return col07Categorie;
	}
	
	public void setCol07Categorie(String col07Categorie) {
		this.col07Categorie = col07Categorie;
	}
	
	public String getCol08EquipeEnCharge() {
		return col08EquipeEnCharge;
	}
	
	public void setCol08EquipeEnCharge(String col08EquipeEnCharge) {
		this.col08EquipeEnCharge = col08EquipeEnCharge;
	}
	
	public String getCol09AgentEnCharge() {
		return col09AgentEnCharge;
	}
	
	public void setCol09AgentEnCharge(String col09AgentEnCharge) {
		this.col09AgentEnCharge = col09AgentEnCharge;
	}
	
	public String getCol10NomProduit() {
		return col10NomProduit;
	}
	
	public void setCol10NomProduit(String col10NomProduit) {
		this.col10NomProduit = col10NomProduit;
	}
	
	public String getCol11Sujet() {
		return col11Sujet;
	}
	
	public void setCol11Sujet(String col11Sujet) {
		this.col11Sujet = col11Sujet;
	}
	
	public String getCol12Description() {
		return col12Description;
	}
	
	public void setCol12Description(String col12Description) {
		this.col12Description = col12Description;
	}
	
	public LocalDateTime getCol13MomentCreation() {
		return col13MomentCreation;
	}
	
	public void setCol13MomentCreation(LocalDateTime col13MomentCreation) {
		this.col13MomentCreation = col13MomentCreation;
	}
	
	public LocalDateTime getCol14MomentSLA() {
		return col14MomentSLA;
	}
	
	public void setCol14MomentSLA(LocalDateTime col14MomentSLA) {
		this.col14MomentSLA = col14MomentSLA;
	}
	
	public LocalDateTime getCol15MomentFin() {
		return col15MomentFin;
	}
	
	public void setCol15MomentFin(LocalDateTime col15MomentFin) {
		this.col15MomentFin = col15MomentFin;
	}
	
	public String getCol16MessageResolution() {
		return col16MessageResolution;
	}
	
	public void setCol16MessageResolution(String col16MessageResolution) {
		this.col16MessageResolution = col16MessageResolution;
	}
}
