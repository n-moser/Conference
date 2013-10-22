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

package com.prodyna.pac.conference.ejb.beans.decorator;

import com.prodyna.pac.conference.ejb.beans.event.TalkModificationEvent;
import com.prodyna.pac.conference.ejb.api.datatype.Talk;
import com.prodyna.pac.conference.ejb.api.exception.ServiceException;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Java EE Decorator that intersects TalkService and sends modifications of Talk entity as Java EE.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:41
 */
@Decorator
public abstract class TalkModificationDecorator implements TalkService {

	@Inject
	@Delegate
	private TalkService delegate;

	@Inject
	private Event<TalkModificationEvent> event;

	@Override
	public Talk updateTalk(Talk talk) throws ServiceException {

		Talk newTalk;

		if (talk != null && talk.getId() != null) {

			Talk oldTalk = this.delegate.findTalkById(talk.getId());

			newTalk = this.delegate.updateTalk(talk);

			if (newTalk != null && newTalk.getId() != null) {
				TalkModificationEvent talkModificationEvent = new TalkModificationEvent(oldTalk, newTalk);
				this.event.fire(talkModificationEvent);
			}
		} else {
			newTalk = this.delegate.updateTalk(talk);
		}

		return newTalk;
	}

}
