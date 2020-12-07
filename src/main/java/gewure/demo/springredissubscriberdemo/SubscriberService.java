package gewure.demo.springredissubscriberdemo;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriberService implements MessageListener {

    public static List<String> messageList = new ArrayList<>(); //holds all messages received so far

    @Override
    public void onMessage(Message message, byte[] bytes) {
        messageList.add(message.toString());
        System.out.println("Message received: " + message.toString());
    }

}