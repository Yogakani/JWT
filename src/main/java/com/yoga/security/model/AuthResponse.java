/**
 * 
 */
package com.yoga.security.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Yoga
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4079394225531100684L;
	
	private String result;
	
	private String token;

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
