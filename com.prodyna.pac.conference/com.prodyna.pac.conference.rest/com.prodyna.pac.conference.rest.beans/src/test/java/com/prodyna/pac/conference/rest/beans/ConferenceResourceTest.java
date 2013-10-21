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
import com.prodyna.pac.conference.rest.api.ConferenceResource;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.io.File;
import java.io.IOException;
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

	private ConferenceResource conferenceResource;

	private ConferenceService conferenceMock;

	private static Logger logger = LoggerFactory.getLogger(ConferenceResourceTest.class);

	@Deployment
	public static Archive<?> createTestArchive() throws IOException {

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


		WebArchive war = ShrinkWrap.create(WebArchive.class, "conference.war");
		war.addPackages(true, "com.prodyna.pac.conference");
		war.addAsLibraries(new File(arquillianLibFolder, "mockito-all-1.9.5.jar"));
		war.addAsWebInfResource("META-INF/test-beans.xml", "beans.xml");
		war.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		war.addAsResource("META-INF/test-namedQueries.xml", "META-INF/namedQueries.xml");
		war.setWebXML("WEB-INF/test-web.xml");

		war.as(ZipExporter.class).exportTo(tempFile, true);

		return war;
	}

	@Before
	public void setUp() {

		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		factory.addMessageBodyReader(new JacksonJsonProvider());

		RegisterBuiltin.register(factory);

		this.conferenceResource = ProxyFactory.create(ConferenceResource.class, "http://localhost:8080/conference/");
	}

	@Produces
	public ConferenceService createConferenceMock() throws ServiceException {

		if (conferenceMock == null) {
			this.conferenceMock = Mockito.mock(ConferenceService.class);

			Conference conference = new Conference();
			conference.setId(1L);
			conference.setName("Sample");

			Mockito.when(this.conferenceMock.getAllConferences()).thenReturn(Arrays.asList(conference));
		}

		return conferenceMock;
	}

	@Test
	@RunAsClient
	public void createConference() throws Exception {

		List<Conference> conferences = this.conferenceResource.getAllConferences();

		Assert.assertNotNull(conferences);
		Assert.assertEquals(1, conferences.size());

		Assert.assertNotNull(conferences.get(0));
		Assert.assertNotNull(conferences.get(0).getId());
		Assert.assertEquals("Sample", conferences.get(0).getName());

	}

}
