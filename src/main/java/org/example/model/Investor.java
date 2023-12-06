package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Erofeevskiy Yuriy on 04.12.2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investor {
    private String name;
    private List<Investment> investments;

    @Override
    public String toString() {
        return "Investor{" +
                "investments=" + investments.size() +
                '}';
    }
}
