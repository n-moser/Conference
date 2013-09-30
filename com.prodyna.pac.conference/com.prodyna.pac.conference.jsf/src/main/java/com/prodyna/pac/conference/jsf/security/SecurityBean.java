/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Nicolas Moser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.prodyna.pac.conference.jsf.security;

import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * SecurityBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 30.09.13
 * Time: 22:30
 */
@ManagedBean
@SessionScoped
@Named("securityBean")
public class SecurityBean implements Serializable {

	@Inject
	private Logger logger;

	private boolean loggedIn;

	private String userName;

	private String password;

	private static final String ADMIN = "admin";

	public boolean isLoggedIn() {

		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {

		this.loggedIn = loggedIn;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String login() {

		logger.info("Authenticating User {}.", this.userName);

		if (ADMIN.equals(this.userName) && ADMIN.equals(this.password)) {
			logger.info("Authentication of User {} succeeded.", this.userName);
			this.loggedIn = true;
		} else {
			logger.info("Authentication of User {} failed.", this.userName);
		}

		return "administration";
	}

	public void logout() {
		this.loggedIn = false;

		this.userName = null;
		this.password = null;
	}
}
