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
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Managed Bean responsible for displaying a single talk.
 * <p/>
 * Author: Nicolas Moser
 * Date: 14.10.13
 * Time: 12:50
 */
@ManagedBean
@RequestScoped
@Named("talkBean")
public class TalkBean {

	private Talk talk;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Inject
	private Logger logger;

	/**
	 * Getter for the displayed talk entity.
	 *
	 * @return the talk entity
	 */
	public Talk getTalk() {

		return talk;
	}

	/**
	 * Setter for the displayed talk entity.
	 *
	 * @param talk
	 * 		the talk entity
	 */
	public void setTalk(Talk talk) {

		if (talk == null) {
			logger.error("Cannot set Talk 'null'.");
			this.talk = new Talk();
		} else {
			this.talk = talk;
		}

		initBreadCrump();
	}

	/** Initialize the Breadcrump for showing the selected conference. */
	private void initBreadCrump() {

		this.breadCrumpBean.setTalk(talk);
		this.breadCrumpBean.setRoom(null);
		this.breadCrumpBean.setSpeaker(null);
	}
}
