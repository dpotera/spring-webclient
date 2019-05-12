package pl.potera.webclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;

    private String name;

    private Double points;

    public static Employee apiEmployee() {
        return new Employee(null, "API employee", 10d);
    }
}
