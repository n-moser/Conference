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

package com.prodyna.pac.conference.ejb.beans.event;

import com.prodyna.pac.conference.ejb.api.datatype.Talk;

/**
 * Event holding talk modification information.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:41
 */
public class TalkModificationEvent {

	private Talk oldTalk;

	private Talk newTalk;

	/**
	 * Creates a new TalkModificationEvent for the old and new Talk.
	 *
	 * @param oldTalk
	 * 		the old talk
	 * @param newTalk
	 * 		the new talk
	 */
	public TalkModificationEvent(Talk oldTalk, Talk newTalk) {

		if (oldTalk == null && newTalk == null) {
			throw new IllegalArgumentException(
					"Cannot create talk modification event for old talk 'null' and new talk 'null'.");
		}

		this.oldTalk = oldTalk;
	}

	/**
	 * Getter for the old Talk.
	 *
	 * @return the old talk
	 */
	public Talk getOldTalk() {

		return this.oldTalk;
	}

	/**
	 * Getter for the new Talk.
	 *
	 * @return the new talk
	 */
	public Talk getNewTalk() {

		return this.newTalk;
	}

	/**
	 * Retrieves the talk changes as string.
	 *
	 * @return the diff between the two talk instances as string
	 */
	public String getChanges() {

		StringBuilder result = new StringBuilder();

		if (oldTalk == null) {
			return "Talk '" + newTalk.getName() + "' has been created.";
		}

		if (newTalk == null) {
			return "Talk '" + oldTalk.getName() + "' has been removed.";
		}

		// TODO Diff

		result.append("Talk: ").append(oldTalk.getName()).append("\n");
		result.append("Date: ").append(oldTalk.getStartDate()).append("\n");
		result.append("Room: ").append(oldTalk.getRoom().getName()).append("\n");
		result.append("Conference: ").append(oldTalk.getConference()).append("\n");

		return result.toString();
	}
}
