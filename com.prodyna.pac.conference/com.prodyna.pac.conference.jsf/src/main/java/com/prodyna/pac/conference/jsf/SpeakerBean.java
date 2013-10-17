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
import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.jsf.breadcrump.BreadCrumpBean;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * SpeakerBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 14.10.13
 * Time: 12:50
 */
@ManagedBean
@SessionScoped
@Named("speakerBean")
public class SpeakerBean implements Serializable {

	@Inject
	private SpeakerService speakerService;

	@Inject
	private TalkService talkService;

	private Speaker speaker;

	private List<Conference> conferences = new ArrayList<Conference>();

	private List<Talk>[] talks;

	@Inject
	private BreadCrumpBean breadCrumpBean;

	@Inject
	private Logger logger;

	public Speaker getSpeaker() {

		return speaker;
	}

	public void setSpeaker(Speaker speaker) {

		if (speaker == null) {
			logger.error("Cannot set Speaker 'null'.");
			this.speaker = new Speaker();
		} else {
			this.speaker = speaker;
		}

		try {
			this.conferences.clear();

			List<Talk> talks = talkService.getTalksBySpeaker(speaker);

			Map<Conference, List<Talk>> talkMap = new LinkedHashMap<Conference, List<Talk>>();

			for (Talk talk : talks) {

				Conference conference = talk.getConference();

				List<Talk> conferenceTalks = talkMap.get(conference);
				if (conferenceTalks == null) {
					conferenceTalks = new ArrayList<Talk>();
					talkMap.put(conference, conferenceTalks);
				}

				conferenceTalks.add(talk);
			}

			this.conferences.addAll(talkMap.keySet());
			this.talks = talkMap.values().toArray(new List[talkMap.size()]);

		} catch (ServiceException e) {
			logger.error("Cannot load talks for speaker {}", speaker.getName(), e);
		}

	}

	public List<Conference> getConferences() {

		return conferences;
	}

	public List<Talk>[] getTalks() {

		return talks;
	}

	public String open(Long speakerId) throws ServiceException {

		if (speakerId == null) {
			logger.error("No Speaker ID submitted!");
		} else {
			Speaker speaker = this.speakerService.findSpeakerById(speakerId);
			this.setSpeaker(speaker);

			this.breadCrumpBean.setSpeaker(speaker);
		}

		return "speaker";
	}
}
