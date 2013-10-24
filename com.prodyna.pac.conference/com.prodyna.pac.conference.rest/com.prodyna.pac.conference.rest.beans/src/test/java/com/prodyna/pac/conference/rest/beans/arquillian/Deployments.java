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

package com.prodyna.pac.conference.rest.beans.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Deployments
 * <p/>
 * Author: Nicolas Moser
 * Date: 22.10.13
 * Time: 10:26
 */
public class Deployments {

	private static Logger logger = LoggerFactory.getLogger(Deployments.class);

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

		File mockitoLib = new File(arquillianLibFolder, "mockito-all-1.9.5.jar");
		if (!mockitoLib.exists()) {
			throw new IllegalStateException("Mockito Lib is not deployed!");
		}

		WebArchive war = ShrinkWrap.create(WebArchive.class, "conference.war");
		war.addPackages(true, "com.prodyna.pac.conference");
		war.addAsLibraries(mockitoLib);
		war.addAsWebInfResource("META-INF/test-beans.xml", "beans.xml");
		war.setWebXML("WEB-INF/test-web.xml");

		war.as(ZipExporter.class).exportTo(tempFile, true);

		war.toString(true);

		return war;
	}
}
