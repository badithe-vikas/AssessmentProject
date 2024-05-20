package com.example.employeetaxation.transformers;


import com.example.employeetaxation.model.entity.EmployeeDetails;
import com.example.employeetaxation.model.vo.EmployeeDetailsVO;
import com.example.employeetaxation.shared.AbstractCopyTransformer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EmployeeDetailsEntityToVoTransformer extends AbstractCopyTransformer<EmployeeDetails, EmployeeDetailsVO> {

    @Override
    public EmployeeDetailsVO apply(EmployeeDetails orig) {
        EmployeeDetailsVO employeeDetailsVO = super.apply(orig);
        employeeDetailsVO.setMobileNumber(Arrays.asList(orig.getMobileNumber().split(",")));
        return employeeDetailsVO;
    }
}
