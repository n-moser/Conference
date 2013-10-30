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

package com.prodyna.pac.conference.rest.api.resource.admin;

import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.rest.api.exception.RESTException;
import com.prodyna.pac.conference.rest.api.resource.TalkResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST Resource to retrieve and administrate conference information.
 * <p/>
 * Author: Nicolas Moser
 * Date: 17.10.13
 * Time: 15:47
 */
@RolesAllowed("admin")
@Path("secure/talk")
public interface TalkAdminResource extends TalkResource {

	/**
	 * Adds a new Talk resource.
	 *
	 * @param talk
	 * 		the talk to add
	 *
	 * @return the added talk
	 *
	 * @throws RESTException
	 * 		when the creation fails
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Talk createTalk(Talk talk) throws RESTException;

	/**
	 * Updates an existing Talk resource.
	 *
	 * @param talk
	 * 		the talk to update
	 *
	 * @return the update talk
	 *
	 * @throws RESTException
	 * 		when the modification fails
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Talk updateTalk(Talk talk) throws RESTException;

	/**
	 * Remvoves an existing Talk resource.
	 *
	 * @param id
	 * 		id of the talk to remove
	 *
	 * @return the removed talk
	 *
	 * @throws RESTException
	 * 		when the deletion fails
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Talk deleteTalk(@PathParam("id") Long id) throws RESTException;
}
