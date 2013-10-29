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

package com.prodyna.pac.conference.rest.beans.resource.admin;

import com.prodyna.pac.conference.ejb.api.datatype.Speaker;
import com.prodyna.pac.conference.rest.api.exception.RESTException;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.rest.api.resource.admin.SpeakerAdminResource;
import com.prodyna.pac.conference.rest.beans.resource.SpeakerResourceBean;

import javax.inject.Inject;

/**
 * SpeakerResourceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 19.09.13
 * Time: 18:28
 */
public class SpeakerAdminResourceBean extends SpeakerResourceBean implements SpeakerAdminResource {

	@Inject
	private SpeakerService speakerService;

	@Override
	public Speaker createSpeaker(Speaker speaker) throws RESTException {

		try {
			return this.speakerService.createSpeaker(speaker);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	public Speaker updateSpeaker(Speaker speaker) throws RESTException {

		try {
			return this.speakerService.updateSpeaker(speaker);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	public Speaker deleteSpeaker(Long id) throws RESTException {

		try {
			Speaker speaker = this.speakerService.findSpeakerById(id);
			return this.speakerService.removeSpeaker(speaker);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	protected SpeakerService getSpeakerService() {

		return this.speakerService;
	}
}
