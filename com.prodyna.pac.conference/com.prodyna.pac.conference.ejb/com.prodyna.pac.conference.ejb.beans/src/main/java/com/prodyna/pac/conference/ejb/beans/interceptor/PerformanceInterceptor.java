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

/**
 * Interceptor that intercepts operations in order to create performance logs.
 * <p/>
 * Author: Nicolas Moser
 * Date: 15.10.13
 * Time: 00:30
 */
@Performance
@Interceptor
public class PerformanceInterceptor {

	private static final String MBEAN = "com.prodyna:type=Performance";

	@Inject
	private Logger logger;

	/**
	 * Interception method that is called before the delegating operation is called.
	 *
	 * @param context
	 * 		the invocation context
	 *
	 * @return the operation result
	 *
	 * @throws Exception
	 * 		when the delegating operation raises an exception
	 */
	@AroundInvoke
	public Object monitorPerformance(InvocationContext context) throws Exception {

		Class<?> serviceClass = context.getTarget().getClass();
		Method method = context.getMethod();

		long start = System.currentTimeMillis();

		try {
			return context.proceed();
		} finally {
			long end = System.currentTimeMillis();
			long duration = end - start;

			String[] args = new String[]{serviceClass.getName(), method.getName(), String.valueOf(duration / 1000.0)};

			logger.info("Operation '{}.{}()' lasted '{}' seconds.", args);

			this.sendToMBean(serviceClass, method, duration);
		}

	}

	/**
	 * Send the service call and duration to the PerformanceMBean.
	 *
	 * @param service
	 * 		the service interface class
	 * @param method
	 * 		the service operation
	 * @param time
	 * 		the service call duration
	 */
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

	}

}
