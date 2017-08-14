package com.automationtools.web.controller;

import java.io.Serializable;

import javax.inject.Named;

import com.automationtools.web.SessionScoped;

@Named("task")
@SessionScoped
public class TaskSimulatorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578060247593536572L;

	public void doSomething() {
		try {
			// simulate a long running request
			Thread.sleep(2000);
		} catch (Exception e) {
			// ignore
		}
	}

}
