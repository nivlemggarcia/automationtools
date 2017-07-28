package com.automationtools.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatService {
	
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String heartbeat() {
		return "I'm Alive!";
	}

}
