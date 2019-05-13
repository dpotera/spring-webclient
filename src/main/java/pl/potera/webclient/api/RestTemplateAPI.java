package pl.potera.webclient.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.potera.webclient.model.Employee;

import java.util.List;

public class RestTemplateAPI {

    private final String uri = "http://localhost:8081/employees";
    private RestTemplate restTemplate;

    public RestTemplateAPI() {
        this.restTemplate = new RestTemplate();
    }

    public Employee postNewEmployee() {
        return restTemplate.postForObject(uri, Employee.apiEmployee(), Employee.class);
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {}
        );
    }
}
