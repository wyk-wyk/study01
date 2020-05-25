package net.sedion.wwf.rabbitmq.mq.consumer;

import com.jfinal.aop.Aop;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import net.sedion.wwf.rabbitmq.mq.AbstractConsumerHandler;
import net.sedion.wwf.rabbitmq.mq.ConsumerAnnotation;
import net.sedion.wwf.rabbitmq.mq.RabbitMQConfig;
import net.sedion.wwf.service.TestService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author WWF
 */
@ConsumerAnnotation(queue = RabbitMQConfig.QUEUE_TEST)
public class SimpleConsumer extends AbstractConsumerHandler {

    TestService testService = Aop.get(TestService.class);

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public SimpleConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, StandardCharsets.UTF_8);
        try {
            testService.doit(message);
            testService.doit2(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
