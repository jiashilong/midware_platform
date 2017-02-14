package com.jarry.rocketmq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.jarry.rocketmq.common.RocketClient;


/**
 * Created by jarry on 17/2/10.
 */
public class HelloWorldProducer extends RocketClient {
    private String group;

    public HelloWorldProducer(String group, String topic) {
        this.group = group;
        this.topic = topic;
    }

    public void produce() throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(this.group);
        producer.setNamesrvAddr(this.host + ":" + this.port);
        producer.start();

        for(long i=1; i<=Long.MAX_VALUE; i++) {
        //while(true) {
            try {
                Message msg = new Message(this.topic, "tag1", ("Hello Man " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sr = producer.send(msg);
                if(sr.getSendStatus() == SendStatus.SEND_OK) {
                        System.out.println("Hello Man " + i);
                }
               // Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            } finally {

            }
        }

        producer.shutdown();
    }

    @Override
    public void process() throws Exception {
        produce();
    }

    public static void main(String[] args) throws Exception {
        HelloWorldProducer producer = new HelloWorldProducer("g4", "t4");
        producer.produce();
    }


}
