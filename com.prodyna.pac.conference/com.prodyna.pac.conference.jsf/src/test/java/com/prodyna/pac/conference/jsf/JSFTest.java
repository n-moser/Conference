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

import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import com.prodyna.pac.conference.jsf.util.LoggerProducer;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
public abstract class JSFTest {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

	private static Logger logger = LoggerFactory.getLogger(JSFTest.class);

	/**
	 * Creates the default JSF test archive and adds the given classes.
	 *
	 * @param classes
	 * 		additional classes to add into the archive
	 *
	 * @return the archive
	 *
	 * @throws Exception when the archive cannot be created
	 */
	protected static Archive<?> createTestArchive(Class<?>... classes) throws Exception {

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
		war.addClass(BreadCrumpBean.class);
		war.addClasses(LoggerProducer.class);
		war.addClass(JSFTest.class);
		war.addClasses(classes);
		war.addAsLibraries(mockitoLib);
		war.addAsWebInfResource("META-INF/test-beans.xml", "beans.xml");
		war.addAsWebInfResource("WEB-INF/test-faces-config.xml", "faces-config.xml");
		war.setWebXML("WEB-INF/test-web.xml");

		war.as(ZipExporter.class).exportTo(tempFile, true);

		war.toString(true);

		return war;
	}

	/**
	 * Parse the given date string in the pattern 'dd.MM.yyyy' as Date.
	 *
	 * @param date
	 * 		the date as string
	 *
	 * @return the date instance
	 */
	protected Date parseDate(String date) {

		try {
			return DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			logger.error("Cannot parse date '{}'.", date);
			return null;
		}
	}

	/**
	 * Parse the given date string in the pattern 'dd.MM.yyyy hh:mm:ss' as Date.
	 *
	 * @param dateTime
	 * 		the date as string
	 *
	 * @return the date instance
	 */
	protected Date parseDateTime(String dateTime) {

		try {
			return DATETIME_FORMAT.parse(dateTime);
		} catch (ParseException e) {
			logger.error("Cannot parse date '{}'.", dateTime);
			return null;
		}
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

		return DATE_FORMAT.format(date);
	}

}
