package SpringMongoDBKafka.kafka.consumer;

import SpringMongoDBKafka.entity.Book;
import SpringMongoDBKafka.entity.Post;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerKafkaConfig {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${kafka.groupId}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Book> bookConsumerFactory() {
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        map.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        map.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(map);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Book> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String,Book> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(bookConsumerFactory());
        return kafkaListenerContainerFactory;
    }

    @Bean
    public ConsumerFactory<String, Post> postConsumerFactory(){
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        map.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        map.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(map);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory poKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Post> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(postConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }
}
