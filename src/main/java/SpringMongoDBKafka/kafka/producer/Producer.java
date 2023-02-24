package SpringMongoDBKafka.kafka.producer;

import SpringMongoDBKafka.config.Constants;
import SpringMongoDBKafka.entity.Book;
import SpringMongoDBKafka.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class Producer {

    @Autowired
    private KafkaTemplate<String,Book> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Post> postKafkaTemplate;

    public Producer(KafkaTemplate<String,Book> kafkaTemplate, KafkaTemplate<String, Post> postKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.postKafkaTemplate = postKafkaTemplate;
    }

    public void sendMessage(Book book) {
        ListenableFuture<SendResult<String,Book>> feature =kafkaTemplate.send(Constants.TOPIC_NAME, book);
        feature.addCallback(new ListenableFutureCallback<SendResult<String,Book>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Error occured while sending",ex);
            }

            @Override
            public void onSuccess(SendResult<String,Book> result) {
                log.info("Send message successfully"+result);
            }
        });

    }

    public void sendMessage(Post post){
        ListenableFuture<SendResult<String, Post>> postFuture = postKafkaTemplate.send(Constants.POST_TOPIC_NAME, post);
        postFuture.addCallback(new ListenableFutureCallback<SendResult<String, Post>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Error occured while sending",ex);
            }

            @Override
            public void onSuccess(SendResult<String, Post> result) {
                log.info("Send message successfully"+ result);
            }
        });
    }
}
