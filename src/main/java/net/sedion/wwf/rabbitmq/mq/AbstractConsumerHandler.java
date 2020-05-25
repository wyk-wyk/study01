package net.sedion.wwf.rabbitmq.mq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;

/**
 * 抽象 mq 消费者
 *
 * @author WWF
 */
public abstract class AbstractConsumerHandler extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public AbstractConsumerHandler(Channel channel) {
        super(channel);
    }
}
