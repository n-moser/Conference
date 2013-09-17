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

package com.prodyna.pac.conference.ejb.beans.service.speaker;

import com.prodyna.pac.conference.ejb.beans.service.ServiceBean;
import com.prodyna.pac.conference.ejb.facade.datatype.Speaker;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.speaker.SpeakerService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * SpeakerServiceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:49
 */
@Stateless
public class SpeakerServiceBean extends ServiceBean implements SpeakerService {

	private static final String QUERY_FIND_SPEAKER_BY_NAME = "Speaker.findSpeakerByName";

	@Inject
	private EntityManager entityManager;

	@Override
	public Speaker findSpeakerById(Long id) throws ServiceException {

		try {

			return this.entityManager.find(Speaker.class, id);

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Speaker entity with ID '" + id + "'.", pe);
		}
	}

	@Override
	public Speaker findSpeakerByName(String name) throws ServiceException {

		try {
			TypedQuery<Speaker> query = this.entityManager.
					createNamedQuery(QUERY_FIND_SPEAKER_BY_NAME, Speaker.class);

			query.setParameter("name", name);

			return query.getSingleResult();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Speaker entity with name '" + name + "'.", pe);
		}
	}

	@Override
	public Speaker createSpeaker(Speaker speaker) throws ServiceException {

		try {
			this.entityManager.persist(speaker);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error persisting new Speaker entity with name '" + speaker.getName() + "'.",
					pe);
		}

		return speaker;
	}

	@Override
	public Speaker updateSpeaker(Speaker speaker) throws ServiceException {

		try {
			speaker = this.entityManager.merge(speaker);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error updating Speaker entity with ID '" + speaker.getId() + "'.", pe);
		}

		return speaker;

	}

	@Override
	public Speaker removeSpeaker(Speaker speaker) throws ServiceException {

		try {
			speaker = this.findSpeakerById(speaker.getId());
			this.entityManager.remove(speaker);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error removing Speaker entity with ID '" + speaker.getId() + "'.", pe);
		}

		return speaker;
	}
}
