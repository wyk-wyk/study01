package mq;

import net.sedion.wwf.rabbitmq.RabbitKit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WWF
 */
public class SimpleSendTest extends BaseTest {


    @Test
    public void sendSimpleMessage() {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String message = "Hello World!" + i;
            messages.add(message);
        }
        String[] strings = new String[messages.size()];
        messages.toArray(strings);
        RabbitKit.getInstance().sendSimpleMessage(QUEUE_NAME, strings);
    }


}
