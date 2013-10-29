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
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.rest.api.resource.admin.TalkAdminResource;
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
 * TalkResourceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.10.13
 * Time: 15:26
 */
@RunWith(Arquillian.class)
public class TalkResourceTest extends ResourceTest {

	private TalkAdminResource talkResource;

	@Before
	public void setUp() {

		super.setUp();

		this.talkResource = super.getResource(TalkAdminResource.class);
	}

	@Produces
	public TalkService createTalkMock() throws ServiceException {

		TalkService talkMock = Mockito.mock(TalkService.class);

		Talk talk = new Talk();
		talk.setId(1L);
		talk.setName("Testing Android");

		Conference conference = new Conference();
		conference.setId(2L);
		talk.setConference(conference);

		Mockito.when(talkMock.createTalk(Mockito.any(Talk.class))).thenReturn(talk);
		Mockito.when(talkMock.updateTalk(Mockito.any(Talk.class))).thenReturn(talk);

		Mockito.when(talkMock.findTalkById(2L)).thenReturn(talk);
		Mockito.when(talkMock.removeTalk(Mockito.any(Talk.class))).thenReturn(talk);

		Mockito.when(talkMock.getAllTalks()).thenReturn(Arrays.asList(talk));

		return talkMock;
	}


	@Test
	@RunAsClient
	public void createTalk() throws Exception {

		Talk talk = new Talk();
		talk.setId(2L);

		Talk result = this.talkResource.createTalk(talk);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void updateTalk() throws Exception {

		Talk talk = new Talk();
		talk.setId(2L);

		Talk result = this.talkResource.createTalk(talk);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void deleteTalk() throws Exception {

		Talk result = this.talkResource.deleteTalk(2L);

		Assert.assertNotNull(result);
		Assert.assertEquals(1L, result.getId().longValue());
	}

	@Test
	@RunAsClient
	public void getAllTalks() throws Exception {

		List<Talk> talks = this.talkResource.getAllTalks();

		Assert.assertNotNull(talks);
		Assert.assertEquals(1, talks.size());

		Assert.assertNotNull(talks.get(0));
		Assert.assertNotNull(talks.get(0).getId());
		Assert.assertEquals(1L, talks.get(0).getId().longValue());
		Assert.assertEquals("Testing Android", talks.get(0).getName());

		Assert.assertNotNull(talks.get(0).getConference());
		Assert.assertEquals(2L, talks.get(0).getConference().getId().longValue());
	}

}
