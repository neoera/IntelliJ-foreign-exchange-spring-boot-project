package com.openpayd.ots.dto.page;

import com.openpayd.ots.dto.ClientDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientPageResult extends PageResult {

    List<ClientDto> clientDtoList;

}
