package pl.potera.webclient.examples;

import pl.potera.webclient.api.AsyncRestTemplateAPI;
import pl.potera.webclient.api.RestTemplateAPI;
import pl.potera.webclient.api.WebClientAPI;
import pl.potera.webclient.timeutils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Collectors;

public class CreateEmployees {

    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();

        TimeUtils.measureTime(() -> {

            for(int i = 0; i < Size.SIZE; i++) {
                webClient.postNewEmployee().block();
            }

        }, "map");
    }
}

class Size { final static int SIZE = 10000; }

class CreateEmployeesFlatMap {
    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();

        TimeUtils.measureTime(() ->

                        Flux.range(0, Size.SIZE)
                                .flatMap(number ->
                                        webClient.postNewEmployee()
                                                .subscribeOn(Schedulers.parallel())
                                )
                                .collect(Collectors.toList())
                                .block()

                , "flatMap");
    }
}

class CreateEmployeesRestTemplate {
    public static void main(String[] args) {
        RestTemplateAPI restTemplate = new RestTemplateAPI();

        TimeUtils.measureTime(() -> {

            for(int i = 0; i < Size.SIZE; i++) {
                restTemplate.postNewEmployee();
            }

        }, "restTemplate for");
    }
}

class CreateEmployeesRestTemplateFlux {
    public static void main(String[] args) {
        RestTemplateAPI restTemplate = new RestTemplateAPI();

        TimeUtils.measureTime(() ->

            Flux.range(0, Size.SIZE)
                    .flatMap(number ->
                            Mono.just(restTemplate.postNewEmployee())
                                    .subscribeOn(Schedulers.parallel())
                    )
                    .collect(Collectors.toList())
                    .block()

        , "restTemplate flatMap");
    }
}

class CreateEmployeesAsyncRestTemplateFlux {
    public static void main(String[] args) {
        AsyncRestTemplateAPI restTemplate = new AsyncRestTemplateAPI();

        TimeUtils.measureTime(() ->

            Flux.range(0, Size.SIZE)
                    .flatMap(number ->
                            Mono.fromFuture(restTemplate.postNewEmployee().completable())
                                    .subscribeOn(Schedulers.parallel())
                    )
                    .collect(Collectors.toList())
                    .block()

        , "asyncRestTemplate flatMap");
    }
}