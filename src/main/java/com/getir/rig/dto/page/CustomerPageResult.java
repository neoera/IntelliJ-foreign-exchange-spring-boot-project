package com.getir.rig.dto.page;

import com.getir.rig.dto.CustomerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerPageResult extends PageResult {

    List<CustomerDto> customerDtoList;

}
