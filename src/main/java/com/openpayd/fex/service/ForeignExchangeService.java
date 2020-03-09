package com.openpayd.fex.service;

import com.openpayd.fex.dto.ConversionResult;
import com.openpayd.fex.dto.PageResult;
import com.openpayd.fex.entity.FexTransaction;
import com.openpayd.fex.exception.NotCompatibleException;
import com.openpayd.fex.exception.NotFoundException;
import com.openpayd.fex.exception.RequestException;
import com.openpayd.fex.repository.FexTransactionRepository;
import com.openpayd.fex.util.FexConstants;
import com.openpayd.fex.util.FexErrorMessages;
import com.openpayd.fex.util.FexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ForeignExchangeService {

    private final FexTransactionRepository fexTransactionRepository;

    @Autowired
    public ForeignExchangeService(FexTransactionRepository fexTransactionRepository) {
        this.fexTransactionRepository = fexTransactionRepository;
    }

    public Double getExchangeRate(String currencyPair) throws IOException, JSONException, NotFoundException, RequestException, NotCompatibleException {

        if (currencyPair.length() == 0 || currencyPair.length() != FexConstants.CURRENCY_PAIR_LENGTH){
            throw new NotCompatibleException(101, "Currency pair is not compatible");
        }

        final int mid = currencyPair.length() / 2; //get the middle of the String
        String[] currencyPairs = {currencyPair.substring(0, mid),currencyPair.substring(mid)};

        Currency sourceCurrency, targetCurrency;
        try {
            sourceCurrency = Currency.getInstance(currencyPairs[0]);
            targetCurrency = Currency.getInstance(currencyPairs[1]);
        }
        catch (IllegalArgumentException iae) {
            throw new NotCompatibleException(102, "Currency pair is not valid");
        }

        return getExchangeRate(sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
    }

    public ConversionResult conversion(Double sourceAmount, String sourceCurrency, String targetCurrency, HttpSession session) throws IOException, JSONException, NotFoundException, RequestException {
        Double exchangeRate = getExchangeRate(sourceCurrency,targetCurrency);
        Double targetCurrencyAmount = sourceAmount * exchangeRate;

        ConversionResult conversionResult = new ConversionResult();
        conversionResult.setTargetCurrencyAmount(targetCurrencyAmount);
        conversionResult.setTransactionUUID(session.getId());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        FexTransaction fexTransaction = new FexTransaction();
        fexTransaction.setTransactionUUID(conversionResult.getTransactionUUID());
        fexTransaction.setSourceCurrency(sourceCurrency);
        fexTransaction.setTargetCurrency(targetCurrency);
        fexTransaction.setTargetCurrencyAmount(targetCurrencyAmount);
        fexTransaction.setTransactionDate(date);
        fexTransactionRepository.save(fexTransaction);

        //setting session to expiry in 10 seconds for test
        session.setMaxInactiveInterval(10);

        return conversionResult;
    }

    private Double getExchangeRate(String sourceCurrency, String targetCurrency) throws IOException, JSONException, NotFoundException, RequestException {
        URL url = new URL(FexConstants.RATES_API_BASE_URL+sourceCurrency+"&symbols="+targetCurrency);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("User-Agent", FexConstants.USER_AGENT_INFO);
        httpURLConnection.connect();
        if (HttpStatus.OK.value() == (httpURLConnection.getResponseCode())){
            // Convert to JSON Object
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream) httpURLConnection.getContent()));
            if(bufferedReader.ready()) {
                String jsonText = FexUtils.readAll(bufferedReader);
                JSONObject obj = new JSONObject(jsonText);
                JSONObject jsonChildObject = (JSONObject)obj.get("rates");
                return Double.valueOf(new DecimalFormat("###.###").format(jsonChildObject.get(targetCurrency)));
            }else{
                throw new NotFoundException(Response.Status.NOT_FOUND.getStatusCode(), FexErrorMessages.RATES_NOT_FOUND);
            }
        }else {
            throw new RequestException(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
        }
    }

    public PageResult conversionList(String transactionUUID, Integer page, Integer size) throws NotCompatibleException {
        if (!FexUtils.isStringNullOrEmpty(transactionUUID)) {
            Pageable pageable = PageRequest.of(page, size);
            Page<FexTransaction> fexTransactions = fexTransactionRepository.findByTransactionUUID(transactionUUID, pageable);

            PageResult pageResult = new PageResult();
            List<ConversionResult> conversionResults = new ArrayList<>();
            
            if (!fexTransactions.isEmpty()){
                for (FexTransaction fexTransaction : fexTransactions) {
                    ConversionResult conversionResult = new ConversionResult();
                    conversionResult.setTransactionUUID(fexTransaction.getTransactionUUID());
                    conversionResult.setTargetCurrencyAmount(fexTransaction.getTargetCurrencyAmount());
                    conversionResults.add(conversionResult);
                }
                pageResult.setConversionResults(conversionResults);
                pageResult.setTotalPages(fexTransactions.getTotalPages());
                pageResult.setGetTotalElements(fexTransactions.getTotalElements());
            }

            return pageResult;

        }else{
            throw new NotCompatibleException(102, FexErrorMessages.TRANSACTION_UUID_EMPTY_ERROR_MESSAGE);
        }
    }
}
