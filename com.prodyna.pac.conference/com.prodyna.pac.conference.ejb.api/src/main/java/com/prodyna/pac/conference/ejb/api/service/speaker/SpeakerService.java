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

package com.prodyna.pac.conference.ejb.api.service.speaker;

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.Service;

import javax.ejb.Local;
import java.util.List;

/**
 * SpeakerService
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:43
 */
@Local
public interface SpeakerService extends Service {

	/**
	 * Find the Speaker entity with the given ID.
	 *
	 * @param id
	 * 		the id of the speaker entity
	 *
	 * @return the speaker entity
	 *
	 * @throws ServiceException
	 * 		when the Speaker entity with the given ID does not exist in the database
	 */
	Speaker findSpeakerById(Long id) throws ServiceException;

	/**
	 * Find the Speaker entity with the given Name.
	 *
	 * @param name
	 * 		the id of the speaker entity
	 *
	 * @return the speaker entity
	 *
	 * @throws ServiceException
	 * 		when the Speaker entity with the given Name does not exist in the database
	 */
	Speaker findSpeakerByName(String name) throws ServiceException;

	/**
	 * Find all Speaker entity instances.
	 *
	 * @return the list of all existing speaker instances
	 *
	 * @throws ServiceException
	 * 		when the Speaker query fails
	 */
	List<Speaker> getAllSpeakers() throws ServiceException;

	/**
	 * Find all Speaker entity instances that hold a reference to the given talk.
	 *
	 * @param talk
	 * 		the talk holding the speakers
	 *
	 * @return the list of all existing speaker instances of the given talk
	 *
	 * @throws ServiceException
	 * 		when the Speakers query fails
	 */
	List<Speaker> getSpeakersByTalk(Talk talk) throws ServiceException;

	/**
	 * Persists a non-persistent Speaker entity to the database.
	 *
	 * @param Speaker
	 * 		the speaker entity to persist
	 *
	 * @return the persisted entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be persisted
	 */
	Speaker createSpeaker(Speaker Speaker) throws ServiceException;

	/**
	 * Updates an already persistent Speaker entity to the database.
	 *
	 * @param Speaker
	 * 		the speaker entity to update
	 *
	 * @return the updated entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be updated
	 */
	Speaker updateSpeaker(Speaker Speaker) throws ServiceException;

	/**
	 * Removes an already persistent Speaker entity from the database.
	 *
	 * @param Speaker
	 * 		the speaker entity to remove
	 *
	 * @return the removed entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be removed
	 */
	Speaker removeSpeaker(Speaker Speaker) throws ServiceException;

}
