package SpringMongoDBKafka.controller;

import SpringMongoDBKafka.entity.Book;
import SpringMongoDBKafka.entity.Post;
import SpringMongoDBKafka.kafka.producer.Producer;
import SpringMongoDBKafka.repository.BookRepository;
import SpringMongoDBKafka.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    private Producer producer;

    public PostController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/save", produces = "application/json")
    public void save(@RequestBody Post post) {
        producer.sendMessage(post);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Post>> getAll() {
        List<Post> all = postRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
