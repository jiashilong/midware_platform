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
    private String group;
    private boolean fromBegin;


    public HelloWordConsumer(String host, int port, boolean fromBegin, String group, String topic) {
        super(host, port);
        this.fromBegin = fromBegin;
        this.group = group;
        this.topic = topic;
    }

    @Override
    public void process() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.group);
        consumer.setNamesrvAddr(this.host + ":" + this.port);
//        if(fromBegin) {
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        } else {
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        }

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(this.topic, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                for(MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        boolean fromBegin = false;
        if(Integer.parseInt(args[2]) == 1) {
            fromBegin = true;
        }
        System.out.println("kkkkkkkk: " + fromBegin);

        HelloWordConsumer consumer = new HelloWordConsumer(host, port, fromBegin, args[3], args[4]);
        consumer.process();
    }
}
