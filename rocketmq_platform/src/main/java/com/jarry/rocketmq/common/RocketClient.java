package com.jarry.rocketmq.common;

/**
 * Created by jarry on 17/2/10.
 */
public abstract class RocketClient {
    protected String host;
    protected int port;

    public RocketClient() {
        this.host = "0.0.0.0";
        this.port = 9876;
    }

    public RocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public abstract void process() throws Exception;
}
