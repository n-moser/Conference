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

package com.prodyna.pac.conference.ejb.beans.producer;

import com.prodyna.pac.conference.ejb.beans.mbean.MBeanProxy;
import com.prodyna.pac.conference.ejb.beans.mbean.performance.Performance;
import com.prodyna.pac.conference.ejb.beans.mbean.performance.PerformanceMBean;

import javax.enterprise.inject.Produces;
import javax.management.JMException;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Produces MBean Proxy instances.
 * <p/>
 * Author: Nicolas Moser
 * Date: 30.10.13
 * Time: 09:33
 */
public class MBeanProxyProducer {

	/**
	 * Creates a new PerformanceMBean proxy.
	 *
	 * @return the mbean proxy
	 *
	 * @throws JMException
	 * 		when the mbean proxy cannnot be created
	 */
	@Produces
	@MBeanProxy
	public PerformanceMBean producePerformanceMBean() throws JMException {

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		if (mBeanServer == null) {
			throw new IllegalStateException("No MBean Server registered!");
		}

		ObjectName objectName = new ObjectName("com.prodyna:type=Performance");
		if (!mBeanServer.isRegistered(objectName)) {
			mBeanServer.registerMBean(new Performance(), objectName);
		}

		return JMX.newMBeanProxy(mBeanServer, objectName, PerformanceMBean.class);
	}

}
