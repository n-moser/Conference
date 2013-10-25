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
import com.prodyna.pac.conference.ejb.api.datatype.TalkSpeaker;

import java.util.ArrayList;

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
		this.newTalk = newTalk;
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

		if (oldTalk == null) {
			return "Talk '" + newTalk.getName() + "' has been created.";
		}

		if (newTalk == null) {
			return "Talk '" + oldTalk.getName() + "' has been removed.";
		}

		StringBuilder result = new StringBuilder();
		result.append("Talk Changes: \n");

		if (oldTalk.getName() != null && !oldTalk.getName().equals(newTalk.getName())) {
			result.append("\tName: ").append(oldTalk.getName()).append(" -> ");
			result.append(newTalk.getName()).append("\n");
		}

		if (oldTalk.getDescription() != null && !oldTalk.getDescription().equals(newTalk.getDescription())) {
			result.append("\tDescription: ").append(oldTalk.getDescription()).append(" -> ");
			result.append(newTalk.getDescription()).append("\n");
		}

		if (oldTalk.getStartDate() != null && oldTalk.getStartDate().getTime() != newTalk.getStartDate().getTime()) {
			result.append("\tStart Date: ").append(oldTalk.getStartDate()).append(" -> ");
			result.append(newTalk.getStartDate()).append("\n");
		}

		if (newTalk.getEndDate() != null && oldTalk.getEndDate().getTime() != newTalk.getEndDate().getTime()) {
			result.append("\tEnd Date: ").append(oldTalk.getEndDate()).append(" -> ");
			result.append(newTalk.getEndDate()).append("\n");
		}

		if (newTalk.getDuration() != null && !oldTalk.getDuration().equals(newTalk.getDuration())) {
			result.append("\tDuration: ").append(oldTalk.getDuration()).append(" -> ");
			result.append(newTalk.getDuration()).append("\n");
		}

		if (oldTalk.getRoom() != null && !oldTalk.getRoom().equals(newTalk.getRoom())) {
			result.append("\tRoom: ").append(oldTalk.getRoom().getName()).append(" -> ");
			result.append(newTalk.getRoom()).append("\n");
		}

		if (oldTalk.getConference() != null && !oldTalk.getConference().equals(newTalk.getConference())) {
			result.append("\tConference: ").append(oldTalk.getConference()).append(" -> ");
			result.append(newTalk.getConference()).append("\n");
		}

		if (!new ArrayList<TalkSpeaker>(oldTalk.getSpeakers()).equals(
				new ArrayList<TalkSpeaker>(newTalk.getSpeakers()))) {
			result.append("\tSpeakers: ").append(oldTalk.getSpeakers()).append(" -> ");
			result.append(newTalk.getSpeakers()).append("\n");
		}

		return result.toString();
	}
}
