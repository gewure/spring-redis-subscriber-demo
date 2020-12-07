package gewure.demo.springredissubscriberdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter,topic());
        return redisMessageListenerContainer;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter()
    {
        return new MessageListenerAdapter(new SubscriberService(),"onMessage");
        // see onMessage() in SubscriberService
    }
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("testtopic");
    }

    @Bean
    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }
}
