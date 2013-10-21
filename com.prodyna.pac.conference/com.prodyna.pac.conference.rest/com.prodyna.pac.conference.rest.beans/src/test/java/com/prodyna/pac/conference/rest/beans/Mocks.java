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

package com.prodyna.pac.conference.rest.beans;

import com.prodyna.pac.conference.ejb.api.service.room.RoomService;
import com.prodyna.pac.conference.ejb.api.service.speaker.SpeakerService;
import com.prodyna.pac.conference.ejb.api.service.talk.TalkService;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;

/**
 * Mocks
 * <p/>
 * Author: Nicolas Moser
 * Date: 21.10.13
 * Time: 16:11
 */
public class Mocks {

	private TalkService talkMock;

	private RoomService roomMock;

	private SpeakerService speakerMock;


	@Produces
	public TalkService createTalkMock() {

		this.talkMock = Mockito.mock(TalkService.class);
		return talkMock;
	}


	@Produces
	public RoomService createRoomMock() {

		this.roomMock = Mockito.mock(RoomService.class);
		return roomMock;
	}


	@Produces
	public SpeakerService createSpeakerMock() {

		this.speakerMock = Mockito.mock(SpeakerService.class);
		return speakerMock;
	}
}
