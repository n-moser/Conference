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

package com.prodyna.pac.conference.ejb.beans.service.talk;

import com.prodyna.pac.conference.ejb.beans.service.ServiceTest;
import com.prodyna.pac.conference.ejb.api.datatype.*;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * TalkServiceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 12.09.13
 * Time: 12:34
 */
@RunWith(Arquillian.class)
public class TalkServiceTest extends ServiceTest {

	@Inject
	private TalkService service;

	@Inject
	private RoomService roomService;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private SpeakerService speakerService;

	private Conference conference;

	private Room room;

	private Speaker adamBien;

	private Speaker larsVogel;

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

		this.room = new Room();
		this.room.setConference(conference);
		this.room.setCapacity(300);
		this.room.setName("Snow White");

		this.room = this.roomService.createRoom(room);
		Assert.assertNotNull(room);
		Assert.assertNotNull(room.getId());

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
	}

	@After
	public void tearDown() throws Exception {

		this.roomService.removeRoom(room);
		this.conferenceService.removeConference(conference);
		this.speakerService.removeSpeaker(adamBien);
		this.speakerService.removeSpeaker(larsVogel);
	}

	@Test
	public void createReadUpdateDeleteTalk() throws Exception {

		Talk talk = new Talk();
		talk.setName("Java EE Webstack Performance");
		talk.setConference(this.conference);
		talk.setRoom(this.room);
		talk.setStartDate(super.parseDate("03.10.2014"));
		talk.setDuration(120);

		TalkSpeaker talkSpeaker = new TalkSpeaker();
		talkSpeaker.setSpeaker(this.adamBien);
		talk.getSpeakers().add(talkSpeaker);

		talkSpeaker = new TalkSpeaker();
		talkSpeaker.setSpeaker(this.larsVogel);
		talk.getSpeakers().add(talkSpeaker);

		Talk result = service.createTalk(talk);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(0L, result.getVersion().longValue());
		Assert.assertEquals("Java EE Webstack Performance", result.getName());

		Assert.assertEquals(2, result.getSpeakers().size());
		Assert.assertNotNull(result.getSpeakers().get(0));
		Assert.assertNotNull(result.getSpeakers().get(0).getId());
		Assert.assertNotNull(result.getSpeakers().get(1));
		Assert.assertNotNull(result.getSpeakers().get(1).getId());

		System.out.println("Talk ID: " + result.getId());
		System.out.println("Talk Version: " + result.getVersion());

		result.setName("Automatisiertes Testen in Android");
		result = service.updateTalk(result);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(1L, result.getVersion().longValue());
		Assert.assertEquals("Automatisiertes Testen in Android", result.getName());

		System.out.println("Talk ID: " + result.getId());
		System.out.println("Talk Version: " + result.getVersion());

		result = service.removeTalk(result);

		Assert.assertNotNull(result);

		result = service.findTalkById(result.getId());

		Assert.assertNull(result);
	}


}
