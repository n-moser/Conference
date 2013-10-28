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

package com.prodyna.pac.conference.jsf.admin;

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Responsible for admin (CRUD) operations on Speaker entity instances.
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.10.13
 * Time: 09:51
 */
@ManagedBean
@SessionScoped
@RolesAllowed("admin")
@Named("speakerAdminBean")
public class SpeakerAdminBean implements Serializable {

	private Speaker speaker;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private Logger logger;

	/**
	 * Getter for the Speaker.
	 *
	 * @return the speaker
	 */
	public Speaker getSpeaker() {

		if (speaker == null) {
			speaker = new Speaker();
		}
		return speaker;
	}

	/**
	 * Setter for the Speaker.
	 *
	 * @param speaker
	 * 		the speaker to set
	 */
	public void setSpeaker(Speaker speaker) {

		this.speaker = speaker;
	}

	/**
	 * Open the speaker with the given ID for editing.
	 *
	 * @param speakerId
	 * 		the speaker ID
	 *
	 * @return the navigation outcome
	 *
	 * @throws ServiceException
	 * 		when the speaker with the given ID cannot be loaded
	 */
	public String edit(Long speakerId) throws ServiceException {

		if (speakerId != null) {
			Speaker speaker = this.speakerService.findSpeakerById(speakerId);
			this.setSpeaker(speaker);
		} else {

			logger.warn("No Speaker ID submitted!");
			logger.warn("Creating new Speaker Instance!");

			this.setSpeaker(new Speaker());
		}
		return "adminSpeaker?faces-redirect=true";
	}

	/**
	 * Save the current speaker instance.
	 *
	 * @return the navigation outcome
	 *
	 * @throws ServiceException
	 * 		when the speaker cannot be saved
	 */
	public String save() throws ServiceException {

		if (speaker != null) {
			if (speaker.getId() == null) {
				speaker = this.speakerService.createSpeaker(speaker);
			} else {
				speaker = this.speakerService.updateSpeaker(speaker);
			}
		}

		return "admin?faces-redirect=true";
	}

	/**
	 * Remove the current speaker instance.
	 *
	 * @return the navigation outcome
	 *
	 * @throws ServiceException
	 * 		when the speaker cannot be removed
	 */
	public String remove() throws ServiceException {

		if (speaker != null) {
			if (speaker.getId() == null) {
				logger.warn("Cannot remove unpersistent Speaker.");
			} else {
				speaker = this.speakerService.removeSpeaker(speaker);
			}
		}

		return "admin?faces-redirect=true";
	}
}
