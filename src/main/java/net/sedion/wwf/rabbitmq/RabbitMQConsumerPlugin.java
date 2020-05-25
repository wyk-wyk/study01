package net.sedion.wwf.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import net.sedion.wwf.rabbitmq.mq.AbstractConsumerHandler;
import net.sedion.wwf.rabbitmq.mq.ConsumerAnnotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 消费者插件
 *
 * @author WWF
 */
public class RabbitMQConsumerPlugin implements IPlugin {


    private ConnectionFactory factory;
    private Connection connection;
    List<Channel> channelList = new ArrayList<>();

    private Class<? extends AbstractConsumerHandler>[] classList;

    public RabbitMQConsumerPlugin(Class<? extends AbstractConsumerHandler>... classList) {
        this.classList = classList;
    }

    @Override
    public boolean start() {
        try {
            factory = RabbitKit.getInstance().getFactory();
            connection = factory.newConnection();
            for (Class c : classList) {
                if (null != c && c.isAnnotationPresent(ConsumerAnnotation.class)) {
                    ConsumerAnnotation annotation = (ConsumerAnnotation) c.getAnnotation(ConsumerAnnotation.class);
                    if (annotation != null && !StrKit.isBlank(annotation.queue())) {
                        Channel channel = connection.createChannel();
                        Map<String, Object> arguments = null;
                        if (!StrKit.isBlank(annotation.arguments())) {
                            arguments = JSONObject.parseObject(annotation.arguments()).getInnerMap();
                        }
                        channel.queueDeclare(annotation.queue(), annotation.durable(), annotation.exclusive(), annotation.autoDelete(), arguments);
                        Consumer consumer = (Consumer) c.getDeclaredConstructor(Channel.class).newInstance(channel);
                        channel.basicConsume(annotation.queue(), true, consumer);
                        channelList.add(channel);
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean stop() {
        if (!channelList.isEmpty()) {
            for (Channel channel : channelList) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            channelList.clear();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (factory != null) {
            factory = null;
        }
        return true;
    }


}
