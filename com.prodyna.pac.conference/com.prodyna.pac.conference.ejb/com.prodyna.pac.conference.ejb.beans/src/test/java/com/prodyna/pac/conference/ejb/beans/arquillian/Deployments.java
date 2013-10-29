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

package com.prodyna.pac.conference.ejb.beans.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Deployments
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.09.13
 * Time: 09:10
 */
public class Deployments {

	private static Logger logger = LoggerFactory.getLogger(Deployments.class);

	@Deployment
	public static Archive<?> createTestArchive() throws IOException {

		WebArchive war = ShrinkWrap.create(WebArchive.class, "conference.war");

		// Workaround since WAR archives does not apply Decorators.
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "conference.jar");
		jar.addPackages(true, "com.prodyna.pac.conference");
		jar.addAsManifestResource("META-INF/test-beans.xml", "beans.xml");
		jar.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		jar.addAsResource("META-INF/namedQueries.xml", "META-INF/namedQueries.xml");

		war.addAsLibraries(jar);

		// *-hornetq-jms.xml files cannot be placed inside JAR files.
		war.addAsWebInfResource("talk-hornetq-jms.xml", "talk-hornetq-jms.xml");

		logger.info(war.toString(true));
		logger.info(jar.toString(true));

		File tempFile = new File("build/arquillian/latest-ejb.war");
		if (!tempFile.exists()) {
			File arquillianFolder = tempFile.getParentFile();
			if (!arquillianFolder.exists()) {
				arquillianFolder.mkdirs();
			}
			tempFile.createNewFile();
		}
		war.as(ZipExporter.class).exportTo(tempFile, true);

		return war;
	}
}
