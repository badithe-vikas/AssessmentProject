package com.example.employeetaxation.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<T> {

    private String message;
    private Integer statusCode;
    private T data;

    public CommonResponse(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


}
