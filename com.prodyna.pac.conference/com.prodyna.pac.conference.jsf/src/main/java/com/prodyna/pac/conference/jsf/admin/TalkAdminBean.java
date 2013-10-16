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

import com.prodyna.pac.conference.ejb.facade.datatype.*;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.facade.service.room.RoomService;
import com.prodyna.pac.conference.ejb.facade.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.facade.service.talk.TalkService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TalkAdminBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.10.13
 * Time: 09:51
 */
@ManagedBean
@SessionScoped
@Named("talkAdminBean")
public class TalkAdminBean implements Serializable {

	private Talk talk;

	private List<Room> rooms;

	private Long speakerId;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private ConferenceAdminBean conferenceAdminBean;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Logger logger;

	private List<Speaker> speakers;

	/**
	 * Getter for the list of all conferences.
	 *
	 * @return all conferences
	 */
	public List<Speaker> getSpeakers() {

		try {
			List<Speaker> existingSpeakers = new ArrayList<Speaker>();
			for (TalkSpeaker talkSpeaker : this.talk.getSpeakers()) {
				existingSpeakers.add(talkSpeaker.getSpeaker());
			}

			this.speakers = speakerService.getAllSpeakers();
			this.speakers.removeAll(existingSpeakers);

		} catch (ServiceException se) {
			logger.error("Error retrieving Speakers.", se);
		}

		return this.speakers;
	}

	public Talk getTalk() {

		if (talk == null) {
			talk = new Talk();
		}
		return talk;
	}

	public void setTalk(Talk conference) {

		this.talk = conference;
	}

	public List<Room> getRooms() throws ServiceException {

		Conference conference = this.talk.getConference();
		if (conference != null) {
			return roomService.getRoomsByConference(conference);
		}

		return Collections.emptyList();
	}

	public Long getSpeakerId() {

		return this.speakerId;
	}

	public void setSpeakerId(Long speakerId) {

		this.speakerId = speakerId;
	}

	public void assignSpeaker() {

		if (this.speakerId != null) {

			try {
				Speaker speaker = this.speakerService.findSpeakerById(this.speakerId);

				TalkSpeaker talkSpeaker = new TalkSpeaker();
				talkSpeaker.setSpeaker(speaker);

				this.talk.getSpeakers().add(talkSpeaker);

				this.speakerId = null;

			} catch (ServiceException e) {
				// TODO Exception Handling
				e.printStackTrace();
			}
		}
	}

	public void unassignSpeaker(Long talkSpeakerId) {

		if (talkSpeakerId != null) {

			TalkSpeaker talkSpeaker = null;
			for (TalkSpeaker speaker : this.talk.getSpeakers()) {
				if (speaker.getId().equals(talkSpeakerId)) {
					talkSpeaker = speaker;
				}
			}
			this.talk.getSpeakers().remove(talkSpeaker);
		}
	}

	public String edit(Long talkId) throws ServiceException {

		return this.edit(talkId, null);
	}

	public String edit(Long talkId, Long conferenceId) throws ServiceException {

		if (talkId != null) {
			Talk talk = this.talkService.findTalkById(talkId);
			this.setTalk(talk);
		} else {

			logger.warn("No Talk ID submitted!");
			logger.warn("Creating new Talk Instance!");

			this.setTalk(new Talk());

			if (this.rooms != null) {
				this.rooms.clear();
			}
		}

		if (conferenceId != null) {
			Conference conference = conferenceService.findConferenceById(conferenceId);
			this.talk.setConference(conference);
		}

		return "adminTalk";
	}

	/**
	 * Validate that at least 1 speaker is assigned to the talk.
	 *
	 * @param event
	 * 		the validation event
	 */
	public void validateSpeakers(ComponentSystemEvent event) {

		if (talk.getSpeakers().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("talkForm:speakerPanel",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wert muss gewählt werden.",
							"Mindestens ein Speaker muss gewählt werden."));

			facesContext.validationFailed();
		}
	}

	public String save() throws ServiceException {

		if (facesContext.isValidationFailed()) {
			return null;
		}

		if (talk != null) {
			if (talk.getId() == null) {
				talk = this.talkService.createTalk(talk);
			} else {
				talk = this.talkService.updateTalk(talk);
			}
		}

		conferenceAdminBean.setConference(talk.getConference());

		return "adminConference";
	}

	public String remove() throws ServiceException {

		if (talk != null) {
			if (talk.getId() == null) {
				logger.warn("Cannot remove unpersistent Talk.");
			} else {
				talk = this.talkService.removeTalk(talk);
			}
		}

		if (conferenceAdminBean.getConference() == null) {
			return "admin";
		}

		return "adminConference";
	}
}