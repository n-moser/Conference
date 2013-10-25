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

package com.prodyna.pac.conference.ejb.api.datatype;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TalkEqualsTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 25.10.13
 * Time: 15:29
 */
public class TalkEqualsTest {

	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");


	@Test
	public void testTalkEqualty() throws Exception {

		Conference conference = new Conference();
		conference.setName("JAX");
		conference.setLocation("Mainz");
		conference.setDescription("Konferenz für die Java-Plattform");
		conference.setStartDate(parseDate("01.10.2014"));
		conference.setEndDate(parseDate("05.10.2014"));

		Room room = new Room();
		room.setConference(conference);
		room.setCapacity(300);
		room.setName("Snow White");

		Speaker adamBien = new Speaker();
		adamBien.setName("Adam Bien");
		adamBien.setDescription(
				"Adam Bien works with many companies as a Java architecture consultant for enterprise applications, helping organizations design and implement high-performance Java solutions and troubleshooting mission-critical problems. He’s also the author of eight books and more than 100 articles on Java, architectures, and best practices.");

		Speaker larsVogel = new Speaker();
		larsVogel.setName("Lars Vogel");
		larsVogel.setDescription(
				"Lars Vogel is the founder and CEO of the vogella GmbH and works as an Eclipse, Git and Android consultant, trainer and book author. He is a regular speaker at international conferences, as for example EclipseCon, Devoxx, OOP, Droidcon and O'Reilly's Android Open and has presented at the Google Headquarters in Mountain View.");

		Talk talk = new Talk();
		talk.setName("Java EE Webstack Performance");
		talk.setConference(conference);
		talk.setRoom(room);
		talk.setStartDate(parseDate("03.10.2014"));
		talk.setDuration(120);

		TalkSpeaker talkSpeaker = new TalkSpeaker();
		talkSpeaker.setSpeaker(adamBien);
		talk.getSpeakers().add(talkSpeaker);

		Talk newTalk = new Talk();
		newTalk.setId(talk.getId());
		newTalk.setVersion(talk.getVersion());
		newTalk.setName(talk.getName());
		newTalk.setConference(talk.getConference());
		newTalk.setRoom(talk.getRoom());
		newTalk.setStartDate(talk.getStartDate());
		newTalk.setDuration(talk.getDuration());
		newTalk.getSpeakers().addAll(talk.getSpeakers());

		Assert.assertEquals(talk, newTalk);
	}

	/**
	 * Parse the given date string in the pattern 'dd.MM.yyyy' as Date.
	 *
	 * @param date
	 * 		the date as string
	 *
	 * @return the date instance
	 *
	 * @throws java.text.ParseException
	 * 		when the date is not in the given format
	 */
	private Date parseDate(String date) throws ParseException {

		return FORMAT.parse(date);
	}
}
