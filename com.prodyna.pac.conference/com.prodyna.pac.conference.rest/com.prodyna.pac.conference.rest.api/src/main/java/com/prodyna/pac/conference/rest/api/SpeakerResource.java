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

package com.prodyna.pac.conference.rest.api;

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.exception.RESTException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * SpeakerResource
 * <p/>
 * Author: Nicolas Moser
 * Date: 17.10.13
 * Time: 15:46
 */
@Path("speaker")
public interface SpeakerResource {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Speaker findSpeaker(@PathParam("id") Long id) throws RESTException;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<Speaker> getAllSpeakers() throws RESTException;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Speaker createSpeaker(Speaker speaker) throws RESTException;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Speaker updateSpeaker(Speaker speaker) throws RESTException;

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	Speaker deleteSpeaker(Long id) throws RESTException;
}
