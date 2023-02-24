package SpringMongoDBKafka.kafka.consumer;

import SpringMongoDBKafka.config.Constants;
import SpringMongoDBKafka.entity.Book;
import SpringMongoDBKafka.entity.Post;
import SpringMongoDBKafka.repository.BookRepository;
import SpringMongoDBKafka.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PostRepository postRepository;

    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.BOOK_GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    private void consume(Book book) {
        bookRepository.insert(book);
    }

    @KafkaListener(topics = Constants.POST_TOPIC_NAME, groupId = Constants.POST_GROUP_ID, containerFactory = "poKafkaListenerContainerFactory")
    private void consume(Post post) {
        postRepository.insert(post);
    }
}
