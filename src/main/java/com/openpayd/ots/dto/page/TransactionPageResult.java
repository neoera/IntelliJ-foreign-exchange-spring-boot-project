package com.openpayd.ots.dto.page;

import com.openpayd.ots.dto.TransactionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionPageResult extends PageResult {

    List<TransactionDto> transactionDtoList;

}
