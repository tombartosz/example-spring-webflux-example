package pl.bykowski.springwebfluxexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;


@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentApi {
    private final  StudentRepo studentRepo;
    private final StudentService service;


    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> get() {
        return studentRepo.findAll().delayElements(Duration.ofMillis(100));
    }

    @PostMapping
    public Mono<String> create(@RequestBody Student student) {
        log.debug("Adding student: " + student.getName());
        studentRepo.save(student).subscribe(s -> log.info("Student added: " + s.getName()));
        return Mono.just("accepted");
    }

    @GetMapping(path =  "/oldMethod", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<String> oldMethod() {
        return service.doSomething();
    }
}
