package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Erofeevskiy Yuriy on 04.12.2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Investment {
    private String from;
    private String to;
    private Long price;
}
