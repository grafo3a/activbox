package net.apasajb.activbox.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.apasajb.activbox.entities.Compte;
import net.apasajb.activbox.repositories.CompteRepository;


@Service
public class CompteDetailsService implements UserDetailsService {
	
	@Autowired
	CompteRepository compteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Compte> compte = compteRepository.findByUsername(username);
		
		if (compte.isPresent()) {
			
			var objetCompte = compte.get();
			
			return User.builder()
					.username(objetCompte.getUsername())
					.password(objetCompte.getPassword())
					.roles(getRoles(objetCompte))
					.build();
			
		} else {
			throw new UsernameNotFoundException(username);
		}
	}
	
	
	private String[] getRoles(Compte compte) {
		
		// On definit le role par defaut
		if (compte.getRole() == null) {
			return new String[] {"USER"};
		}
		
		return compte.getRole().split(",");
	}
}
