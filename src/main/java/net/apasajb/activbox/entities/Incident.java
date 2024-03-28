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
	@Column(name="id_incident")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer col01IdIncident;
	
	@Column(name="numero_incident", length=20, nullable=false, unique=true)
	private String col02NumeroIncident;
	
	@Column(name="agent_initial", length=50, nullable=false)
	private String col03AgentInitial;
	
	@Column(name="demandeur", length=50, nullable=false)
	private String col04Demandeur;
	
	@Column(name="etat", length=20, nullable=false)
	private String col05Etat;
	
	@Column(name="categorie", length=50, nullable=false)
	private String col06Categorie;
	
	@Column(name="equipe_en_charge", length=20, nullable=false)
	private String col07EquipeEnCharge;
	
	@Column(name="agent_en_charge", length=50, nullable=false)
	private String col08AgentEnCharge;
	
	@Column(name="produit", length=20, nullable=false)
	private String col09Produit;
	
	@Column(name="sujet", length=100, nullable=false)
	private String col10Sujet;
	
	@Column(name="description", length=1000, nullable=false)
	private String col11Description;
	
	@Column(name="moment_creation", length=100, nullable=false)
	private LocalDateTime col12MomentCreation;
	
	@Column(name="moment_sla", length=100, nullable=false)
	private LocalDateTime col13MomentSLA;
	
	@Column(name="moment_fin", length=100, nullable=false)
	private LocalDateTime col14MomentFin;
	
	@Column(name="notes", length=100, nullable=false)
	private String col15Notes;
	
	@Column(name="message_resolution", length=100, nullable=false)
	private String col16MessageResolution;
	
	
	/* Constructeurs par defaut et parametrique */
	public Incident() {
		super();
	}
	
	public Incident(String col03AgentInitial, String col04Demandeur, String col06Categorie, String col09Produit,
			String col10Sujet, String col11Description) {
		
		super();
		this.col03AgentInitial = col03AgentInitial;
		this.col04Demandeur = col04Demandeur;
		this.col06Categorie = col06Categorie;
		this.col09Produit = col09Produit;
		this.col10Sujet = col10Sujet;
		this.col11Description = col11Description;
	}
	
	@Override
	public String toString() {
		
		return "Incident [col01IdIncident=" + col01IdIncident + ", col02NumeroIncident=" + col02NumeroIncident
				+ ", col03AgentInitial=" + col03AgentInitial + ", col04Demandeur=" + col04Demandeur + ", col05Etat="
				+ col05Etat + ", col06Categorie=" + col06Categorie + ", col07EquipeEnCharge=" + col07EquipeEnCharge
				+ ", col08AgentEnCharge=" + col08AgentEnCharge + ", col09Produit=" + col09Produit + ", col10Sujet="
				+ col10Sujet + ", col11Description=" + col11Description + ", col12MomentCreation=" + col12MomentCreation
				+ ", col13MomentSLA=" + col13MomentSLA + ", col14MomentFin=" + col14MomentFin + ", col15Notes="
				+ col15Notes + ", col16MessageResolution=" + col16MessageResolution + "]";
	}
	
	
	//======================================= GETTERS & SETTERS //
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
	
	public String getCol03AgentInitial() {
		return col03AgentInitial;
	}
	
	public void setCol03AgentInitial(String col03AgentInitial) {
		this.col03AgentInitial = col03AgentInitial;
	}
	
	public String getCol04Demandeur() {
		return col04Demandeur;
	}
	
	public void setCol04Demandeur(String col04Demandeur) {
		this.col04Demandeur = col04Demandeur;
	}
	
	public String getCol05Etat() {
		return col05Etat;
	}
	
	public void setCol05Etat(String col05Etat) {
		this.col05Etat = col05Etat;
	}
	
	public String getCol06Categorie() {
		return col06Categorie;
	}
	
	public void setCol06Categorie(String col06Categorie) {
		this.col06Categorie = col06Categorie;
	}
	
	public String getCol07EquipeEnCharge() {
		return col07EquipeEnCharge;
	}
	
	public void setCol07EquipeEnCharge(String col07EquipeEnCharge) {
		this.col07EquipeEnCharge = col07EquipeEnCharge;
	}
	
	public String getCol08AgentEnCharge() {
		return col08AgentEnCharge;
	}
	
	public void setCol08AgentEnCharge(String col08AgentEnCharge) {
		this.col08AgentEnCharge = col08AgentEnCharge;
	}
	
	public String getCol09Produit() {
		return col09Produit;
	}
	
	public void setCol09Produit(String col09Produit) {
		this.col09Produit = col09Produit;
	}
	
	public String getCol10Sujet() {
		return col10Sujet;
	}
	
	public void setCol10Sujet(String col10Sujet) {
		this.col10Sujet = col10Sujet;
	}
	
	public String getCol11Description() {
		return col11Description;
	}
	
	public void setCol11Description(String col11Description) {
		this.col11Description = col11Description;
	}
	
	public LocalDateTime getCol12MomentCreation() {
		return col12MomentCreation;
	}
	
	public void setCol12MomentCreation(LocalDateTime col12MomentCreation) {
		this.col12MomentCreation = col12MomentCreation;
	}
	
	public LocalDateTime getCol13MomentSLA() {
		return col13MomentSLA;
	}
	
	public void setCol13MomentSLA(LocalDateTime col13MomentSLA) {
		this.col13MomentSLA = col13MomentSLA;
	}
	
	public LocalDateTime getCol14MomentFin() {
		return col14MomentFin;
	}
	
	public void setCol14MomentFin(LocalDateTime col14MomentFin) {
		this.col14MomentFin = col14MomentFin;
	}
	
	public String getCol15Notes() {
		return col15Notes;
	}
	
	public void setCol15Notes(String col15Notes) {
		this.col15Notes = col15Notes;
	}
	
	public String getCol16MessageResolution() {
		return col16MessageResolution;
	}
	
	public void setCol16MessageResolution(String col16MessageResolution) {
		this.col16MessageResolution = col16MessageResolution;
	}
	//===========================================================//
}
