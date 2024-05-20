package com.example.employeetaxation.transformers;

import com.example.employeetaxation.model.entity.EmployeeDetails;
import com.example.employeetaxation.model.vo.EmployeeDetailsVO;
import com.example.employeetaxation.shared.AbstractCopyTransformer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDetailsVoToEntityTransformer extends AbstractCopyTransformer<EmployeeDetailsVO, EmployeeDetails> {
    @Override
    public EmployeeDetails apply(EmployeeDetailsVO orig) {
        EmployeeDetails employeeDetails = super.apply(orig);
        List<String> mobileNumbersList = orig.getMobileNumber();
        String mobileNumbersString = String.join(",", mobileNumbersList);
        employeeDetails.setMobileNumber(mobileNumbersString);
        return employeeDetails;
    }
}
