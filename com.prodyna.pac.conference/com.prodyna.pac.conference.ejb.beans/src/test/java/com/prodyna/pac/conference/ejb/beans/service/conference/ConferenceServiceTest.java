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

package com.prodyna.pac.conference.ejb.beans.service.conference;

import com.prodyna.pac.conference.ejb.beans.service.ServiceTest;
import com.prodyna.pac.conference.ejb.facade.datatype.Conference;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * ConferenceServiceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 12.09.13
 * Time: 12:34
 */
@RunWith(Arquillian.class)
public class ConferenceServiceTest extends ServiceTest {

	@Inject
	private ConferenceService service;

	@Test
	public void createReadUpdateDeleteConference() throws Exception {

		Conference conference = new Conference();
		conference.setName("JAX");
		conference.setDescription("Konferenz für die Java-Plattform");
		conference.setStartDate(super.parseDate("01.10.2013"));
		conference.setEndDate(super.parseDate("05.10.2013"));

		Conference result = service.createConference(conference);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(0L, result.getVersion().longValue());
		Assert.assertEquals("JAX", result.getName());

		System.out.println("Conference ID: " + result.getId());
		System.out.print("Conference Version: " + result.getVersion());

		result.setName("W-JAX");
		result = service.updateConference(result);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertNotNull(result.getVersion());
		Assert.assertEquals(1L, result.getVersion().longValue());
		Assert.assertEquals("W-JAX", result.getName());

		System.out.println("Conference ID: " + result.getId());
		System.out.print("Conference Version: " + result.getVersion());

		result = service.removeConference(result);

		Assert.assertNotNull(result);

		result = service.findConferenceById(result.getId());

		Assert.assertNull(result);
	}


}
