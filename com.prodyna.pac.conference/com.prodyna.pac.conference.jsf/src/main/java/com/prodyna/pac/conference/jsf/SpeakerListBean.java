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

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * ConferenceListBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 26.09.13
 * Time: 20:16
 */
@ManagedBean
@RequestScoped
@Named("speakerListBean")
public class SpeakerListBean {

	@Inject
	private SpeakerService service;

	@Inject
	private Logger logger;

	private List<Speaker> speakers;

	/**
	 * Getter for the list of all conferences.
	 *
	 * @return all conferences
	 */
	public List<Speaker> getSpeakers() {

		return this.speakers;
	}

	@PostConstruct
	public void init() {

		try {
			this.speakers = service.getAllSpeakers();

		} catch (ServiceException se) {
			logger.error("Error retrieving Speakers.", se);
		}
	}
}
