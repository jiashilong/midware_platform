package com.jarry.rocketmq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.jarry.rocketmq.common.RocketClient;


/**
 * Created by jarry on 17/2/10.
 */
public class HelloWorldProducer extends RocketClient {

    public void produce() throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer_test");
        producer.setNamesrvAddr(this.host + ":" + this.port);
        producer.start();

        for(int i=0; i<10; i++) {
            try {
                Message msg = new Message("test", "tag_hello", ("Hello World " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sr = producer.send(msg);
                System.out.println(sr);
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
        HelloWorldProducer producer = new HelloWorldProducer();
        producer.produce();
    }


}
