package pl.bykowski.springwebfluxexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepo repo;

        Mono<String> doSomething() {

        Mono<String> objectMono = Mono.fromCallable(() -> {

                    log.info("Long running old method");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Long running old method ended");
                    return "Finish";
                }
        );

        return objectMono.subscribeOn(Schedulers.boundedElastic());

    }
}
