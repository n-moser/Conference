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

package com.prodyna.pac.conference.ejb.beans.mbean;

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.beans.mbean.performance.PerformanceEntry;
import com.prodyna.pac.conference.ejb.beans.mbean.performance.PerformanceMBean;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 * PerformanceMBeanTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 30.10.13
 * Time: 09:20
 */
@RunWith(Arquillian.class)
public class PerformanceMBeanTest {

	@Inject
	private SpeakerService service;

	@Inject
	@MBeanProxy
	private PerformanceMBean mBean;

	@Before
	public void setUp() throws Exception {

		Assert.assertNotNull(mBean);
		this.mBean.reset();

		// Sample Invocation

		Speaker speaker = new Speaker();
		speaker.setName("Adam Bien");

		Speaker result = service.createSpeaker(speaker);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());

		result.setName("Jens Vogel");
		result = service.updateSpeaker(result);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());

		result = service.removeSpeaker(result);

		Assert.assertNotNull(result);

		result = service.findSpeakerById(result.getId());

		Assert.assertNull(result);
	}

	@Test
	public void getCount() throws Exception {

		Assert.assertEquals(4, this.mBean.getCount());
	}

	@Test
	public void report() throws Exception {

		mBean.report("Sample", "doSome", 10);
		Assert.assertEquals(5, this.mBean.getCount());
	}

	@Test
	public void reset() throws Exception {

		mBean.reset();
		Assert.assertEquals(0, this.mBean.getCount());
	}

	@Test
	public void getAll() throws Exception {

		List<PerformanceEntry> entries = mBean.getAll();
		Assert.assertNotNull(entries);

		Assert.assertEquals(4, entries.size());

		Assert.assertNotNull(mBean.getAllAsString());
	}

	@Test
	public void getWorst() throws Exception {

		Assert.assertNotNull(this.mBean.getWorstByTime());
		Assert.assertNotNull(this.mBean.getWorstByCount());
		Assert.assertNotNull(this.mBean.getWorstByAverage());
	}
}
