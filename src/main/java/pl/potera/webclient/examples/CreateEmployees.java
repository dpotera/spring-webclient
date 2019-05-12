package pl.potera.webclient.examples;

import pl.potera.webclient.WebClientAPI;
import pl.potera.webclient.timeutils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Collectors;

public class CreateEmployees {

    private final static int SIZE = 100;

    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();

        TimeUtils.measureTime(() -> {

            for(int i = 0; i < SIZE; i++) {
                webClient.postNewEmployee().block();
            }

        }, "map");


        TimeUtils.measureTime(() ->

            Flux.range(0, SIZE)
                    .flatMap(number ->
                            webClient.postNewEmployee()
                                    .subscribeOn(Schedulers.parallel())
                    )
                    .collect(Collectors.toList())
                    .block()

        , "flatMap");
    }
}