package org.tchw.springtesting.services.impl;

import org.tchw.springtesting.services.MessageSender;

public class MessageSenderImpl implements MessageSender {

	@Override
	public void send(String message) {
		System.out.println("Send: " + message);
	}

}
