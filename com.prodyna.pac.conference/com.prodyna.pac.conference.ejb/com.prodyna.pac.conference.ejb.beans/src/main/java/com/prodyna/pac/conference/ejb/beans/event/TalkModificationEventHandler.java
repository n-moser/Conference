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

package com.prodyna.pac.conference.ejb.beans.event;

import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class TalkModificationEventHandler {

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/talk")
	private Queue queue;

	private Connection connection;

	private Session session;

	private MessageProducer messageProducer;

	@Inject
	private Logger logger;

	@Asynchronous
	public void accept(@Observes(during = TransactionPhase.AFTER_SUCCESS) TalkModificationEvent event) {

		try {
			String message = this.createMessage(event);
			sendMessage(message);
		} catch (JMSException e) {
			// TODO: Exception Handling
			logger.error("Error sending Message to JMS Queue.", e);
		}
	}

	/**
	 * Create the text message depending on the talk modification.
	 *
	 * @param event
	 * 		the event holding the necessary talk modification information
	 *
	 * @return the text message
	 */
	private String createMessage(TalkModificationEvent event) {

		if (event != null && event.getTalk() != null) {

			// TODO Better Message
			StringBuilder message = new StringBuilder();

			message.append("Talk: ").append(event.getTalk().getName()).append("\n");
			message.append("Date: ").append(event.getTalk().getStartDate()).append("\n");
			message.append("Room: ").append(event.getTalk().getRoom().getName()).append("\n");
			message.append("Conference: ").append(event.getTalk().getConference()).append("\n");
			return message.toString();
		}

		return "Undefined Talk Change";
	}

	/**
	 * Send the message to the JMS Queue.
	 *
	 * @param messageBody
	 * 		the text message to send
	 *
	 * @throws JMSException
	 * 		in case the JMS message cannot be sent
	 */
	private void sendMessage(String messageBody) throws JMSException {

		try {
			// TODO: CDI Producer
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();

			this.session = this.connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

			this.messageProducer = this.session.createProducer(this.queue);

			TextMessage message = this.session.createTextMessage(messageBody);
			this.messageProducer.send(message);

			session.commit();

		} finally {
			if (messageProducer != null) {
				this.messageProducer.close();
			}
			if (session != null) {
				this.session.close();
			}
			if (connection != null) {
				this.connection.close();
			}
		}
	}

}
