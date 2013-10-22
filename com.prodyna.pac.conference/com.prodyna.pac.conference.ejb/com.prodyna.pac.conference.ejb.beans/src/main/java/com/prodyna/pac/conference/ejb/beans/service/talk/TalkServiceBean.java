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

import com.prodyna.pac.conference.ejb.api.datatype.*;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.exception.ValidationException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.ejb.beans.interceptor.Performance;
import com.prodyna.pac.conference.ejb.beans.service.ServiceBean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * TalkServiceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:49
 */
@Stateless
@Performance
public class TalkServiceBean extends ServiceBean implements TalkService {

	private static final String QUERY_FIND_ALL_TALKS = "Talk.findAllTalks";

	private static final String QUERY_FIND_TALKS_BY_CONFERENCE = "Talk.findTalksByConference";

	private static final String QUERY_FIND_TALKS_BY_ROOM = "Talk.findTalksByRoom";

	private static final String QUERY_FIND_TALKS_BY_ROOM_IN_TIME = "Talk.findTalksByRoomInTime";

	private static final String QUERY_FIND_TALKS_BY_SPEAKER = "Talk.findTalksBySpeaker";

	private static final String QUERY_FIND_TALKS_BY_SPEAKER_IN_TIME = "Talk.findTalksBySpeakerInTime";

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
	public List<Talk> getAllTalks() throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_ALL_TALKS, Talk.class);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list all Talk entities.", pe);
		}
	}

	@Override
	public List<Talk> getTalksByConference(Conference conference) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALKS_BY_CONFERENCE, Talk.class);

			query.setParameter("conference", conference);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list Talk entities for conference.", pe);
		}
	}

	@Override
	public List<Talk> getTalksByRoom(Room room) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALKS_BY_ROOM, Talk.class);

			query.setParameter("room", room);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list Talk entities for room.", pe);
		}
	}

	@Override
	public List<Talk> getTalksByRoom(Room room, Date startTime, Date endTime) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALKS_BY_ROOM_IN_TIME, Talk.class);

			query.setParameter("room", room);
			query.setParameter("startTime", startTime, TemporalType.TIMESTAMP);
			query.setParameter("endTime", endTime, TemporalType.TIMESTAMP);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list Talk entities for room.", pe);
		}
	}

	@Override
	public List<Talk> getTalksBySpeaker(Speaker speaker) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALKS_BY_SPEAKER, Talk.class);

			query.setParameter("speaker", speaker);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list Talk entities for speaker.", pe);
		}
	}

	@Override
	public List<Talk> getTalksBySpeaker(Speaker speaker, Date startTime, Date endTime) throws ServiceException {

		try {
			TypedQuery<Talk> query = this.entityManager.
					createNamedQuery(QUERY_FIND_TALKS_BY_SPEAKER_IN_TIME, Talk.class);

			query.setParameter("speaker", speaker);
			query.setParameter("startTime", startTime, TemporalType.TIMESTAMP);
			query.setParameter("endTime", endTime, TemporalType.TIMESTAMP);

			return query.getResultList();

		} catch (PersistenceException pe) {
			throw new ServiceException("Cannot list Talk entities for speaker.", pe);
		}
	}

	@Override
	public Talk createTalk(Talk talk) throws ServiceException {

		this.validateTalk(talk);

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

		this.validateTalk(talk);

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

	@Override
	public void validateTalk(Talk talk) throws ServiceException {

		ValidationException exception = new ValidationException("Error validating Talk.");

		Date startTime = talk.getStartDate();
		Date endTime = talk.getEndDate();

		Conference conference = talk.getConference();
		if (conference != null) {

			if (startTime.before(conference.getStartDate())) {
				exception.addItem("startDate",
						"Talk must be within the timeframe of conference '" + conference.getName() + "'.");
			}
			if (endTime.after(conference.getEndDate())) {
				exception.addItem("startDate",
						"Talk must be within the timeframe of conference '" + conference.getName() + "'.");
			}
		}

		for (TalkSpeaker talkSpeaker : talk.getSpeakers()) {

			Speaker speaker = talkSpeaker.getSpeaker();

			if (speaker != null) {
				List<Talk> talks = this.getTalksBySpeaker(speaker, startTime, endTime);

				if (!talks.isEmpty()) {

					if (talks.size() == 1) {
						Talk intersectingTalk = talks.get(0);
						if (talk.getId().equals(intersectingTalk.getId())) {
							// Skip if intersected Talk is validated Talk.
							continue;
						}
					}

					exception.addItem("speakers",
							"The talk intersects another talk of speaker '" + speaker.getName() + "'.");
				}
			}
		}

		Room room = talk.getRoom();

		if (room != null) {
			List<Talk> talks = this.getTalksByRoom(room, startTime, endTime);

			if (!talks.isEmpty()) {

				if (talks.size() == 1) {
					Talk intersectingTalk = talks.get(0);
					if (talk.getId().equals(intersectingTalk.getId())) {
						// Skip if intersected Talk is validated Talk.
						return;
					}
				}

				exception.addItem("room", "The talk intersects another talk in room '" + room.getName() + "'.");
			}
		}

		if (exception.hasItems()) {
			throw exception;
		}
	}
}
