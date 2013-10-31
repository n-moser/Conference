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

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Responsible for admin (CRUD) operations on Room entity instances.
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.10.13
 * Time: 09:51
 */
@ManagedBean
@SessionScoped
@RolesAllowed("admin")
@Named("roomAdminBean")
public class RoomAdminBean extends AdminBean implements Serializable {

	private Room room;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private RoomService roomService;

	@Inject
	private ConferenceAdminBean conferenceAdminBean;

	@Inject
	private Logger logger;

	/**
	 * Getter for the room instance.
	 *
	 * @return the room
	 */
	public Room getRoom() {

		if (room == null) {
			room = new Room();
		}
		return room;
	}

	/**
	 * Setter for the room instance.
	 *
	 * @param room
	 * 		the room to set
	 */
	public void setRoom(Room room) {

		this.room = room;
	}

	/**
	 * Edit the room with the given id.
	 *
	 * @param roomId
	 * 		the room ID
	 *
	 * @return the navigation outcome
	 *
	 * @throws ServiceException
	 * 		when the room cannot be loaded
	 */
	public String edit(Long roomId) throws ServiceException {

		return this.edit(roomId, null);
	}

	/**
	 * Edit the room with the given id and an optional conference ID.
	 *
	 * @param roomId
	 * 		the room ID
	 * @param conferenceId
	 * 		the conference ID of the default conference (optional)
	 *
	 * @return the navigation outcome
	 *
	 * @throws ServiceException
	 * 		when the room cannot be loaded
	 */
	public String edit(Long roomId, Long conferenceId) throws ServiceException {

		if (roomId != null) {
			Room room = this.roomService.findRoomById(roomId);
			this.setRoom(room);
		} else {

			logger.warn("No Room ID submitted!");
			logger.warn("Creating new Room Instance!");

			this.setRoom(new Room());
		}

		if (conferenceId != null) {
			Conference conference = conferenceService.findConferenceById(conferenceId);
			this.room.setConference(conference);
		}

		return "adminRoom?faces-redirect=true";
	}

	@Override
	protected String internalSave() throws ServiceException {

		if (room != null) {
			if (room.getId() == null) {
				room = this.roomService.createRoom(room);
			} else {
				room = this.roomService.updateRoom(room);
			}
		}

		conferenceAdminBean.setConference(room.getConference());

		return "adminConference?faces-redirect=true";
	}

	@Override
	protected String internalRemove() throws ServiceException {


		if (room != null) {
			if (room.getId() == null) {
				logger.warn("Cannot remove unpersistent Room.");
			} else {
				room = this.roomService.removeRoom(room);
			}
		}

		if (conferenceAdminBean.getConference() == null) {
			return "admin?faces-redirect=true";
		}

		return "adminConference?faces-redirect=true";
	}

	@Override
	protected String getFormName() {
		return "roomForm";
	}
}
