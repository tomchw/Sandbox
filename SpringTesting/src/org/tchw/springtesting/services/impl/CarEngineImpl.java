package org.tchw.springtesting.services.impl;

import org.tchw.springtesting.services.CarEngine;
import org.tchw.springtesting.services.MessageSender;

public class CarEngineImpl implements CarEngine {

    private final MessageSender messageSender;

    public CarEngineImpl(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void start() {
        messageSender.send("Car is being started");
    }

    @Override
    public void stop() {
        messageSender.send("Car is being stopped");
    }

}
