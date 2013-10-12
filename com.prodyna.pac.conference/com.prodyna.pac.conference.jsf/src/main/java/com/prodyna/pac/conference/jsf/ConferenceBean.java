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

import com.prodyna.pac.conference.ejb.facade.datatype.Conference;
import com.prodyna.pac.conference.ejb.facade.datatype.Talk;
import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.service.conference.ConferenceService;
import com.prodyna.pac.conference.ejb.facade.service.talk.TalkService;
import com.prodyna.pac.conference.ejb.facade.util.DateIterator;
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
 * ConferenceBean
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
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

	private Conference conference;

	private List<Date> dates = new ArrayList<Date>();

	private List<Talk>[] talks;

	@Inject
	private Logger logger;

	public Conference getConference() {

		if (conference == null) {
			conference = new Conference();
		}
		return conference;
	}

	public void setConference(Conference conference) {

		if (conference == null) {
			logger.error("Cannot set Conference 'null'.");
			this.conference = new Conference();
		} else {
			this.conference = conference;
		}

		this.dates.clear();

		DateIterator dateIterator = new DateIterator(conference.getStartDate(), conference.getEndDate());

		while (dateIterator.hasNext()) {
			Date date = dateIterator.next();
			this.dates.add(date);
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
					talkDate.setTime(talk.getDate());

					if (date.get(Calendar.DAY_OF_YEAR) == talkDate.get(Calendar.DAY_OF_YEAR)) {
						this.talks[i].add(talk);
					}
				}
			}

		} catch (ServiceException e) {
			logger.error("Cannot load talks for conference {}", conference.getName(), e);
		}

	}

	public List<Date> getDates() {

		return this.dates;
	}

	public List<Talk>[] getTalks() {

		return this.talks;
	}

	public String open(Long conferenceId) throws ServiceException {

		if (conferenceId == null) {
			logger.error("No Conference ID submitted!");
		} else {
			Conference conference = this.conferenceService.findConferenceById(conferenceId);
			this.setConference(conference);
		}

		return "conference";
	}
}
