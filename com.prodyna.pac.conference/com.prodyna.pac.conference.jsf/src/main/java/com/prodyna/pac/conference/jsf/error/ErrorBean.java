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

package com.prodyna.pac.conference.jsf.error;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * ErrorBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 16.10.13
 * Time: 17:16
 */
@ManagedBean
@RequestScoped
@Named("errorBean")
public class ErrorBean {

	/**
	 * Getter for the excpetion stack trace.
	 *
	 * @return the stack trace as string
	 */
	public String getStackTrace() {
		// Get the current JSF context
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

		// Fetch the exception
		Throwable exception = (Throwable) requestMap.get("javax.servlet.error.exception");

		// Create a writer for keeping the stacktrace of the exception
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);

		// Fill the stack trace into the write
		fillStackTrace(exception, printWriter);

		String stack = writer.toString();

		return stack;
	}

	/**
	 * Write the stack trace from an exception into a writer.
	 *
	 * @param exception
	 * 		exception for which to get the stack trace
	 * @param writer
	 * 		writer to write the stack trace
	 */
	private void fillStackTrace(Throwable exception, PrintWriter writer) {

		if (null == exception) {
			return;
		}

		exception.printStackTrace(writer);

		// The first time fillStackTrace is called it will always
		//  be a ServletException
		if (exception instanceof ServletException) {
			Throwable cause = ((ServletException) exception).getRootCause();
			if (null != cause) {
				writer.println("Root Cause:");
				fillStackTrace(cause, writer);
			}
		} else {
			// Embedded cause inside the ServletException
			Throwable cause = exception.getCause();

			if (null != cause) {
				writer.println("Cause:");
				fillStackTrace(cause, writer);
			}
		}
	}
}
