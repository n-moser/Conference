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

package com.prodyna.pac.conference.jsf.admin;

import com.prodyna.pac.conference.ejb.facade.exception.ServiceException;
import com.prodyna.pac.conference.ejb.facade.exception.ValidationException;
import com.prodyna.pac.conference.ejb.facade.exception.ValidationItem;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.lang.reflect.InvocationTargetException;

/**
 * AdminBean
 * <p/>
 * Author: Nicolas Moser
 * Date: 16.10.13
 * Time: 22:35
 */
public abstract class AdminBean {

	/**
	 * Save the contained datatype to the server.
	 *
	 * @return the navigation target
	 */
	public String save() {

		try {
			return this.internalSave();

		} catch (ValidationException ve) {
			this.addMessage(ve);
		} catch (ServiceException se) {
			this.addMessage(se);
		} catch (Exception e) {
			this.addMessage(e);
		}

		return null;
	}

	/**
	 * Remove the contained datatype from the server.
	 *
	 * @return the navigation target
	 */
	public String remove() {

		try {
			return this.internalRemove();

		} catch (ValidationException ve) {
			this.addMessage(ve);
		} catch (ServiceException se) {
			this.addMessage(se);
		} catch (Exception e) {
			this.addMessage(e);
		}

		return null;
	}


	/**
	 * Save the contained datatype to the server.
	 *
	 * @return the navigation target
	 *
	 * @throws ServiceException
	 * 		when the server raised an exception
	 */
	protected abstract String internalSave() throws ServiceException;

	/**
	 * Remove the contained datatype from the server.
	 *
	 * @return the navigation target
	 *
	 * @throws ServiceException
	 * 		when the server raised an exception
	 */
	protected abstract String internalRemove() throws ServiceException;

	/**
	 * Retrieve the form name of the administration bean.
	 *
	 * @return the form bean
	 */
	protected abstract String getFormName();

	private void addMessage(ValidationException ve) {

		if (ve.hasItems()) {

			for (ValidationItem item : ve.getItems()) {

				String clientId = this.getFormName() + ":" + item.getPropertyName();

				FacesContext.getCurrentInstance().addMessage(clientId,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", item.getMessage()));
			}

			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	private void addMessage(ServiceException se) {

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Error", se.getMessage()));
	}

	private void addMessage(Exception exception) {

		FacesContext context = FacesContext.getCurrentInstance();

		if (exception instanceof InvocationTargetException) {
			Throwable targetException = ((InvocationTargetException) exception).getTargetException();

			if (targetException instanceof ValidationException) {
				this.addMessage((ValidationException) targetException);
			} else if (targetException instanceof ServiceException) {
				this.addMessage((ServiceException) targetException);
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected Error", exception.getMessage()));
			}
		} else {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected Error", exception.getMessage()));
		}
	}
}
