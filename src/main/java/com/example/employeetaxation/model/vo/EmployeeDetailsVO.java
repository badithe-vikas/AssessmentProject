package com.example.employeetaxation.model.vo;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDetailsVO {

    private Long employeeId;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @Email
    @NotBlank(message = "email is required")
    private String email;


    @NotEmpty(message = "mobile number should not be empty")
    private List<@NotBlank @Size(min = 10, max = 15) String> mobileNumber;

    @NotNull(message = "date of joining cannot be null")
    private LocalDate doj;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double salary;
}
