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

package com.prodyna.pac.conference.ejb.api.service.room;

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.Service;

import javax.ejb.Local;
import java.util.List;

/**
 * RoomService
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:43
 */
@Local
public interface RoomService extends Service {

	/**
	 * Find the Room entity with the given ID.
	 *
	 * @param id
	 * 		the id of the room entity
	 *
	 * @return the room entity
	 *
	 * @throws ServiceException
	 * 		when the Room entity with the given ID does not exist in the database
	 */
	Room findRoomById(Long id) throws ServiceException;

	/**
	 * Find the Room entity with the given Name.
	 *
	 * @param name
	 * 		the id of the room entity
	 *
	 * @return the room entity
	 *
	 * @throws ServiceException
	 * 		when the Room entity with the given Name does not exist in the database
	 */
	Room findRoomByName(String name) throws ServiceException;

	/**
	 * Find all Room entity instances.
	 *
	 * @return the list of all existing room instances
	 *
	 * @throws ServiceException
	 * 		when the Room query fails
	 */
	List<Room> getAllRooms() throws ServiceException;

	/**
	 * Find all Room entity instances that hold a reference to the given conference.
	 *
	 * @param conference
	 * 		the conference holding the rooms
	 *
	 * @return the list of all existing room instances of the given conference
	 *
	 * @throws ServiceException
	 * 		when the Room query fails
	 */
	List<Room> getRoomsByConference(Conference conference) throws ServiceException;

	/**
	 * Persists a non-persistent Room entity to the database.
	 *
	 * @param Room
	 * 		the room entity to persist
	 *
	 * @return the persisted entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be persisted
	 */
	Room createRoom(Room Room) throws ServiceException;

	/**
	 * Updates an already persistent Room entity to the database.
	 *
	 * @param Room
	 * 		the room entity to update
	 *
	 * @return the updated entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be updated
	 */
	Room updateRoom(Room Room) throws ServiceException;

	/**
	 * Removes an already persistent Room entity from the database.
	 *
	 * @param Room
	 * 		the room entity to remove
	 *
	 * @return the removed entity
	 *
	 * @throws ServiceException
	 * 		when the entity cannot be removed
	 */
	Room removeRoom(Room Room) throws ServiceException;
}
