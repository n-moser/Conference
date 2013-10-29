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

import com.prodyna.pac.conference.ejb.api.datatype.Conference;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.ejb.api.datatype.util.DateIterator;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Managed Bean responsible for displaying a single conference.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.10.13
 * Time: 17:48
 */
@ManagedBean
@RequestScoped
@Named("conferenceBean")
public class ConferenceBean {

	@Inject
	private TalkService talkService;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Inject
	private Logger logger;

	private Conference conference;

	private List<Date> dates = new ArrayList<Date>();

	private List<Talk>[] talks;

	/**
	 * Getter for the list of dates.
	 *
	 * @return the list of dates
	 */
	public List<Date> getDates() {

		return this.dates;
	}

	/**
	 * Getter for the list of talks.
	 *
	 * @return the list of talks
	 */
	public List<Talk>[] getTalks() {

		return this.talks;
	}

	/**
	 * Getter for the conference entity.
	 *
	 * @return the conference instance
	 */
	public Conference getConference() {

		return conference;
	}

	/**
	 * Setter for the conference instance.
	 *
	 * @param conference
	 * 		the conference to set
	 */
	public void setConference(Conference conference) {

		if (conference == null) {
			logger.error("Cannot set Conference 'null'.");
			this.conference = new Conference();
		} else {
			this.conference = conference;
		}

		this.loadDates();
		this.initBreadCrump();
	}

	/** Load the dates for the current conference. */
	@SuppressWarnings("unchecked")
	private void loadDates() {

		this.dates.clear();

		if (conference.getStartDate() != null && conference.getEndDate() != null) {

			DateIterator dateIterator = new DateIterator(conference.getStartDate(), conference.getEndDate());

			while (dateIterator.hasNext()) {
				Date date = dateIterator.next();
				this.dates.add(date);
			}
		}

		try {
			this.talks = new List[dates.size()];

			List<Talk> talks = talkService.getTalksByConference(conference);

			for (int i = 0; i < this.dates.size(); i++) {
				Calendar date = Calendar.getInstance();
				date.setTime(this.dates.get(i));
				this.talks[i] = new ArrayList<Talk>();

				for (Talk talk : talks) {
					Calendar talkDate = Calendar.getInstance();
					talkDate.setTime(talk.getStartDate());

					if (date.get(Calendar.DAY_OF_YEAR) == talkDate.get(Calendar.DAY_OF_YEAR)) {
						this.talks[i].add(talk);
					}
				}
			}

		} catch (ServiceException e) {
			logger.error("Cannot load talks for conference {}", conference.getName(), e);
		}
	}

	/** Initialize the Breadcrump for showing the selected conference. */
	private void initBreadCrump() {

		this.breadCrumpBean.setConference(conference);
		this.breadCrumpBean.setTalk(null);
		this.breadCrumpBean.setRoom(null);
		this.breadCrumpBean.setSpeaker(null);
	}
}
