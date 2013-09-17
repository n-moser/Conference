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

package com.prodyna.pac.conference.ejb.beans.service.talk;

import com.prodyna.pac.conference.ejb.beans.service.ServiceBean;
import com.prodyna.pac.conference.ejb.facade.datatype.Talk;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.talk.TalkService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * TalkServiceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:49
 */
@Stateless
public class TalkServiceBean extends ServiceBean implements TalkService {

	private static final String QUERY_FIND_TALK_BY_NAME = "Talk.findTalkByName";

	@Inject
	private EntityManager entityManager;

	@Override
	public Talk findTalkById(Long id) throws ServiceException {

		try {

			return this.entityManager.find(Talk.class, id);

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Talk entity with ID '" + id + "'.", pe);
		}
	}

	@Override
	public Talk findTalkByName(String name) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALK_BY_NAME, Talk.class);

			query.setParameter("name", name);

			return query.getSingleResult();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot find Talk entity with name '" + name + "'.", pe);
		}
	}

	@Override
	public Talk createTalk(Talk talk) throws ServiceException {

		try {
			this.entityManager.persist(talk);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error persisting new Talk entity with name '" + talk.getName() + "'.", pe);
		}

		return talk;
	}

	@Override
	public Talk updateTalk(Talk talk) throws ServiceException {

		try {
			talk = this.entityManager.merge(talk);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error updating Talk entity with ID '" + talk.getId() + "'.", pe);
		}

		return talk;

	}

	@Override
	public Talk removeTalk(Talk talk) throws ServiceException {

		try {
			talk = this.findTalkById(talk.getId());
			this.entityManager.remove(talk);
			this.entityManager.flush();
		} catch (PersistenceException pe) {
			throw new ServiceException("Error removing Talk entity with ID '" + talk.getId() + "'.", pe);
		}

		return talk;
	}
}
