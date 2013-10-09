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

package com.prodyna.pac.conference.jsf;

import com.prodyna.pac.conference.ejb.facade.datatype.Speaker;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.speaker.SpeakerService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * SpeakerBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.10.13
 * Time: 09:51
 */
@ManagedBean
@SessionScoped
@Named("speakerBean")
public class SpeakerBean implements Serializable {

	private Speaker speaker;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private Logger logger;


	public Speaker getSpeaker() {

		if (speaker == null) {
			speaker = new Speaker();
		}
		return speaker;
	}

	public void setSpeaker(Speaker conference) {

		this.speaker = conference;
	}

	public String edit(Long speakerId) throws ServiceException {

		if (speakerId != null) {
			Speaker speaker = this.speakerService.findSpeakerById(speakerId);
			this.setSpeaker(speaker);
		} else {

			logger.warn("No Speaker ID submitted!");
			logger.warn("Creating new Speaker Instance!");

			this.setSpeaker(new Speaker());
		}
		return "adminSpeaker";
	}

	public String save() throws ServiceException {

		if (speaker != null) {
			if (speaker.getId() == null) {
				speaker = this.speakerService.createSpeaker(speaker);
			} else {
				speaker = this.speakerService.updateSpeaker(speaker);
			}
		}

		return "admin";
	}

	public String remove() throws ServiceException {

		if (speaker != null) {
			if (speaker.getId() == null) {
				logger.warn("Cannot remove unpersistent Speaker.");
			} else {
				speaker = this.speakerService.removeSpeaker(speaker);
			}
		}

		return "admin";
	}
}