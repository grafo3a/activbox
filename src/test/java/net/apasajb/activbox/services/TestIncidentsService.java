package net.apasajb.activbox.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestIncidentsService {
	
	IncidentsService incidentsService = new IncidentsService();
	
	@Test
	void testerGenerationNumeroIncident() {
		
		Assertions.assertEquals("IN00000001", incidentsService.genererNumeroIncident(1));
		Assertions.assertEquals("IN00000010", incidentsService.genererNumeroIncident(10));
		Assertions.assertEquals("IN00000100", incidentsService.genererNumeroIncident(100));
		Assertions.assertEquals("IN00001000", incidentsService.genererNumeroIncident(1000));
		Assertions.assertEquals("IN00010000", incidentsService.genererNumeroIncident(10000));
		Assertions.assertEquals("IN00100000", incidentsService.genererNumeroIncident(100000));
		Assertions.assertEquals("IN01000000", incidentsService.genererNumeroIncident(1000000));
		Assertions.assertEquals("IN10000000", incidentsService.genererNumeroIncident(10000000));
		
		Assertions.assertEquals("IN00000009", incidentsService.genererNumeroIncident(9));
		Assertions.assertEquals("IN00000012", incidentsService.genererNumeroIncident(12));
		Assertions.assertEquals("IN00000123", incidentsService.genererNumeroIncident(123));
		Assertions.assertEquals("IN00001234", incidentsService.genererNumeroIncident(1234));
		Assertions.assertEquals("IN00012345", incidentsService.genererNumeroIncident(12345));
		Assertions.assertEquals("IN00123456", incidentsService.genererNumeroIncident(123456));
		Assertions.assertEquals("IN01234567", incidentsService.genererNumeroIncident(1234567));
		Assertions.assertEquals("IN12345678", incidentsService.genererNumeroIncident(12345678));
		
		Assertions.assertEquals("IN00000005", incidentsService.genererNumeroIncident(5));
		Assertions.assertEquals("IN00000078", incidentsService.genererNumeroIncident(78));
		Assertions.assertEquals("IN00000444", incidentsService.genererNumeroIncident(444));
		Assertions.assertEquals("IN00008129", incidentsService.genererNumeroIncident(8129));
		Assertions.assertEquals("IN00038381", incidentsService.genererNumeroIncident(38381));
		Assertions.assertEquals("IN00456123", incidentsService.genererNumeroIncident(456123));
		Assertions.assertEquals("IN01030507", incidentsService.genererNumeroIncident(1030507));
		Assertions.assertEquals("IN91284570", incidentsService.genererNumeroIncident(91284570));
	}
}
