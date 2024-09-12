package net.apasajb.activbox.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


/**
 * Classe parent pour tous les tickets
 * @author ApasaJB
 */
@MappedSuperclass
public class Ticket {
	
	/* ==== VALEURS FIXES ==== */
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer col01Id;
	
	@Column(name="numero", length=20, unique=true)
	private String col02NumeroTicket;
	
	@Column(name="type", length=20)
	private String col03TypeTicket;
	
	@Column(name="agent_initial", length=50, nullable=false)
	private String col04AgentInitial;
	
	@Column(name="demandeur", length=50, nullable=false)
	private String col05Demandeur;
	
	@Column(name="entreprise", length=20, nullable=false)
	private String col06Entreprise;
	
	@Column(name="categorie", length=50, nullable=false)
	private String col07Categorie;
	
	@Column(name="produit", length=20)
	private String col08Produit;
	
	@Column(name="moment_creation", length=30)
	private LocalDateTime col09MomentCreation;
	
	@Column(name="moment_creation_affichage", length=30)
	private String col091MomentCreationPourAffichage;
	
	/* ==== VALEURS VARIABLES ==== */
	
	@Column(name="prioriteh", length=1, nullable=false)
	private String col10Prioriteh;
	
	@Column(name="etat", length=20)
	private String col11Etat;
	
	@Column(name="equipe_en_charge", length=20, nullable=false)
	private String col12EquipeEnCharge;
	
	@Column(name="agent_en_charge", length=50)
	private String col13AgentEnCharge;
	
	@Column(name="moment_sla", length=100)
	private LocalDateTime col14MomentSLA;
	
	@Column(name="sujet", length=100, nullable=false)
	private String col15Sujet;
	
	@Column(name="description", length=1000, nullable=false)
	private String col16Description;
	
	@Column(name="moment_fin", length=100)
	private LocalDateTime col17MomentFin;
	
	@Column(name="message_resolution", length=100)
	private String col18MessageResolution;
	
	/* ==== CONSTRUCTEUR PAR DEFAUT */
	
	public Ticket() {
		super();
	}
	
	/* ==== LA METHODE TOSTRING ====
	 * N'est pas necessaire */
	
	/* ==== GETTERS & SETTERS ==== */
	
	public Integer getCol01Id() {
		return col01Id;
	}
	
	public void setCol01Id(Integer col01Id) {
		this.col01Id = col01Id;
	}
	
	public String getCol02NumeroTicket() {
		return col02NumeroTicket;
	}
	
	public void setCol02NumeroTicket(String col02NumeroTicket) {
		this.col02NumeroTicket = col02NumeroTicket;
	}
	
	public String getCol03TypeTicket() {
		return col03TypeTicket;
	}
	
	public void setCol03TypeTicket(String col03TypeTicket) {
		this.col03TypeTicket = col03TypeTicket;
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
	
	public String getCol06Entreprise() {
		return col06Entreprise;
	}
	
	public void setCol06Entreprise(String col06Entreprise) {
		this.col06Entreprise = col06Entreprise;
	}
	
	public String getCol07Categorie() {
		return col07Categorie;
	}
	
	public void setCol07Categorie(String col07Categorie) {
		this.col07Categorie = col07Categorie;
	}
	
	public String getCol08Produit() {
		return col08Produit;
	}
	
	public void setCol08Produit(String col08Produit) {
		this.col08Produit = col08Produit;
	}
	
	public LocalDateTime getCol09MomentCreation() {
		return col09MomentCreation;
	}
	
	public void setCol09MomentCreation(LocalDateTime col09MomentCreation) {
		this.col09MomentCreation = col09MomentCreation;
	}
	
	public String getCol091MomentCreationPourAffichage() {
		return col091MomentCreationPourAffichage;
	}
	
	public void setCol091MomentCreationPourAffichage(String col091MomentCreationPourAffichage) {
		this.col091MomentCreationPourAffichage = col091MomentCreationPourAffichage;
	}
	
	public String getCol10Prioriteh() {
		return col10Prioriteh;
	}
	
	public void setCol10Prioriteh(String col10Prioriteh) {
		this.col10Prioriteh = col10Prioriteh;
	}
	
	public String getCol11Etat() {
		return col11Etat;
	}
	
	public void setCol11Etat(String col11Etat) {
		this.col11Etat = col11Etat;
	}
	
	public String getCol12EquipeEnCharge() {
		return col12EquipeEnCharge;
	}
	
	public void setCol12EquipeEnCharge(String col12EquipeEnCharge) {
		this.col12EquipeEnCharge = col12EquipeEnCharge;
	}
	
	public String getCol13AgentEnCharge() {
		return col13AgentEnCharge;
	}
	
	public void setCol13AgentEnCharge(String col13AgentEnCharge) {
		this.col13AgentEnCharge = col13AgentEnCharge;
	}
	
	public LocalDateTime getCol14MomentSLA() {
		return col14MomentSLA;
	}
	
	public void setCol14MomentSLA(LocalDateTime col14MomentSLA) {
		this.col14MomentSLA = col14MomentSLA;
	}
	
	public String getCol15Sujet() {
		return col15Sujet;
	}
	
	public void setCol15Sujet(String col15Sujet) {
		this.col15Sujet = col15Sujet;
	}
	
	public String getCol16Description() {
		return col16Description;
	}
	
	public void setCol16Description(String col16Description) {
		this.col16Description = col16Description;
	}
	
	public LocalDateTime getCol17MomentFin() {
		return col17MomentFin;
	}
	
	public void setCol17MomentFin(LocalDateTime col17MomentFin) {
		this.col17MomentFin = col17MomentFin;
	}
	
	public String getCol18MessageResolution() {
		return col18MessageResolution;
	}
	
	public void setCol18MessageResolution(String col18MessageResolution) {
		this.col18MessageResolution = col18MessageResolution;
	}
}
