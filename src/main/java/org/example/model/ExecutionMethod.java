package org.example.model;

import lombok.Data;

/**
 * @author Erofeevskiy Yuriy on 06.12.2023
 */

@Data
public class ExecutionMethod {
    private String name;
    private long time;
    private String description;

    public ExecutionMethod(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void represent() {
        System.out.println(name + ". " + description); // TODO Delete
    }

    public void showExecutionTime() {
        System.out.println(name + " " + time + " ms."); // TODO Delete
    }
}
