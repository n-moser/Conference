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
package com.prodyna.pac.conference.ejb.beans.mdb;

import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message Driven Bean that handles the JMS Queue <b>queue/talk</b>.
 * <p/>
 * Author: Nicolas Moser
 * Date: 15.10.13
 * Time: 00:30
 */
@MessageDriven(mappedName = "queue/talk",
		activationConfig = {@ActivationConfigProperty(propertyName = "acknowledgeMode",
				propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(propertyName = "destination",
				propertyValue = "queue/talk"), @ActivationConfigProperty(propertyName = "destinationType",
				propertyValue = "javax.jms.Queue")})
public class TalkNotificationBean implements MessageListener {

	@Inject
	private Logger logger;

	@Override
	public void onMessage(Message message) {

		try {
			logger.info("Received Notification {}.", message.getJMSMessageID());

			if (message instanceof TextMessage) {
				logger.info("Message: \n {}.", ((TextMessage) message).getText());
			}

		} catch (JMSException e) {
			logger.error("Error deserializing JMS message.", e);
		}
	}

}
