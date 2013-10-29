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

import com.prodyna.pac.conference.rest.api.resource.Resource;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ResourceTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 12.09.13
 * Time: 12:37
 */
public abstract class ResourceTest {

	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	/** Setup the test environment. */
	protected void setUp() {

		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		factory.addMessageBodyReader(new JacksonJsonProvider());

		RegisterBuiltin.register(factory);
	}

	/**
	 * Retrieve the given resource from resteasy proxy factory.
	 *
	 * @param resourceClass
	 * 		the resource class
	 * @param <R>
	 * 		the resource type
	 *
	 * @return the resource instance
	 */
	protected <R extends Resource> R getResource(Class<R> resourceClass) {

		return ProxyFactory.create(resourceClass, "http://localhost:8080/conference/");
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
	protected Date parseDate(String date) throws ParseException {

		return FORMAT.parse(date);
	}

	/**
	 * Prints the date to the given format 'dd.MM.yyyy'.
	 *
	 * @param date
	 * 		the date instance
	 *
	 * @return the date as string
	 */
	protected String format(Date date) {

		return FORMAT.format(date);
	}

}
