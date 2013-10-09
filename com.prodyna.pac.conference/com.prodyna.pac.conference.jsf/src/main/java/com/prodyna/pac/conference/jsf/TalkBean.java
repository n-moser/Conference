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

package com.prodyna.pac.conference.jsf;

import com.prodyna.pac.conference.ejb.facade.datatype.Conference;
import com.prodyna.pac.conference.ejb.facade.datatype.Room;
import com.prodyna.pac.conference.ejb.facade.datatype.Speaker;
import com.prodyna.pac.conference.ejb.facade.datatype.Talk;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.room.RoomService;
import com.prodyna.pac.conference.ejb.facade.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.facade.service.talk.TalkService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * TalkBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.10.13
 * Time: 09:51
 */
@ManagedBean
@SessionScoped
@Named("talkBean")
public class TalkBean implements Serializable {

	private Talk talk;

	private List<Speaker> speakers;

	private List<Room> rooms;

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private Logger logger;


	public Talk getTalk() {

		if (talk == null) {
			talk = new Talk();
		}
		return talk;
	}

	public void setTalk(Talk conference) {

		this.talk = conference;
	}

	public List<Speaker> getSpeakers() throws ServiceException {

		if (speakers == null) {
			if (this.talk != null && this.talk.getId() != null) {
				this.speakers = this.speakerService.getSpeakersByTalk(this.talk);
			}
		}
		return speakers;
	}

	public List<Room> getRooms() throws ServiceException {

		Conference conference = this.talk.getConference();
		if (conference != null) {
			return roomService.getRoomsByConference(conference);
		}

		return Collections.emptyList();
	}

	public String edit(Long talkId) throws ServiceException {

		if (talkId != null) {
			Talk talk = this.talkService.findTalkById(talkId);
			this.setTalk(talk);
		} else {

			logger.warn("No Talk ID submitted!");
			logger.warn("Creating new Talk Instance!");

			this.setTalk(new Talk());
		}
		return "adminTalk";
	}

	public String save() throws ServiceException {

		if (talk != null) {
			if (talk.getId() == null) {
				talk = this.talkService.createTalk(talk);
			} else {
				talk = this.talkService.updateTalk(talk);
			}
		}

		return "admin";
	}

	public String remove() throws ServiceException {

		if (talk != null) {
			if (talk.getId() == null) {
				logger.warn("Cannot remove unpersistent Talk.");
			} else {
				talk = this.talkService.removeTalk(talk);
			}
		}

		return "admin";
	}
}
