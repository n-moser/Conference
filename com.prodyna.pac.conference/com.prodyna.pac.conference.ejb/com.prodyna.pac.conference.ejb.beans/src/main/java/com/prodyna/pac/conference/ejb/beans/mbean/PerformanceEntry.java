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

package com.prodyna.pac.conference.ejb.beans.mbean;

/**
 * A performance entry for one service operation.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:43
 */
public class PerformanceEntry {

	private String service;

	private String method;

	private int count;

	private long minTime = Long.MAX_VALUE;

	private long maxTime = Long.MIN_VALUE;

	private long sum;

	/**
	 * Creates a new performance entry.
	 *
	 * @param service
	 * 		the service interface name
	 * @param method
	 * 		the service operation name
	 */
	public PerformanceEntry(String service, String method) {

		this.service = service;
		this.method = method;
	}

	/**
	 * Report a service call duraion to the entry.
	 *
	 * @param time
	 * 		the duration
	 */
	public void report(long time) {

		if (time > maxTime) {
			maxTime = time;
		}
		if (time < minTime) {
			minTime = time;
		}

		this.sum += time;
		this.count++;
	}

	/**
	 * Getter for the service name.
	 *
	 * @return the service name
	 */
	public String getService() {

		return service;
	}

	/**
	 * Getter for the service operation name.
	 *
	 * @return the service operation name
	 */
	public String getMethod() {

		return method;
	}

	/**
	 * Getter for the invocation count.
	 *
	 * @return the invocation count
	 */
	public int getCount() {

		return count;
	}

	/**
	 * Getter for the minimum duration.
	 *
	 * @return the smallest duration
	 */
	public long getMinTime() {

		return minTime;
	}

	/**
	 * Getter for the maximum duration.
	 *
	 * @return the longest duration
	 */
	public long getMaxTime() {

		return maxTime;
	}

	/**
	 * Getter for the average duration.
	 *
	 * @return the average duration
	 */
	public long getAverageTime() {

		return sum / count;
	}

	/**
	 * Getter for the overall duration.
	 *
	 * @return the overall duration
	 */
	public long getSum() {

		return sum;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("PerformanceEntry [service=");
		builder.append(service);
		builder.append(", method=");
		builder.append(method);
		builder.append(", count=");
		builder.append(count);
		builder.append(", minTime=");
		builder.append(minTime);
		builder.append(", maxTime=");
		builder.append(maxTime);
		builder.append(", sum=");
		builder.append(sum);
		builder.append("]");
		return builder.toString();
	}

}
