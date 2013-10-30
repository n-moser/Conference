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

package com.prodyna.pac.conference.ejb.beans.mbean.performance;

import java.util.List;

/**
 * An MBean for monitoring service operation statistics like invocation count and duration.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:43
 */
public interface PerformanceMXBean {

	/**
	 * Getter for all performance entries.
	 *
	 * @return the list of entries
	 */
	List<PerformanceEntry> getAll();

	/**
	 * Retrieves all performance entries as single string.
	 *
	 * @return the result string
	 */
	String getAllAsString();

	/**
	 * Report a service invocation.
	 *
	 * @param service
	 * 		the invocated service
	 * @param method
	 * 		the invocated service operation
	 * @param time
	 * 		the service operation duration
	 */
	void report(String service, String method, long time);

	/**
	 * Retrieves the amount of performance entries.
	 *
	 * @return the amount of entries
	 */
	int getCount();

	/**
	 * Retrieve the worst entry by overal time.
	 *
	 * @return the entry with most overall duration
	 */
	PerformanceEntry getWorstByTime();

	/**
	 * Retrieve the worst entry by average time.
	 *
	 * @return the entry with most average duration
	 */
	PerformanceEntry getWorstByAverage();

	/**
	 * Retrieve the worst entry by invocation count.
	 *
	 * @return the entry with most invocation counts
	 */
	PerformanceEntry getWorstByCount();

	/** Reset all collected performance entries. */
	void reset();

}
