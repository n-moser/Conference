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

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.ejb.api.util.DateIterator;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * RoomBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 14.10.13
 * Time: 12:50
 */
@ManagedBean
@SessionScoped
@Named("roomBean")
public class RoomBean implements Serializable {

	@Inject
	private RoomService roomService;

	@Inject
	private TalkService talkService;

	private Room room;

	private List<Date> dates = new ArrayList<Date>();

	private List<Talk>[] talks;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Inject
	private Logger logger;

	public Room getRoom() {

		return room;
	}



	public void setRoom(Room room) {

		if (room == null) {
			logger.error("Cannot set Room 'null'.");
			this.room = new Room();
		} else {
			this.room = room;
		}

		this.dates.clear();

		if (room.getConference() != null) {

			Conference conference = room.getConference();

			if (conference.getStartDate() != null && conference.getEndDate() != null) {

				DateIterator dateIterator = new DateIterator(conference.getStartDate(), conference.getEndDate());

				while (dateIterator.hasNext()) {
					Date date = dateIterator.next();
					this.dates.add(date);
				}
			}
		}

		try {
			this.talks = new List[dates.size()];

			List<Talk> talks = talkService.getTalksByRoom(room);

			for (int i = 0; i < this.dates.size(); i++) {
				Calendar date = Calendar.getInstance();
				date.setTime(this.dates.get(i));
				this.talks[i] = new ArrayList<Talk>();

				for (Talk talk : talks) {
					Calendar talkDate = Calendar.getInstance();
					talkDate.setTime(talk.getStartDate());

					if (date.get(Calendar.DAY_OF_YEAR) == talkDate.get(Calendar.DAY_OF_YEAR)) {
						this.talks[i].add(talk);
					}
				}
			}

		} catch (ServiceException e) {
			logger.error("Cannot load talks for room {}", room.getName(), e);
		}

	}

	public List<Date> getDates() {

		return this.dates;
	}

	public List<Talk>[] getTalks() {

		return this.talks;
	}

	public String open(Long roomId) throws ServiceException {

		if (roomId == null) {
			logger.error("No Room ID submitted!");
		} else {
			Room room = this.roomService.findRoomById(roomId);
			this.setRoom(room);

			this.breadCrumpBean.setRoom(room);
			this.breadCrumpBean.setSpeaker(null);
		}

		return "room";
	}
}
