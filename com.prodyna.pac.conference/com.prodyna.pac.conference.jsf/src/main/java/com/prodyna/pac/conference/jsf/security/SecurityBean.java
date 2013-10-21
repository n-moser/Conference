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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

	/**
	 * Login the user with given username and password.
	 *
	 * @return the login direction
	 */
	public String login() {

		if (!this.isLoggedIn()) {

			if (this.userName != null && !this.userName.isEmpty()) {

				logger.info("Authenticating User {}.", this.userName);

				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

				try {
					request.login(this.userName, this.password);

					logger.info("Authentication of User {} succeeded.", this.userName);
					this.loggedIn = true;

				} catch (ServletException e) {
					logger.warn("Authentication of User {} failed.", this.userName);
					context.addMessage("loginForm",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or Password invalid.",
									"Please try again."));
				}
			}
		}

		return null;
	}

	/**
	 * Logout the user from the current session.
	 *
	 * @return the welcome page navigation
	 */
	public String logout() {

		this.loggedIn = false;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();

		this.userName = null;
		this.password = null;

		return "welcome?faces-redirect=true";
	}
}
