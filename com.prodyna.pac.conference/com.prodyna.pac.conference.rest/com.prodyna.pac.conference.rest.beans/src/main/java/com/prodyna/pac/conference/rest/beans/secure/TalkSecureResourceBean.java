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

package com.prodyna.pac.conference.rest.beans.secure;

import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.RESTException;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import com.prodyna.pac.conference.rest.api.secure.TalkSecureResource;
import com.prodyna.pac.conference.rest.beans.TalkResourceBean;

import javax.inject.Inject;

/**
 * TalkResourceBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 19.09.13
 * Time: 18:28
 */
public class TalkSecureResourceBean extends TalkResourceBean implements TalkSecureResource {

	@Inject
	private TalkService talkService;

	@Override
	public Talk createTalk(Talk talk) throws RESTException {

		try {
			return this.talkService.createTalk(talk);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	public Talk updateTalk(Talk talk) throws RESTException {

		try {
			return this.talkService.updateTalk(talk);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	public Talk deleteTalk(Long id) throws RESTException {

		try {
			Talk talk = this.talkService.findTalkById(id);
			return this.talkService.removeTalk(talk);
		} catch (ServiceException e) {
			throw new RESTException(e);
		}
	}

	@Override
	protected TalkService getTalkService() {

		return this.talkService;
	}

}
