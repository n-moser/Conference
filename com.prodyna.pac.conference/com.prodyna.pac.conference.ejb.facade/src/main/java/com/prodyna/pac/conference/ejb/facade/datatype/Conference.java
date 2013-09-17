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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Conference
 * <p/>
 * Author: Nicolas Moser
 * Date: 06.09.13
 * Time: 17:33
 */
@Entity
public class Conference implements Datatype {

	private Long id;

	private Long version;

	private String name;

	private String description;

	private Date startDate;

	private Date endDate;

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
	 * Getter for the conference name.
	 *
	 * @return the conference name
	 */
	@Size(min = 3, max = 50)
	@NotNull
	@Column(nullable = false, length = 50)
	public String getName() {

		return name;
	}

	/**
	 * Setter for the conference name.
	 *
	 * @param name
	 * 		the conference name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Getter for the conference description.
	 *
	 * @return the conference description
	 */
	@Size(min = 1, max = 255)
	@Column(nullable = true, length = 255)
	public String getDescription() {

		return description;
	}

	/**
	 * Setter for the conference description.
	 *
	 * @param description
	 * 		the description to set
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * Getter for the conference start date.
	 *
	 * @return the start date
	 */
	@Future
	@NotNull
	@Temporal(value = TemporalType.DATE)
	@Column(nullable = false)
	public Date getStartDate() {

		return startDate;
	}

	/**
	 * Setter for the conference start date.
	 *
	 * @param startDate
	 * 		the start date to set
	 */
	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	/**
	 * Getter for the conference end date.
	 *
	 * @return the end date
	 */
	@Future
	@NotNull
	@Temporal(value = TemporalType.DATE)
	@Column(nullable = false)
	public Date getEndDate() {

		return endDate;
	}

	/**
	 * Setter for the conference end date.
	 *
	 * @param endDate
	 * 		the end date to set
	 */
	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}
}
