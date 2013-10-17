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

package com.prodyna.pac.conference.ejb.api.datatype;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * TalkSpeaker
 * <p/>
 * Author: Nicolas Moser
 * Date: 09.09.13
 * Time: 23:28
 */
@Entity
public class TalkSpeaker implements Datatype {

	private Long id;

	private Long version;

	private Speaker speaker;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Long getId() {

		return this.id;
	}

	/**
	 * Setter for the entity identifier.
	 *
	 * @param id
	 * 		the ID to set
	 */
	public void setId(Long id) {

		this.id = id;
	}

	@Version
	@Override
	public Long getVersion() {

		return version;
	}

	/**
	 * Setter for the entity version.
	 *
	 * @param version
	 * 		the version to set
	 */
	public void setVersion(Long version) {

		this.version = version;
	}

	/**
	 * Getter for the speaker.
	 *
	 * @return the speaker
	 */
	@NotNull
	@JoinColumn
	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
	public Speaker getSpeaker() {

		return speaker;
	}

	/**
	 * Setter for the speaker
	 *
	 * @param speaker
	 * 		the speaker to set
	 */
	public void setSpeaker(Speaker speaker) {

		this.speaker = speaker;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TalkSpeaker that = (TalkSpeaker) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (speaker != null ? !speaker.equals(that.speaker) : that.speaker != null) {
			return false;
		}
		if (version != null ? !version.equals(that.version) : that.version != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		if (speaker != null) {
			return this.getSpeaker().getName();
		}

		return "Undefined";
	}

}
