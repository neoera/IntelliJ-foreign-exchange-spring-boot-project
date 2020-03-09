package com.openpayd.fex.rest.api;

import com.openpayd.fex.dto.ConversionResult;
import com.openpayd.fex.dto.PageResult;
import com.openpayd.fex.exception.NotCompatibleException;
import com.openpayd.fex.exception.RequestException;
import com.openpayd.fex.exception.NotFoundException;
import com.openpayd.fex.service.ForeignExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Foreign Exchange Controller
 */

@RestController
public class ForeignExchangeController {

    private final ForeignExchangeService foreignExchangeService;

    @Autowired
    public ForeignExchangeController(ForeignExchangeService foreignExchangeService) {
        this.foreignExchangeService = foreignExchangeService;
    }

    //@RestLogger
    @GetMapping("/exchangeRate/{currencyPair}")
    Double getExchangeRate(@PathVariable String currencyPair) throws IOException, JSONException, NotFoundException, RequestException, NotCompatibleException {
        return foreignExchangeService.getExchangeRate(currencyPair);
    }

    @GetMapping("/conversion/{sourceAmount}/{sourceCurrency}/{targetCurrency}")
    ConversionResult conversion(@PathVariable Double sourceAmount,
                                @PathVariable String sourceCurrency,
                                @PathVariable String targetCurrency,
                                HttpSession session) throws IOException, JSONException, NotFoundException, RequestException {
        return foreignExchangeService.conversion(sourceAmount, sourceCurrency, targetCurrency, session);
    }

    @GetMapping("/conversionList/{transactionId}/{page}/{size}")
    PageResult conversionList(@PathVariable String transactionId,
                              @PathVariable Integer page,
                              @PathVariable Integer size) throws NotCompatibleException {
        return foreignExchangeService.conversionList(transactionId, page, size);
    }

}