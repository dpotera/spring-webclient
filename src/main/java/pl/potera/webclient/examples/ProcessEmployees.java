package pl.potera.webclient.examples;

import pl.potera.webclient.Employee;
import pl.potera.webclient.WebClientAPI;
import pl.potera.webclient.timeutils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessEmployees {

    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();

        TimeUtils.measureTime(() ->

                webClient.getAllEmployees()
                        .flatMap(employee -> {
                            Employee changedEmployee = new Employee(
                                    employee.getId(),
                                    employee.getName(),
                                    employee.getPoints() + 10d);
                            return webClient.updateEmployee(changedEmployee)
                                    .delayElement(Duration.ofMillis(10))
                                    .subscribeOn(Schedulers.parallel());
                        }).blockLast()

                , "processing flux");
    }
}

class ProcessEmployeesList {
    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();

        TimeUtils.measureTime(() -> {

            List<Employee> employees =
                    webClient.getAllEmployees()
                            .collectList()
                            .switchIfEmpty(Mono.just(new ArrayList<>()))
                            .block();

            Flux.fromIterable(employees)
                    .flatMap(employee -> {
                        Employee changedEmployee = new Employee(
                                employee.getId(),
                                employee.getName(),
                                employee.getPoints() + 10d);
                        return webClient.updateEmployee(changedEmployee)
                                .subscribeOn(Schedulers.parallel());
                    }).blockLast();

        }, "processing list");
    }
}

class AddEmployees {
    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();
        Flux.range(0, 100)
                .map(number -> webClient.postNewEmployee().block())
                .collect(Collectors.toList())
                .block();
    }
}
