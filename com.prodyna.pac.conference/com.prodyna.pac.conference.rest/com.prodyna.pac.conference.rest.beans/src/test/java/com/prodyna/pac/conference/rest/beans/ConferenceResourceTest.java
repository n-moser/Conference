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
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.conference.ConferenceService;
import com.prodyna.pac.conference.rest.api.resource.admin.ConferenceAdminResource;
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
 * ConferenceResourceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.10.13
 * Time: 15:26
 */
@RunWith(Arquillian.class)
public class ConferenceResourceTest extends ResourceTest {

	private ConferenceAdminResource conferenceResource;

	@Before
	public void setUp() {

		super.setUp();

		this.conferenceResource = super.getResource(ConferenceAdminResource.class);
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

	@Test
	@RunAsClient
	public void createConference() throws Exception {

		Conference conference = new Conference();
		conference.setId(2L);

		Conference result = this.conferenceResource.createConference(conference);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void updateConference() throws Exception {

		Conference conference = new Conference();
		conference.setId(2L);

		Conference result = this.conferenceResource.createConference(conference);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void deleteConference() throws Exception {

		Conference result = this.conferenceResource.deleteConference(2L);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void getAllConferences() throws Exception {

		List<Conference> conferences = this.conferenceResource.getAllConferences();

		Assert.assertNotNull(conferences);
		Assert.assertEquals(1, conferences.size());

		Assert.assertNotNull(conferences.get(0));
		Assert.assertNotNull(conferences.get(0).getId());
		Assert.assertEquals(1L, conferences.get(0).getId().longValue());
		Assert.assertEquals("JAX", conferences.get(0).getName());
	}

}
