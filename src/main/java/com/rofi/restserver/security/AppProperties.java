package com.rofi.restserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class AppProperties {
	@Autowired
	private Environment env; // can call value in the application properties

	public String getTokenSecret() {
		return env.getProperty("tokenSecret");
	}
}
