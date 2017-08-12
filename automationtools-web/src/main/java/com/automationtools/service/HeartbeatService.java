package com.automationtools.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatService {
	
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> heartbeat() {
		return new ResponseEntity<>("I'm Alive", HttpStatus.OK);
	}

}
