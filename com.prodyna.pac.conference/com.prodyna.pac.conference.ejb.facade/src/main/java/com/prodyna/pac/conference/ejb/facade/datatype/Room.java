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

package com.prodyna.pac.conference.ejb.facade.datatype;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Room
 * <p/>
 * Author: Nicolas Moser
 * Date: 06.09.13
 * Time: 17:36
 */
@Entity
public class Room implements Datatype {

	private Long id;

	private Long version;

	private String name;

	private Integer capacity = 50;

	private Conference conference;

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
	 * Getter for the rooms name.
	 *
	 * @return the name of the room
	 */
	@Size(min = 3, max = 50)
	@NotNull
	@Column(nullable = false, length = 50)
	public String getName() {

		return name;
	}

	/**
	 * Setter for the rooms name.
	 *
	 * @param name
	 * 		the rooms name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Getter for the rooms capacity.
	 *
	 * @return the capacity
	 */
	@Min(value = 1)
	@Max(value = 10000)
	@Column(nullable = false)
	public Integer getCapacity() {

		return capacity;
	}

	/**
	 * Setter for the rooms capacity.
	 *
	 * @param capacity
	 * 		the capacity to set
	 */
	public void setCapacity(Integer capacity) {

		this.capacity = capacity;
	}

	/**
	 * Getter for the contained conference.
	 *
	 * @return the conference this room belongs to
	 */
	@NotNull
	@JoinColumn
	@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
	public Conference getConference() {

		return conference;
	}

	/**
	 * Setter for the conference this room belongs to.
	 *
	 * @param conference
	 * 		the conference to set
	 */
	public void setConference(Conference conference) {

		this.conference = conference;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Room room = (Room) o;

		if (capacity != null ? !capacity.equals(room.capacity) : room.capacity != null) {
			return false;
		}
		if (conference != null ? !conference.equals(room.conference) : room.conference != null) {
			return false;
		}
		if (id != null ? !id.equals(room.id) : room.id != null) {
			return false;
		}
		if (name != null ? !name.equals(room.name) : room.name != null) {
			return false;
		}
		if (version != null ? !version.equals(room.version) : room.version != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
		result = 31 * result + (conference != null ? conference.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return this.getName();
	}
}
