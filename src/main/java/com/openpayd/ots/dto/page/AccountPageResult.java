package com.openpayd.ots.dto.page;

import com.openpayd.ots.dto.AccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountPageResult extends PageResult {

    List<AccountDto> accountDtoList;

}
