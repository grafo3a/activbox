package net.apasajb.activbox.services;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Precise les actions qui sont effectuees quand la connexion est acceptee.
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String nextUrl;
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		
		// On garde les infos de la connexion en session
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("authentication", authentication);
		
		// On redirige la requete selon le role de l'utilisateur
		// A AMELIORER
		if (isAdmin) {
			nextUrl = "/admin";
			
		} else {
			nextUrl = "/";
		}
		
		String currentUserName = authentication.getName();
		System.out.println("\n==== User logged in: " + currentUserName);
		
		response.sendRedirect(nextUrl);
	}
}
