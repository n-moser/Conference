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

package com.prodyna.pac.conference.rest.beans.resources;

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.exception.RESTException;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * ConferenceResource
 * <p/>
 * Author: Nicolas Moser
 * Date: 19.09.13
 * Time: 18:28
 */
@Path("conference")
public class ConferenceResource {

	@Inject
	private ConferenceService conferenceService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Conference findConference(@PathParam("id") Long id) throws RESTException {

		try {
			return this.conferenceService.findConferenceById(id);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conference> getAllTasks() throws RESTException {

		try {
			return this.conferenceService.getAllConferences();
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Conference createConference(Conference conference) throws RESTException {

		try {
			return this.conferenceService.createConference(conference);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Conference updateConference(Conference conference) throws RESTException {

		try {
			return this.conferenceService.updateConference(conference);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Conference deleteConference(Long id) throws RESTException {

		try {
			Conference conference = this.conferenceService.findConferenceById(id);
			return this.conferenceService.removeConference(conference);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

}
