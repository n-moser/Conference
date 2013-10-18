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

import com.prodyna.pac.conference.ejb.beans.service.ServiceTest;
import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Room;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * RoomServiceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 12.09.13
 * Time: 12:34
 */
@RunWith(Arquillian.class)
public class RoomServiceTest extends ServiceTest {

	@Inject
	private RoomService service;

	@Inject
	private ConferenceService conferenceService;

	private Conference conference;

	@Before
	public void setUp() throws Exception {

		this.conference = new Conference();
		this.conference.setName("JAX");
		this.conference.setLocation("Mainz");
		this.conference.setDescription("Konferenz für die Java-Plattform");
		this.conference.setStartDate(super.parseDate("01.10.2014"));
		this.conference.setEndDate(super.parseDate("05.10.2014"));

		this.conference = conferenceService.createConference(this.conference);
		Assert.assertNotNull(conference);
		Assert.assertNotNull(conference.getId());
	}

	@After
	public void tearDown() throws Exception {

		this.conferenceService.removeConference(conference);
	}

	@Test
	public void createReadUpdateDeleteRoom() throws Exception {

		Room room = new Room();
		room.setCapacity(300);
		room.setConference(conference);
		room.setName("Snow White");

		Room result = service.createRoom(room);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(0L, result.getVersion().longValue());
		Assert.assertEquals("Snow White", result.getName());

		System.out.println("Room ID: " + result.getId());
		System.out.println("Room Version: " + result.getVersion());

		result.setName("Cinderella");
		result = service.updateRoom(result);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(1L, result.getVersion().longValue());
		Assert.assertEquals("Cinderella", result.getName());

		System.out.println("Room ID: " + result.getId());
		System.out.println("Room Version: " + result.getVersion());

		result = service.removeRoom(result);

		Assert.assertNotNull(result);

		result = service.findRoomById(result.getId());

		Assert.assertNull(result);
	}


}
