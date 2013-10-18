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

package com.prodyna.pac.conference.ejb.beans.interceptor;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

@Performance
@Interceptor
public class PerformanceInterceptor {

	private static final String MBEAN = "com.prodyna:type=Performance";

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object monitorPerformance(InvocationContext context) throws Exception {

		Method method = context.getMethod();

		long start = System.currentTimeMillis();

		try {
			return context.proceed();
		} finally {
			long end = System.currentTimeMillis();
			long duration = end - start;

			logger.debug("Operation '{}' lasted '{}' seconds.", method.getName(), duration / 1000.0);

			this.sendToMBean(context.getTarget().getClass(), method, duration);
		}

	}

	private void sendToMBean(Class<?> service, Method method, long time) {

		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName(MBEAN);

			if (!mBeanServer.isRegistered(objectName)) {
				mBeanServer.registerMBean(new com.prodyna.pac.conference.ejb.beans.mbean.Performance(), objectName);
			}

			Object[] parameters = new Object[]{service.getName(), method.getName(), time};
			String[] signature = new String[]{String.class.getName(), String.class.getName(), long.class.getName()};

			mBeanServer.invoke(objectName, "report", parameters, signature);

		} catch (Exception e) {
			logger.error("Error invoking MBean performance report.", e);
		}

		// JMX.newMBeanProxy(conn, objectName, PerformanceMBean.class);
	}

}
