package com.jarry.rocketmq.producer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.jarry.rocketmq.common.RocketClient;

import java.util.List;

/**
 * Created by jarry on 17/2/10.
 */
public class HelloWordConsumer extends RocketClient {
    @Override
    public void process() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_test");
        consumer.setNamesrvAddr(this.host + ":" + this.port);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("test", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                for(MessageExt msg : msgs) {
                    System.out.println(msg.getQueueId() + ":" + new String(msg.getBody()) + ":"
                                     + msg.getMsgId());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    public static void main(String[] args) throws Exception {
        HelloWordConsumer consumer = new HelloWordConsumer();
        consumer.process();
    }
}
