package org.tchw.springtesting.services.impl;

import org.apache.log4j.Logger;
import org.tchw.springtesting.services.MessageSender;

public class MessageSenderImpl implements MessageSender {

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public void send(String message) {
        log.debug(message);
    }

}
