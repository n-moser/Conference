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

import com.prodyna.pac.conference.ejb.api.service.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * Interceptor that intercepts operations in order to create audit logs.
 * <p/>
 * Author: Nicolas Moser
 * Date: 15.10.13
 * Time: 00:30
 */
@Audit
@Interceptor
public class AuditInterceptor {

	private static final String MARKER_AUDIT = "AUDIT";

	@Inject
	private Logger logger;

	@Inject
	private UserSession userSession;

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
	public Object audit(InvocationContext context) throws Exception {

		Marker marker = MarkerFactory.getMarker(MARKER_AUDIT);

		String user = userSession.getUserName() != null ? userSession.getUserName() : "Anonymous";
		Class<?> serviceClass = context.getTarget().getClass();
		Method method = context.getMethod();

		Object[] args = new Object[]{user, serviceClass.getName(), method.getName()};

		logger.debug(marker, "User '{}' accessed service operation '{}.{}()'.", args);

		return context.proceed();
	}
}
