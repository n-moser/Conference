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

package com.prodyna.pac.conference.jsf.breadcrump;

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Bean holding all breadcrump entities in the session.
 * <p/>
 * Author: Nicolas Moser
 * Date: 01.10.13
 * Time: 00:22
 */
@ManagedBean
@SessionScoped
@Named("breadCrumpBean")
public class BreadCrumpBean implements Serializable {

	@Inject
	private Logger logger;

	private Conference conference;

	private Talk talk;

	private Speaker speaker;

	private Room room;

	/**
	 * Getter for the conference entity.
	 *
	 * @return the conference
	 */
	public Conference getConference() {

		return conference;
	}

	/**
	 * Setter for the conference.
	 *
	 * @param conference
	 * 		the conference to set
	 */
	public void setConference(Conference conference) {

		this.conference = conference;
	}

	/**
	 * Getter for the talk entity.
	 *
	 * @return the talk
	 */
	public Talk getTalk() {

		return talk;
	}

	/**
	 * Setter for the talk entity.
	 *
	 * @param talk
	 * 		the talk to set
	 */
	public void setTalk(Talk talk) {

		this.talk = talk;
	}


	/**
	 * Getter for the speaker entity.
	 *
	 * @return the speaker
	 */
	public Speaker getSpeaker() {

		return speaker;
	}

	/**
	 * Setter for the speaker entity.
	 *
	 * @param speaker
	 * 		the speaker to set
	 */
	public void setSpeaker(Speaker speaker) {

		if (speaker != null) {
			this.room = null;
		}

		this.speaker = speaker;
	}

	/**
	 * Getter for the room entity.
	 *
	 * @return the room
	 */
	public Room getRoom() {

		return room;
	}

	/**
	 * Setter for the room entity.
	 *
	 * @param room
	 * 		the room to set
	 */
	public void setRoom(Room room) {

		if (room != null) {
			this.speaker = null;
		}

		this.room = room;
	}

	/** Clears all breadcrump state. */
	public void clear() {

		this.setConference(null);
		this.setTalk(null);
		this.setRoom(null);
		this.setSpeaker(null);
	}

}
