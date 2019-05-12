package pl.potera.webclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientAPI {

    private WebClient webClient;

    public WebClientAPI() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/employees")
                .build();
    }

    public Mono<ResponseEntity<Employee>> postNewEmployee() {
        return webClient
                .post()
                .body(Mono.just(Employee.apiEmployee()), Employee.class)
                .exchange()
                .flatMap(response -> response.toEntity(Employee.class));
    }
}
