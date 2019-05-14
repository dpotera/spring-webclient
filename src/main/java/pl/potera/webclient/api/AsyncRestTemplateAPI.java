package pl.potera.webclient.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import pl.potera.webclient.model.Employee;

import java.util.List;

public class AsyncRestTemplateAPI {

    private final String uri = "http://localhost:8081/employees";
    private AsyncRestTemplate restTemplate;

    public AsyncRestTemplateAPI() {
        this.restTemplate = new AsyncRestTemplate();
    }

    public ListenableFuture<ResponseEntity<Employee>> postNewEmployee() {
        return restTemplate.postForEntity(uri, new HttpEntity<>(Employee.apiEmployee()), Employee.class);
    }

    public ListenableFuture<ResponseEntity<List<Employee>>> getAllEmployees() {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {}
        );
    }
}
