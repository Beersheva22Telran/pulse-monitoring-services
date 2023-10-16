package telran.monitoring.controller;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entities.*;
import telran.monitoring.repo.*;
import telran.monitoring.service.*;
@RequiredArgsConstructor
@RestController

public class EmailDataProviderController {
	final EmailDataProviderService service;
	@GetMapping("data/{patientId}")
	ResponseEntity<?> getData(@PathVariable long patientId) {
		ResponseEntity<?> res = null;
		try {
			EmailNotificationData data = service.getEmailData(patientId);
			res =  new ResponseEntity<EmailNotificationData>(data, HttpStatus.OK);
		} catch (Exception e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return res;
		
		
	}
}
