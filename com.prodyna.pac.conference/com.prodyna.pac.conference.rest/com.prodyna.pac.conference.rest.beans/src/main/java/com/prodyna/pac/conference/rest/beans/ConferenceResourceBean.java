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

package com.prodyna.pac.conference.rest.beans;

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.exception.RESTException;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.rest.api.ConferenceResource;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * ConferenceResourceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 19.09.13
 * Time: 18:28
 */
public class ConferenceResourceBean implements ConferenceResource {

	@Inject
	private ConferenceService conferenceService;

	@Override
	public Conference findConference(@PathParam("id") Long id) throws RESTException {

		try {
			return this.getConferenceService().findConferenceById(id);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	public List<Conference> getAllConferences() throws RESTException {

		try {
			return this.getConferenceService().getAllConferences();
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	/**
	 * Getter for the conference service EJB.
	 *
	 * @return the conference service
	 */
	protected ConferenceService getConferenceService() {

		return this.conferenceService;
	}

}
