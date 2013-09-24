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

package com.prodyna.pac.conference.ejb.beans.service.conference;

import com.prodyna.pac.conference.ejb.beans.service.ServiceBean;
import com.prodyna.pac.conference.ejb.facade.datatype.Conference;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceServiceLocal;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceServiceRemote;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ConferenceServiceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:49
 */
@Stateless
public class ConferenceServiceBean extends ServiceBean implements ConferenceServiceLocal, ConferenceServiceRemote {

	private static final String QUERY_FIND_ALL_CONFERENCES = "Conference.findAllConferences";

	private static final String QUERY_FIND_CONFERENCE_BY_NAME = "Conference.findConferenceByName";

	@Inject
	private EntityManager entityManager;

	@Override
	public Conference findConferenceById(Long id) throws ServiceException {

		try {
			return this.entityManager.find(Conference.class, id);

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Conference entity with ID '" + id + "'.", pe);
		}
	}

	@Override
	public Conference findConferenceByName(String name) throws ServiceException {

		try {
			TypedQuery<Conference> query = this.entityManager.
					createNamedQuery(QUERY_FIND_CONFERENCE_BY_NAME, Conference.class);

			query.setParameter("name", name);

			return query.getSingleResult();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Conference entity with name '" + name + "'.", pe);
		}
	}

	@Override
	public List<Conference> getAllConferences() throws ServiceException {

		try {
			TypedQuery<Conference> query = this.entityManager.
					createNamedQuery(QUERY_FIND_ALL_CONFERENCES, Conference.class);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list all Conference entities.", pe);
		}
	}

	@Override
	public Conference createConference(Conference conference) throws ServiceException {

		try {
			this.entityManager.persist(conference);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException(
					"Error persisting new Conference entity with name '" + conference.getName() + "'.", pe);
		}

		return conference;
	}

	@Override
	public Conference updateConference(Conference conference) throws ServiceException {

		try {
			conference = this.entityManager.merge(conference);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error updating Conference entity with ID '" + conference.getId() + "'.", pe);
		}

		return conference;
	}

	@Override
	public Conference removeConference(Conference conference) throws ServiceException {

		try {
			conference = this.findConferenceById(conference.getId());
			this.entityManager.remove(conference);
			this.entityManager.flush();

			this.entityManager.detach(conference);

		} catch (PersistenceException pe) {
			throw new ServiceException("Error removing Conference entity with ID '" + conference.getId() + "'.", pe);
		}

		return conference;
	}
}
