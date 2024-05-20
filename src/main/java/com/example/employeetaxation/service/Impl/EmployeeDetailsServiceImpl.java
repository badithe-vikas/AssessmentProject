package com.example.employeetaxation.service.Impl;

import com.example.employeetaxation.common.CommonMessages;
import com.example.employeetaxation.common.CommonResponse;
import com.example.employeetaxation.model.entity.EmployeeDetails;
import com.example.employeetaxation.model.response.EmployeeResponse;
import com.example.employeetaxation.model.vo.EmployeeDetailsVO;
import com.example.employeetaxation.repository.EmployeeDetailsRepository;
import com.example.employeetaxation.service.EmployeeDetailsService;
import com.example.employeetaxation.transformers.EmployeeDetailsEntityToVoTransformer;
import com.example.employeetaxation.transformers.EmployeeDetailsVoToEntityTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final EmployeeDetailsEntityToVoTransformer employeeDetailsEntityToVoTransformer;

    private final EmployeeDetailsVoToEntityTransformer employeeDetailsVoToEntityTransformer;

    @Override
    public CommonResponse<?> getAllEmployeeDetails() {
        log.info("EmployeeDetailsServiceImpl : getAllEmployeeDetails is initiated ");

        List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();

        List<EmployeeDetailsVO> employeeDetailsVos = employeeDetails.stream().map(employeeDetailsEntityToVoTransformer::apply).collect(Collectors.toList());
        return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.SUCCESS_MESSAGE, employeeDetailsVos);
    }

    @Override
    public CommonResponse<?> saveEmployee(EmployeeDetailsVO employeeDetailsVO) {
        if (employeeDetailsVO != null) {
            EmployeeDetails employeeDetails = employeeDetailsVoToEntityTransformer.apply(employeeDetailsVO);
            EmployeeDetails details = employeeDetailsRepository.save(employeeDetails);
            return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.SAVE, employeeDetailsEntityToVoTransformer.apply(details));
        } else {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NULL_OBJECT);
        }
    }

    @Override
    public CommonResponse<?> deleteEmployee(Long id) {
        if (id == null) {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.INVALID_REQUEST);
        }
        Optional<EmployeeDetails> detailsOptional = employeeDetailsRepository.findById(id);
        if (detailsOptional.isPresent()) {
            employeeDetailsRepository.deleteById(id);
            return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.DELETE);
        } else {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NO_DATA_FOUND);
        }
    }

    @Override
    public CommonResponse<?> getEmployeeDetailsById(Long id) {
        if (id == null) {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.INVALID_REQUEST);
        }

        Optional<EmployeeDetails> detailsOptional = employeeDetailsRepository.findById(id);
        if (detailsOptional.isPresent()) {
            return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.GET, detailsOptional.get());
        } else {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NO_DATA_FOUND);
        }
    }

    @Override
    public CommonResponse<?> updateEmployee(EmployeeDetailsVO employeeDetailsVO) {
        if (employeeDetailsVO != null) {
            Optional<EmployeeDetails> detailsOptional = employeeDetailsRepository.findById(employeeDetailsVO.getEmployeeId());
            if (detailsOptional.isPresent()) {
                EmployeeDetails existingEmployee = detailsOptional.get();

                existingEmployee.setFirstName(employeeDetailsVO.getFirstName());
                existingEmployee.setLastName(employeeDetailsVO.getLastName());
                existingEmployee.setEmail(employeeDetailsVO.getEmail());
                List<String> mobileNumbersList = employeeDetailsVO.getMobileNumber();
                String mobileNumbersString = String.join(",", mobileNumbersList);
                existingEmployee.setMobileNumber(mobileNumbersString);
                existingEmployee.setDoj(employeeDetailsVO.getDoj());
                existingEmployee.setSalary(employeeDetailsVO.getSalary());
                EmployeeDetails employeeDetails = employeeDetailsRepository.save(existingEmployee);
                return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.UPDATE, employeeDetailsEntityToVoTransformer.apply(employeeDetails));
            } else {
                return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NO_DATA);
            }

        } else {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NULL_OBJECT);
        }
    }

    @Override
    public CommonResponse<?> getEmployeeTax(Long id) {
        if (id == null) {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.INVALID_REQUEST);
        }
        Optional<EmployeeDetails> employeeOptional = employeeDetailsRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            return new CommonResponse<>(CommonMessages.ERROR_CODE, CommonMessages.NO_DATA_FOUND);
        }

        EmployeeDetails employee = employeeOptional.get();
        double yearlySalary = calculateYearlySalary(employee);
        double taxAmount = calculateTax(yearlySalary);
        double cessAmount = calculateCess(yearlySalary);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setYearlySalary(yearlySalary);
        employeeResponse.setTaxAmount(taxAmount);
        employeeResponse.setCessAmount(cessAmount);

        return new CommonResponse<>(CommonMessages.SUCCESS_CODE, CommonMessages.GET, employeeResponse);
    }


    public double calculateTax(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return 12500 + (yearlySalary - 500000) * 0.10;
        } else {
            return 62500 + (yearlySalary - 1000000) * 0.20;
        }
    }

    public double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        } else {
            return 0;
        }
    }

    public double calculateYearlySalary(EmployeeDetails employee) {
        LocalDate startDate = employee.getDoj();
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 3, 31);

        if (startDate.isAfter(endDate)) {
            return 0;
        }

        long months = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1)) + 1;
        long days = ChronoUnit.DAYS.between(startDate, startDate.withDayOfMonth(startDate.lengthOfMonth()));

        return (employee.getSalary() * (months - 1)) + (employee.getSalary() * (days / 30.0));
    }

}
