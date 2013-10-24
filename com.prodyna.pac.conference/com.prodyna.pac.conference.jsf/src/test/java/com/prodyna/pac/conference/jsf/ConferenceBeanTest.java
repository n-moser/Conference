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
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;

/**
 * ConferenceBeanTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 24.10.13
 * Time: 08:34
 */
@RunWith(Arquillian.class)
public class ConferenceBeanTest extends JSFTest {

	@Inject
	private ConferenceBean conferenceBean;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Deployment
	public static Archive<?> createTestArchive() throws Exception {

		// Workaround for Arquillian, since no Gradle Resolver is available.
		File tempFile = new File("build/arquillian/latest-rest.war");
		File arquillianFolder = tempFile.getParentFile();
		File arquillianLibFolder = new File(arquillianFolder, "lib");
		if (!tempFile.exists()) {
			if (!arquillianFolder.exists()) {
				arquillianFolder.mkdirs();
			}
			tempFile.createNewFile();
		}

		File mockitoLib = new File(arquillianLibFolder, "mockito-all-1.9.5.jar");
		if (!mockitoLib.exists()) {
			throw new IllegalStateException("Mockito Lib is not deployed!");
		}

		WebArchive war = ShrinkWrap.create(WebArchive.class, "conference.war");
		war.addPackages(true, "com.prodyna.pac.conference.ejb");
		war.addClass(ConferenceBean.class);
		war.addClass(BreadCrumpBean.class);
		war.addClass(ConferenceBeanTest.class);
		war.addClass(JSFTest.class);
		war.addAsLibraries(mockitoLib);
		war.addAsWebInfResource("META-INF/test-beans.xml", "beans.xml");
		war.addAsWebInfResource("WEB-INF/test-faces-config.xml", "faces-config.xml");
		war.setWebXML("WEB-INF/test-web.xml");

		war.as(ZipExporter.class).exportTo(tempFile, true);

		war.toString(true);

		return war;
	}

	@Produces
	public Logger produceLogger(InjectionPoint ip) {

		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}

	@Produces
	public ConferenceService createConferenceMock() throws ServiceException {

		ConferenceService conferenceMock = Mockito.mock(ConferenceService.class);

		Conference conference = new Conference();
		conference.setId(1L);
		conference.setName("JAX");

		Mockito.when(conferenceMock.createConference(Mockito.any(Conference.class))).thenReturn(conference);
		Mockito.when(conferenceMock.updateConference(Mockito.any(Conference.class))).thenReturn(conference);

		Mockito.when(conferenceMock.findConferenceById(2L)).thenReturn(conference);
		Mockito.when(conferenceMock.removeConference(Mockito.any(Conference.class))).thenReturn(conference);

		Mockito.when(conferenceMock.getAllConferences()).thenReturn(Arrays.asList(conference));

		return conferenceMock;
	}

	@Produces
	public TalkService createTalkMock() throws ServiceException {

		TalkService talkMock = Mockito.mock(TalkService.class);

		Talk talk = new Talk();
		talk.setId(1L);
		talk.setName("Testing Android");
		talk.setStartDate(super.parseDateTime("01.01.2014 10:00:00"));

		Conference conference = new Conference();
		conference.setId(2L);
		talk.setConference(conference);

		Mockito.when(talkMock.createTalk(Mockito.any(Talk.class))).thenReturn(talk);
		Mockito.when(talkMock.updateTalk(Mockito.any(Talk.class))).thenReturn(talk);

		Mockito.when(talkMock.findTalkById(2L)).thenReturn(talk);
		Mockito.when(talkMock.removeTalk(Mockito.any(Talk.class))).thenReturn(talk);

		Mockito.when(talkMock.getAllTalks()).thenReturn(Arrays.asList(talk));
		Mockito.when(talkMock.getTalksByConference(Mockito.any(Conference.class))).thenReturn(Arrays.asList(talk));

		return talkMock;
	}

	@Test
	public void setConference() throws Exception {

		Conference conference = new Conference();
		conference.setStartDate(super.parseDate("01.01.2014"));
		conference.setEndDate(super.parseDate("02.01.2014"));

		conferenceBean.setConference(conference);

		Assert.assertEquals(2, conferenceBean.getDates().size());

		Assert.assertNotNull(conferenceBean.getConference());
		Assert.assertEquals(2, conferenceBean.getTalks().length);

		Assert.assertNotNull(conferenceBean.getTalks()[0]);
		Assert.assertEquals(1, conferenceBean.getTalks()[0].size());
		Assert.assertNotNull(conferenceBean.getTalks()[1]);
		Assert.assertEquals(0, conferenceBean.getTalks()[1].size());

		Assert.assertNotNull(breadCrumpBean.getConference());
	}

	@Test
	public void setConferenceNull() throws Exception {

		conferenceBean.setConference(null);

		Assert.assertNotNull(conferenceBean.getConference());
	}

}
