package com.automationtools.services;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automationtools.core.Data;
import com.automationtools.template.Key;

/**
 * 
 * @author 	Melvin Garcia
 * @since	1.0.0
 */
@RestController
public class ExecuteScriptService {
	
	public String execute(@RequestBody Key ... key) {
		return null;
	}
	
	public String execute(@RequestBody Data ... data) {
		return null;
	}
	
	public String execute(@RequestParam("charset") String charset, @RequestBody byte[] raw) {
		return null;
	}

}
