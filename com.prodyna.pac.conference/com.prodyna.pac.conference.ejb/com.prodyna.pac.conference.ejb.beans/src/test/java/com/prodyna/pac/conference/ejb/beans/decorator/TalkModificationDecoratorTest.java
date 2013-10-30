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

package com.prodyna.pac.conference.ejb.beans.decorator;

import com.prodyna.pac.conference.ejb.api.datatype.*;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.ejb.beans.EJBTest;
import com.prodyna.pac.conference.ejb.beans.event.TalkModificationEvent;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * TalkModificationDecoratorTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 25.10.13
 * Time: 11:57
 */
@RunWith(Arquillian.class)
public class TalkModificationDecoratorTest extends EJBTest {

	@Inject
	private TalkService talkService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private RoomService roomService;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TestTalkModificationEventHandler eventHandler;

	private Conference jax;

	private Conference jfs;

	private Room snowWhite;

	private Room blueRoom;

	private Speaker adamBien;

	private Speaker larsVogel;

	private Talk talk;

	@Before
	public void setUp() throws Exception {

		this.jax = new Conference();
		this.jax.setName("JAX");
		this.jax.setLocation("Mainz");
		this.jax.setDescription("Konferenz für die Java-Plattform");
		this.jax.setStartDate(super.parseDate("01.10.2014"));
		this.jax.setEndDate(super.parseDate("05.10.2014"));

		this.jax = conferenceService.createConference(this.jax);
		Assert.assertNotNull(jax);
		Assert.assertNotNull(jax.getId());

		this.snowWhite = new Room();
		this.snowWhite.setConference(jax);
		this.snowWhite.setCapacity(300);
		this.snowWhite.setName("Snow White");

		this.snowWhite = this.roomService.createRoom(snowWhite);
		Assert.assertNotNull(snowWhite);
		Assert.assertNotNull(snowWhite.getId());

		this.jfs = new Conference();
		this.jfs.setName("Java Forum");
		this.jfs.setLocation("Stuttgart");
		this.jfs.setDescription("Java Forum Stuttgart");
		this.jfs.setStartDate(super.parseDate("02.10.2014"));
		this.jfs.setEndDate(super.parseDate("03.10.2014"));

		this.jfs = conferenceService.createConference(jfs);
		Assert.assertNotNull(this.jfs);
		Assert.assertNotNull(this.jfs.getId());

		this.blueRoom = new Room();
		this.blueRoom.setConference(this.jfs);
		this.blueRoom.setCapacity(200);
		this.blueRoom.setName("Blue Room");

		this.blueRoom = this.roomService.createRoom(this.blueRoom);
		Assert.assertNotNull(this.blueRoom);
		Assert.assertNotNull(this.blueRoom.getId());


		this.adamBien = new Speaker();
		this.adamBien.setName("Adam Bien");
		this.adamBien.setDescription(
				"Adam Bien works with many companies as a Java architecture consultant for enterprise applications, helping organizations design and implement high-performance Java solutions and troubleshooting mission-critical problems. He’s also the author of eight books and more than 100 articles on Java, architectures, and best practices.");

		this.adamBien = this.speakerService.createSpeaker(this.adamBien);
		Assert.assertNotNull(adamBien);
		Assert.assertNotNull(adamBien.getId());

		this.larsVogel = new Speaker();
		this.larsVogel.setName("Lars Vogel");
		this.larsVogel.setDescription(
				"Lars Vogel is the founder and CEO of the vogella GmbH and works as an Eclipse, Git and Android consultant, trainer and book author. He is a regular speaker at international conferences, as for example EclipseCon, Devoxx, OOP, Droidcon and O'Reilly's Android Open and has presented at the Google Headquarters in Mountain View.");

		this.larsVogel = this.speakerService.createSpeaker(this.larsVogel);
		Assert.assertNotNull(larsVogel);
		Assert.assertNotNull(larsVogel.getId());

		this.talk = new Talk();
		this.talk.setName("Java EE Webstack Performance");
		this.talk.setConference(this.jax);
		this.talk.setRoom(this.snowWhite);
		this.talk.setStartDate(super.parseDate("03.10.2014"));
		this.talk.setDuration(120);

		TalkSpeaker talkSpeaker = new TalkSpeaker();
		talkSpeaker.setSpeaker(this.adamBien);
		this.talk.getSpeakers().add(talkSpeaker);

		this.talk = talkService.createTalk(this.talk);

		this.eventHandler.getEventQueue().clear();
	}

	@After
	public void tearDown() throws Exception {

		this.talkService.removeTalk(talk);
		this.roomService.removeRoom(snowWhite);
		this.roomService.removeRoom(blueRoom);
		this.conferenceService.removeConference(jax);
		this.conferenceService.removeConference(jfs);
		this.speakerService.removeSpeaker(adamBien);
		this.speakerService.removeSpeaker(larsVogel);
	}

	@Test
	public void testTalkUpdateWithoutModification() throws Exception {

		Talk newTalk = new Talk();
		newTalk.setId(talk.getId());
		newTalk.setVersion(talk.getVersion());
		newTalk.setName(talk.getName());
		newTalk.setConference(talk.getConference());
		newTalk.setRoom(talk.getRoom());
		newTalk.setStartDate(talk.getStartDate());
		newTalk.setDuration(talk.getDuration());
		newTalk.getSpeakers().addAll(talk.getSpeakers());

		this.talk = talkService.updateTalk(newTalk);

		Thread.sleep(1000l);

		Assert.assertEquals(0, eventHandler.getEventQueue().size());
	}

	@Test
	public void testTalkUpdateWithLittleModification() throws Exception {

		Talk newTalk = new Talk();
		newTalk.setId(talk.getId());
		newTalk.setVersion(talk.getVersion());
		newTalk.setName("haha");
		newTalk.setConference(talk.getConference());
		newTalk.setRoom(talk.getRoom());
		newTalk.setStartDate(talk.getStartDate());
		newTalk.setDuration(talk.getDuration());
		newTalk.getSpeakers().addAll(talk.getSpeakers());

		this.talk = talkService.updateTalk(newTalk);

		Thread.sleep(1000l);

		Assert.assertEquals(1, eventHandler.getEventQueue().size());

		TalkModificationEvent event = eventHandler.getEventQueue().poll();

		System.out.println(event.getChanges());

		Assert.assertNotNull(event.getOldTalk());
		Assert.assertNotNull(event.getNewTalk());

		Assert.assertNotEquals(event.getOldTalk(), event.getNewTalk());

		Assert.assertEquals("Java EE Webstack Performance", event.getOldTalk().getName());
		Assert.assertEquals("haha", event.getNewTalk().getName());
	}

	@Test
	public void testTalkUpdateWithMajorModification() throws Exception {

		Talk newTalk = new Talk();
		newTalk.setId(talk.getId());
		newTalk.setVersion(talk.getVersion());
		newTalk.setName("haha");
		newTalk.setConference(this.jfs);
		newTalk.setRoom(this.blueRoom);
		newTalk.setStartDate(super.parseDate("02.10.2014"));
		newTalk.setDuration(90);
		newTalk.getSpeakers().add(new TalkSpeaker(this.adamBien));

		talkService.updateTalk(newTalk);

		Thread.sleep(1000l);

		Assert.assertEquals(1, eventHandler.getEventQueue().size());

		TalkModificationEvent event = eventHandler.getEventQueue().poll();

		System.out.println(event.getChanges());

		Assert.assertNotNull(event.getOldTalk());
		Assert.assertNotNull(event.getNewTalk());

		Assert.assertNotEquals(event.getOldTalk(), event.getNewTalk());

		Assert.assertEquals("Java EE Webstack Performance", event.getOldTalk().getName());
		Assert.assertEquals("haha", event.getNewTalk().getName());
	}
}
