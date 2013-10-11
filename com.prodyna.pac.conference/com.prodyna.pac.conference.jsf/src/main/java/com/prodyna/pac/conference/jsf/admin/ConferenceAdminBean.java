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

package com.prodyna.pac.conference.jsf.admin;

import com.prodyna.pac.conference.ejb.facade.datatype.Conference;
import com.prodyna.pac.conference.ejb.facade.datatype.Room;
import com.prodyna.pac.conference.ejb.facade.datatype.Talk;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.facade.service.room.RoomService;
import com.prodyna.pac.conference.ejb.facade.service.talk.TalkService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * ConferenceAdminBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 26.09.13
 * Time: 18:46
 */
@ManagedBean
@SessionScoped
@Named("conferenceAdminBean")
public class ConferenceAdminBean implements Serializable {

	private Conference conference;

	private List<Talk> talks;

	private List<Room> rooms;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private RoomService roomService;

	@Inject
	private TalkService talkService;

	@Inject
	private Logger logger;

	public Conference getConference() {

		if (conference == null) {
			conference = new Conference();
		}
		return conference;
	}

	public void setConference(Conference conference) {

		this.conference = conference;
	}

	public List<Talk> getTalks() throws ServiceException {

		if (conference != null && conference.getId() != null) {
			this.talks = this.talkService.getTalksByConference(this.conference);
		}
		return talks;
	}

	public List<Room> getRooms() throws ServiceException {

		if (conference != null && conference.getId() != null) {
			this.rooms = this.roomService.getRoomsByConference(this.conference);
		}
		return rooms;
	}

	public String edit(Long conferenceId) throws ServiceException {

		if (conferenceId != null) {
			Conference conference = this.conferenceService.findConferenceById(conferenceId);
			this.setConference(conference);
		} else {

			logger.warn("No Conference ID submitted!");
			logger.warn("Creating new Conference Instance!");

			this.setConference(new Conference());

			if (this.talks != null) {
				this.talks.clear();
			}
			if (this.rooms != null) {
				this.rooms.clear();
			}
		}

		return "adminConference";
	}

	public String save() throws ServiceException {

		if (conference != null) {
			if (conference.getId() == null) {
				conference = this.conferenceService.createConference(conference);
			} else {
				conference = this.conferenceService.updateConference(conference);
			}
		}

		return "admin";
	}

	public String remove() throws ServiceException {

		if (conference != null) {
			if (conference.getId() == null) {
				logger.warn("Cannot remove unpersistent Conference.");
			} else {
				conference = this.conferenceService.removeConference(conference);
			}
		}

		return "admin";
	}

}
