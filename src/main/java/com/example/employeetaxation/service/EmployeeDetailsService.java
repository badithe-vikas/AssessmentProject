package com.example.employeetaxation.service;

import com.example.employeetaxation.common.CommonResponse;
import com.example.employeetaxation.model.vo.EmployeeDetailsVO;

public interface EmployeeDetailsService {

    CommonResponse<?> getAllEmployeeDetails();

    CommonResponse<?> saveEmployee(EmployeeDetailsVO employeeDetailsVO);

    CommonResponse<?> deleteEmployee(Long id);

    CommonResponse<?> getEmployeeDetailsById(Long id);

    CommonResponse<?> updateEmployee(EmployeeDetailsVO employeeDetailsVO);

    CommonResponse<?> getEmployeeTax(Long id);
}
