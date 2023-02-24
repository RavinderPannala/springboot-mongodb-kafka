package SpringMongoDBKafka.controller;

import SpringMongoDBKafka.entity.Book;
import SpringMongoDBKafka.kafka.producer.Producer;
import SpringMongoDBKafka.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    private Producer producer;
    public BookController(Producer producer){
        this.producer= producer;
    }

    @PostMapping(value = "/save", produces = "application/json")
    public void save(@RequestBody Book book) {
       producer.sendMessage(book);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
