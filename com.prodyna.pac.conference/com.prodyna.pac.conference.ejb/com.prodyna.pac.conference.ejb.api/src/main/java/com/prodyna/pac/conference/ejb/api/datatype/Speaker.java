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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A speaker is able to hold a talk.
 * <p/>
 * Author: Nicolas Moser
 * Date: 06.09.13
 * Time: 17:36
 */
@Entity
public class Speaker implements Datatype {

	private Long id;

	private Long version;

	private String name;

	private String description;

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
	 * Getter for the speakers name.
	 *
	 * @return name of the speaker
	 */
	@NotNull
	@Size(min = 3, max = 50)
	@Pattern(regexp = "[a-zA-Z ]*")
	@Column(nullable = false, length = 50)
	public String getName() {

		return name;
	}

	/**
	 * Setter for the speakers name.
	 *
	 * @param name
	 * 		the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Getter for the speakers profile description.
	 *
	 * @return the profile description
	 */
	@Lob
	@Size(max = 1000)
	@Column(nullable = true, length = 1000)
	public String getDescription() {

		return description;
	}

	/**
	 * Setter for the speakers profile description.
	 *
	 * @param description
	 * 		the description to set
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Speaker speaker = (Speaker) o;

		if (description != null ? !description.equals(speaker.description) : speaker.description != null) {
			return false;
		}
		if (id != null ? !id.equals(speaker.id) : speaker.id != null) {
			return false;
		}
		if (name != null ? !name.equals(speaker.name) : speaker.name != null) {
			return false;
		}
		if (version != null ? !version.equals(speaker.version) : speaker.version != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return this.getName();
	}
}
