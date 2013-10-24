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
import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Arrays;

/**
 * ConferenceBeanTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 24.10.13
 * Time: 08:34
 */
@RunWith(Arquillian.class)
public class SpeakerBeanTest extends JSFTest {

	@Inject
	private SpeakerBean speakerBean;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Deployment
	public static Archive<?> createTestArchive() throws Exception {

		return JSFTest.createTestArchive(SpeakerBean.class, SpeakerBeanTest.class);
	}

	@Produces
	public TalkService createTalkMock() throws ServiceException {

		TalkService talkMock = Mockito.mock(TalkService.class);

		Talk talk = new Talk();
		talk.setId(1L);
		talk.setName("Testing Android");
		talk.setStartDate(super.parseDateTime("01.01.2014 10:00:00"));

		Conference conference = new Conference();
		conference.setStartDate(super.parseDate("01.01.2014"));
		conference.setEndDate(super.parseDate("02.01.2014"));
		talk.setConference(conference);

		Mockito.when(talkMock.getTalksBySpeaker(Mockito.any(Speaker.class))).thenReturn(Arrays.asList(talk));

		return talkMock;
	}

	@Test
	public void setSpeaker() throws Exception {

		Speaker speaker = new Speaker();

		speakerBean.setSpeaker(speaker);

		Assert.assertNotNull(speakerBean.getSpeaker());

		Assert.assertNotNull(speakerBean.getConferences());
		Assert.assertEquals(1, speakerBean.getConferences().size());

		Assert.assertNotNull(speakerBean.getTalks());
		Assert.assertEquals(1, speakerBean.getTalks().length);

		Assert.assertNotNull(speakerBean.getTalks()[0]);
		Assert.assertEquals(1, speakerBean.getTalks()[0].size());

		Assert.assertNotNull(breadCrumpBean.getSpeaker());
	}

	@Test
	public void setSpeakerNull() throws Exception {

		speakerBean.setSpeaker(null);

		Assert.assertNotNull(speakerBean.getSpeaker());
	}

}
