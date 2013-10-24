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

import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * ConferenceBeanTest
 * <p/>
 * Author: Nicolas Moser
 * Date: 24.10.13
 * Time: 08:34
 */
@RunWith(Arquillian.class)
public class TalkBeanTest extends JSFTest {

	@Inject
	private TalkBean talkBean;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Deployment
	public static Archive<?> createTestArchive() throws Exception {

		return JSFTest.createTestArchive(TalkBean.class, TalkBeanTest.class);
	}

	@Test
	public void setTalk() throws Exception {

		talkBean.setTalk(new Talk());

		Assert.assertNotNull(breadCrumpBean.getTalk());
	}

	@Test
	public void setTalkNull() throws Exception {

		talkBean.setTalk(null);

		Assert.assertNotNull(talkBean.getTalk());
	}

}
