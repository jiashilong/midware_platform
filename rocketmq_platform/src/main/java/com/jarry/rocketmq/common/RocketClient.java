package com.jarry.rocketmq.common;

/**
 * Created by jarry on 17/2/10.
 */
public abstract class RocketClient {
    protected String host;
    protected int port;
    protected String topic;

    public RocketClient() {
        this.host = "localhost";
        this.port = 9876;
        this.topic = "default";
    }

    public RocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public abstract void process() throws Exception;
}
