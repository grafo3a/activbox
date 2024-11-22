package net.apasajb.activbox.services;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	// On passe dans cette methode quand il y a erreur de login
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		request.setAttribute("error", "true");
		request.getRequestDispatcher("/login").forward(request, response);
	}
}
