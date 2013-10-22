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

package com.prodyna.pac.conference.rest.beans;


import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import com.prodyna.pac.conference.rest.api.admin.RoomAdminResource;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;

/**
 * RoomResourceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.10.13
 * Time: 15:26
 */
@RunWith(Arquillian.class)
public class RoomResourceTest extends ResourceTest {

	private RoomAdminResource roomResource;

	@Before
	public void setUp() {

		super.setUp();

		this.roomResource = super.getResource(RoomAdminResource.class);
	}

	@Produces
	public RoomService createRoomMock() throws ServiceException {

		RoomService roomMock = Mockito.mock(RoomService.class);

		Room room = new Room();
		room.setId(1L);
		room.setName("Red Room");

		Conference conference = new Conference();
		conference.setId(2L);
		room.setConference(conference);

		Mockito.when(roomMock.createRoom(Mockito.any(Room.class))).thenReturn(room);
		Mockito.when(roomMock.updateRoom(Mockito.any(Room.class))).thenReturn(room);

		Mockito.when(roomMock.findRoomById(2L)).thenReturn(room);
		Mockito.when(roomMock.removeRoom(Mockito.any(Room.class))).thenReturn(room);

		Mockito.when(roomMock.getAllRooms()).thenReturn(Arrays.asList(room));

		return roomMock;
	}


	@Test
	@RunAsClient
	public void createRoom() throws Exception {

		Room room = new Room();
		room.setId(2L);

		Room result = this.roomResource.createRoom(room);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void updateRoom() throws Exception {

		Room room = new Room();
		room.setId(2L);

		Room result = this.roomResource.createRoom(room);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void deleteRoom() throws Exception {

		Room result = this.roomResource.deleteRoom(2L);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void getAllRooms() throws Exception {

		List<Room> rooms = this.roomResource.getAllRooms();

		Assert.assertNotNull(rooms);
		Assert.assertEquals(1, rooms.size());

		Assert.assertNotNull(rooms.get(0));
		Assert.assertNotNull(rooms.get(0).getId());
		Assert.assertEquals(1L, rooms.get(0).getId().longValue());
		Assert.assertEquals("Red Room", rooms.get(0).getName());

		Assert.assertNotNull(rooms.get(0).getConference());
		Assert.assertEquals(2L, rooms.get(0).getConference().getId().longValue());
	}

}
