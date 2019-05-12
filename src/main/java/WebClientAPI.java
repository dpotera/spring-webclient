import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientAPI {

    private WebClient webClient;

    WebClientAPI() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/employees")
                .build();
    }

    public static void main(String[] args) {
        WebClientAPI webClient = new WebClientAPI();
        webClient.postNewEmployee()
                .subscribe(System.out::println);

    }

    private Mono<ResponseEntity<Employee>> postNewEmployee() {
        return webClient
                .post()
                .body(Mono.just(new Employee(null, "API employee", 10d)), Employee.class)
                .exchange()
                .flatMap(response -> response.toEntity(Employee.class));
    }
}
