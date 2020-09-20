package br.com.explorandoti.webflux.controller;

import br.com.explorandoti.webflux.data.Notification;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private int counter = 0;
    private final FluxProcessor<Notification, Notification> processor = DirectProcessor.<Notification>create().serialize();
    private final FluxSink<Notification> sink = processor.sink();

    @GetMapping(value = "/connection",  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ServerSentEvent<Notification>> streamEvents() {
        return processor.map(e -> ServerSentEvent.builder(e).build());
    }

    @GetMapping("/event")
    void streamEvent() {
        sink.next(Notification.builder()
                .id(ThreadLocalRandom.current().nextInt(100))
                .createdAt(LocalDateTime.now())
                .message("Hello World #" + ++counter)
                .build());
    }
}