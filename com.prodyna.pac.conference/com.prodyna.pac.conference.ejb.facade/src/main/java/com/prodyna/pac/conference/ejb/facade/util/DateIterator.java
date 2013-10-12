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

/*
 * (C)opyright 2012 ssb Software Service und Beratung GmbH
 */
package com.prodyna.pac.conference.ejb.facade.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Iteriert Tagesweise von einem Start-Datum zu einem Ende-Datum.
 *
 * @author Nicolas Moser <nicolas.moser@prodyna.de>
 */
public class DateIterator implements Iterator<Date> {

	private final Calendar current;

	private final Calendar end;

	/**
	 * Constructs a DateIterator.
	 *
	 * @param startDate
	 * 		das Start Darum
	 * @param endDate
	 * 		das Ende Datum
	 */
	public DateIterator(Date startDate, Date endDate) {

		if ((startDate == null) || (endDate == null)) {
			throw new IllegalArgumentException("Cannot create DateIterator for date 'null'.");
		}
		if (startDate.after(endDate)) {
			throw new IllegalArgumentException("Cannot create DateIterator for startDate after endDate.");
		}

		this.current = Calendar.getInstance();
		this.current.setTime(startDate);
		this.current.add(Calendar.DATE, -1);

		this.end = Calendar.getInstance();
		this.end.setTime(endDate);
		this.end.add(Calendar.DATE, -1);
	}

	@Override
	public boolean hasNext() {

		final boolean yearIsAfter = this.current.get(Calendar.YEAR) > this.end.get(Calendar.YEAR);
		final boolean yearIsEqual = this.current.get(Calendar.YEAR) == this.end.get(Calendar.YEAR);
		final boolean dayIsAfter = this.current.get(Calendar.DAY_OF_YEAR) > this.end.get(Calendar.DAY_OF_YEAR);

		final boolean isAfter = yearIsAfter || (yearIsEqual && dayIsAfter);
		return !isAfter;
	}

	@Override
	public Date next() {

		this.current.add(Calendar.DATE, 1);
		return this.current.getTime();
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException("Remove is not supported in DateIterator!");
	}
}
