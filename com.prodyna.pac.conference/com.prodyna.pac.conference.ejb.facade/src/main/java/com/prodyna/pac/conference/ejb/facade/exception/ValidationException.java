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

package com.prodyna.pac.conference.ejb.facade.exception;

import javax.ejb.ApplicationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ServiceException
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:44
 */
@ApplicationException(rollback = true)
public class ValidationException extends ServiceException {

	private List<ValidationItem> items = new ArrayList<ValidationItem>();

	/** Creates a new service exception without parameters. */
	public ValidationException() {

		super();
	}

	/**
	 * Creates a new validation exception with the given error message.
	 *
	 * @param message
	 * 		the error message
	 */
	public ValidationException(String message) {

		super(message);
	}

	/**
	 * Creates a new service exception with the given error cause.
	 *
	 * @param cause
	 * 		the causing exception
	 */
	public ValidationException(Exception cause) {

		super(cause);
	}

	/**
	 * Creates a new service exception with the given error message and cause.
	 *
	 * @param message
	 * 		the error message
	 * @param cause
	 * 		the causing exception
	 */
	public ValidationException(String message, Exception cause) {

		super(message, cause);
	}

	/**
	 * Add an item to the validation exception.
	 *
	 * @param propertyName
	 * 		the property name
	 * @param message
	 * 		the validation messag
	 */
	public void addItem(String propertyName, String message) {

		this.items.add(new ValidationItem(propertyName, message));
	}

	/**
	 * Getter for the validation items.
	 *
	 * @return the list of items
	 */
	public List<ValidationItem> getItems() {

		return Collections.unmodifiableList(items);
	}

	/**
	 * Check whether the exception contains validation items.
	 *
	 * @return <b>true</b> if it holds items, <b>false</b> if not
	 */
	public boolean hasItems() {

		return !this.items.isEmpty();
	}
}
