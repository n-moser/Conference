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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default Implementation of PerformanceMBean.
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:43
 */
public class Performance implements PerformanceMBean {

	private Map<String, PerformanceEntry> entries = new HashMap<String, PerformanceEntry>();

	@Override
	public synchronized void report(String service, String method, long time) {

		String key = service + ":" + method;

		PerformanceEntry entry = entries.get(key);

		if (entry == null) {
			entry = new PerformanceEntry(service, method);
			entries.put(key, entry);
		}

		entry.report(time);
	}

	@Override
	public List<PerformanceEntry> getAll() {

		return new ArrayList<PerformanceEntry>(entries.values());
	}

	@Override
	public String getAllAsString() {

		StringBuilder result = new StringBuilder();
		for (PerformanceEntry entry : this.entries.values()) {
			result.append(entry);
			result.append("\n");
		}

		return result.toString();
	}

	@Override
	public int getCount() {

		return this.entries.size();
	}

	@Override
	public PerformanceEntry getWorstByTime() {

		PerformanceEntry worst = null;
		for (PerformanceEntry entry : this.entries.values()) {

			if (worst == null) {
				worst = entry;
			} else {
				if (entry.getSum() > worst.getSum()) {
					worst = entry;
				}
			}
		}
		return worst;
	}

	@Override
	public PerformanceEntry getWorstByAverage() {

		PerformanceEntry worst = null;
		for (PerformanceEntry entry : this.entries.values()) {

			if (worst == null) {
				worst = entry;
			} else {
				if (entry.getAverageTime() > worst.getAverageTime()) {
					worst = entry;
				}
			}
		}
		return worst;
	}

	@Override
	public PerformanceEntry getWorstByCount() {

		PerformanceEntry worst = null;
		for (PerformanceEntry entry : this.entries.values()) {

			if (worst == null) {
				worst = entry;
			} else {
				if (entry.getCount() > worst.getCount()) {
					worst = entry;
				}
			}
		}
		return worst;
	}

	@Override
	public void reset() {

		this.entries.clear();
	}

}
