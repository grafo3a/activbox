package net.apasajb.activbox.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.apasajb.activbox.entities.Compte;


public interface CompteRepository extends JpaRepository<Compte, Long> {
	
	Optional<Compte> findByUsername(String username);
}
