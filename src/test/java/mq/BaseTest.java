package mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import net.sedion.wwf.rabbitmq.RabbitKit;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author WWF
 */
public class BaseTest {
    ConnectionFactory factory;
    protected Connection connection;

    protected final static String QUEUE_NAME = "test";

    @Before
    public void init() {
        factory = RabbitKit.getInstance().getFactory();
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
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
    }
}
