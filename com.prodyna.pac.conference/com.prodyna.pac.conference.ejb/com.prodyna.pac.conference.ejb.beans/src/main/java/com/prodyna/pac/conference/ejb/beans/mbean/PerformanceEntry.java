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

public class PerformanceEntry {

	private String service;

	private String method;

	private int count;

	private long minTime = Long.MAX_VALUE;

	private long maxTime = Long.MIN_VALUE;

	private long sum;

	public PerformanceEntry(String service, String method) {

		this.service = service;
		this.method = method;
	}

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

	public String getService() {

		return service;
	}

	public String getMethod() {

		return method;
	}

	public int getCount() {

		return count;
	}

	public long getMinTime() {

		return minTime;
	}

	public long getMaxTime() {

		return maxTime;
	}

	public long getAverageTime() {

		return sum / count;
	}

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
