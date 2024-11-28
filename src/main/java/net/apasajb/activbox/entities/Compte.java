package net.apasajb.activbox.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;


/**
 * Entiteh representant un compte en BDD.
 */
@Entity
public class Compte {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="username", length=50, nullable=false, unique=true)
	String username;
	
	@Column(name="password", length=50, nullable=false, unique=true)
	String password;
	
	@Transient
	String passwordx2;
	
	@Column(name="role", length=50, nullable=false, unique=false)
	String role;    // Ex. "ADMIN, USER"
	
	// ==== CONSTRUCTEURS PAR DEFAUT & PARAMETRIQUE ====
	public Compte() {
		super();
	}
	
	//==== METHODE TOSTRING ====
	@Override
	public String toString() {
		return "Compte [id=" + id + ", username=" + username + ", password=" + password + ", passwordx2=" + passwordx2
				+ ", role=" + role + "]";
	}
	
	//==== GETTERS & SETTERS ====
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordx2() {
		return passwordx2;
	}
	
	public void setPasswordx2(String passwordx2) {
		this.passwordx2 = passwordx2;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
