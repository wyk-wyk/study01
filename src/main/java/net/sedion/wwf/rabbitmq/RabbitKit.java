package net.sedion.wwf.rabbitmq;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author WWF
 */
public class RabbitKit {


    private static RabbitKit instance;
    private ConnectionFactory factory;

    private static final AMQP.BasicProperties PROPERTIES = new AMQP.BasicProperties.Builder()
            .contentType("application/json")
            .deliveryMode(2)
            .build();

    public static RabbitKit getInstance() {
        if (instance == null) {
            instance = new RabbitKit();
            instance.init();
        }
        return instance;
    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    /**
     * 初始化
     */
    public void init() {
        Prop prop = PropKit.use("rabbit.properties", "utf-8");
        if (factory == null) {
            String host = prop.get("rabbit.host", "localhost");
            int port = prop.getInt("rabbit.port", 5672);
            String vhost = prop.get("rabbit.vhost", "/");
            String username = prop.get("rabbit.username", "");
            String password = prop.get("rabbit.password", "");
            factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setVirtualHost(vhost);
            if (!StrKit.isBlank(username)) {
                factory.setUsername(username);
                factory.setPassword(password);
            }
        }
    }

    /**
     * 发送简单消息
     *
     * @param queue
     * @param messages
     */
    public void sendSimpleMessage(String queue, String... messages) {
        if (messages == null || messages.length == 0) {
            return;
        }
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            for (String message : messages) {
                channel.basicPublish("", queue, PROPERTIES, message.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
