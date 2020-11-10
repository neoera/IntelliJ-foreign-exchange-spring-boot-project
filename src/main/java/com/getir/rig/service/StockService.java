package com.getir.rig.service;

import com.getir.rig.dto.StockDto;
import com.getir.rig.dto.page.StockPageResult;
import com.getir.rig.entity.Stock;
import com.getir.rig.exception.type.RecordNotFoundException;
import com.getir.rig.repository.StockRepository;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.viewobject.StockRequest;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private Mapper mapper = new DozerBeanMapper();
    private static Logger logger = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Stock create(StockRequest stockRequest) {
        logger.info("creating new stock: {}", stockRequest);
        Stock stock = mapper.map(stockRequest, Stock.class);
        stock = stockRepository.save(stock);
        logger.info("new stock created successfully with Id: {}", stock.getStockId());
        return stock;
    }

    public StockPageResult list(Pageable pageable) {
        Page<Stock> stocks = stockRepository.findAll(pageable);
        StockPageResult result = new StockPageResult();

        if (stocks.getSize() > 0){
            List<StockDto> stockDtoList = new ArrayList<>();
            stocks.forEach(stock -> {
                StockDto stockDto = mapper.map(stock, StockDto.class);
                stockDtoList.add(stockDto);
            });

            result.setStockDtoList(stockDtoList);
            result.setTotalPages(stocks.getTotalPages());
            result.setGetTotalElements(stocks.getTotalElements());
        }
        return result;
    }

    public StockDto get(long id) {
        logger.info("getting stock detail with stock id: {}", id);
        Optional<Stock> stock = stockRepository.findById(id);
        if (!stock.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.STOCK_NOT_FOUND.getMessage());
        }
        logger.info("stock get successful with id: {}", id);
        return mapper.map(stock, StockDto.class);
    }

}
