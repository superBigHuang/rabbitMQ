package com.huang;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 通道绑定交换机
//        channel.exchangeBind()
        channel.exchangeDeclare("logs", "fanout");

        // 创建临时队列
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queue, "logs", "");

        // 消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("消费者2" + new String(body));
            }
        });


    }
}
