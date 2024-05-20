package com.example.employeetaxation.model.response;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private Double yearlySalary;
    private Double taxAmount;
    private Double cessAmount;

}
