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

package com.prodyna.pac.conference.ejb.beans.service.room;

import com.prodyna.pac.conference.ejb.beans.service.ServiceBean;
import com.prodyna.pac.conference.ejb.facade.datatype.Room;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.room.RoomService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * RoomServiceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:49
 */
@Stateless
public class RoomServiceBean extends ServiceBean implements RoomService {

    private static final String QUERY_FIND_ROOM_BY_NAME = "Room.findRoomByName";

    @Inject
    private EntityManager entityManager;

    @Override
    public Room findRoomById(Long id) throws ServiceException {
        try {

            return this.entityManager.find(Room.class, id);

        } catch (PersistenceException pe) {
            throw new ServiceException("Cannot find Room entity with ID '" + id + "'.", pe);
        }
    }

    @Override
    public Room findRoomByName(String name) throws ServiceException {

        try {
            TypedQuery<Room> query = this.entityManager.
                    createNamedQuery(QUERY_FIND_ROOM_BY_NAME, Room.class);

            query.setParameter("name", name);

            return query.getSingleResult();

        } catch (PersistenceException pe) {
            throw new ServiceException("Cannot find Room entity with name '" + name + "'.", pe);
        }
    }

    @Override
    public Room createRoom(Room room) throws ServiceException {

        try {
            this.entityManager.persist(room);
            this.entityManager.flush();
        } catch (PersistenceException pe) {
            throw new ServiceException("Error persisting new Room entity with name '" + room.getName() + "'.", pe);
        }

        return room;
    }

    @Override
    public Room updateRoom(Room room) throws ServiceException {

        try {
            room = this.entityManager.merge(room);
            this.entityManager.flush();
        } catch (PersistenceException pe) {
            throw new ServiceException("Error updating Room entity with ID '" + room.getId() + "'.", pe);
        }

        return room;

    }

    @Override
    public Room removeRoom(Room room) throws ServiceException {

        try {
            this.entityManager.remove(room);
            this.entityManager.flush();
        } catch (PersistenceException pe) {
            throw new ServiceException("Error removing Room entity with ID '" + room.getId() + "'.", pe);
        }

        return room;
    }
}
