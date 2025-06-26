package net.apasajb.activbox.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


/**
 * Gère les erreurs http.
 */
@Controller
public class CustomErrorController implements ErrorController {
	
	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			
			// Traitement du code d'etat 404
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error/404";    // Le template error/404.html
				
			// Traitement du code d'etat 403
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error/403";    // Le template error/403.html
			}
		}
		
		return "error/default";
	}
}
