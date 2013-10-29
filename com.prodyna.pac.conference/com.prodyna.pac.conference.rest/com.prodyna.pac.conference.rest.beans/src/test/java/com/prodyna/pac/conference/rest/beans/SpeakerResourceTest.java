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


import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.rest.api.resource.admin.SpeakerAdminResource;
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
 * SpeakerResourceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.10.13
 * Time: 15:26
 */
@RunWith(Arquillian.class)
public class SpeakerResourceTest extends ResourceTest {

	private SpeakerAdminResource speakerResource;

	@Before
	public void setUp() {

		super.setUp();

		this.speakerResource = super.getResource(SpeakerAdminResource.class);
	}

	@Produces
	public SpeakerService createSpeakerMock() throws ServiceException {

		SpeakerService speakerMock = Mockito.mock(SpeakerService.class);

		Speaker speaker = new Speaker();
		speaker.setId(1L);
		speaker.setName("Mr Bean");

		Mockito.when(speakerMock.createSpeaker(Mockito.any(Speaker.class))).thenReturn(speaker);
		Mockito.when(speakerMock.updateSpeaker(Mockito.any(Speaker.class))).thenReturn(speaker);

		Mockito.when(speakerMock.findSpeakerById(2L)).thenReturn(speaker);
		Mockito.when(speakerMock.removeSpeaker(Mockito.any(Speaker.class))).thenReturn(speaker);

		Mockito.when(speakerMock.getAllSpeakers()).thenReturn(Arrays.asList(speaker));

		return speakerMock;
	}


	@Test
	@RunAsClient
	public void createSpeaker() throws Exception {

		Speaker speaker = new Speaker();
		speaker.setId(2L);

		Speaker result = this.speakerResource.createSpeaker(speaker);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void updateSpeaker() throws Exception {

		Speaker speaker = new Speaker();
		speaker.setId(2L);

		Speaker result = this.speakerResource.createSpeaker(speaker);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void deleteSpeaker() throws Exception {

		Speaker result = this.speakerResource.deleteSpeaker(2L);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void getAllSpeakers() throws Exception {

		List<Speaker> speakers = this.speakerResource.getAllSpeakers();

		Assert.assertNotNull(speakers);
		Assert.assertEquals(1, speakers.size());

		Assert.assertNotNull(speakers.get(0));
		Assert.assertNotNull(speakers.get(0).getId());
		Assert.assertEquals(1L, speakers.get(0).getId().longValue());
		Assert.assertEquals("Mr Bean", speakers.get(0).getName());
	}

}
