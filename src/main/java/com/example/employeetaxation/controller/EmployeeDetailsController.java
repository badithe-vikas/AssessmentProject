package com.example.employeetaxation.controller;

import com.example.employeetaxation.common.CommonResponse;
import com.example.employeetaxation.model.vo.EmployeeDetailsVO;
import com.example.employeetaxation.service.EmployeeDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/employee")
@Slf4j
@AllArgsConstructor
public class EmployeeDetailsController {

    private final EmployeeDetailsService employeeDetailsService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDetailsVO employeeDetailsVO) {
        CommonResponse<?> savedEmployee = employeeDetailsService.saveEmployee(employeeDetailsVO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() {
        CommonResponse<?> allEmployeeDetails = employeeDetailsService.getAllEmployeeDetails();
        return new ResponseEntity<>(allEmployeeDetails, HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        CommonResponse<?> employeeDetailsById = employeeDetailsService.getEmployeeDetailsById(id);
        return new ResponseEntity<>(employeeDetailsById, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
        CommonResponse<?> response = employeeDetailsService.deleteEmployee(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDetailsVO employeeDetailsVo) {
        CommonResponse<?> updateEmployee = employeeDetailsService.updateEmployee(employeeDetailsVo);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @GetMapping("/tax/{employeeId}")
    public ResponseEntity<?> getEmployeeTax(@PathVariable Long employeeId) {
        CommonResponse<?> employeeTax = employeeDetailsService.getEmployeeTax(employeeId);
        return new ResponseEntity<>(employeeTax, HttpStatus.OK);
    }


}
