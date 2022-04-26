package com.example;

import java.util.function.Consumer;

public class Reader {
    private String consumerTag;
    private String exchangeName;
    public Reader(String exchangeName) { this.exchangeName = exchangeName; }
    public void start(Consumer<String> callback) {
        consumerTag = RabbitMQHelpers.readMessage(
            RabbitMQHelpers.createQueue(exchangeName), callback);
    }
    public void stop() { RabbitMQHelpers.cancelReading(consumerTag); }
}
