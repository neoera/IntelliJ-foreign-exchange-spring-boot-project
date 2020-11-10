package com.getir.rig.service;

import com.getir.rig.dto.ProductDto;
import com.getir.rig.dto.page.ProductPageResult;
import com.getir.rig.entity.Product;
import com.getir.rig.entity.Stock;
import com.getir.rig.exception.type.RecordNotFoundException;
import com.getir.rig.repository.ProductRepository;
import com.getir.rig.repository.StockRepository;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.viewobject.ProductRequest;
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
public class ProductService {

    private Mapper mapper = new DozerBeanMapper();
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Product create(ProductRequest productRequest) {
        logger.info("creating new customer: {}", productRequest);
        Product product = mapper.map(productRequest, Product.class);

        Optional<Stock> stock = stockRepository.findById(productRequest.getStockId());
        if (!stock.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.STOCK_NOT_FOUND.getMessage());
        }

        product.setStock(stock.get());
        product = productRepository.save(product);
        logger.info("new product created successfully with Id: {}", product.getProductId());
        return product;
    }

    public ProductDto get(long id) {
        logger.info("getting product detail with product id: {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.PRODUCT_NOT_FOUND.getMessage());
        }
        logger.info("product get successful with id: {}", id);
        ProductDto productDto = mapper.map(product.get(), ProductDto.class);
        productDto.setStockId(product.get().getStock().getStockId());
        return productDto;
    }

    public ProductPageResult list(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        ProductPageResult result = new ProductPageResult();

        if (!products.isEmpty()){
            List<ProductDto> productDtos = new ArrayList<>();
            products.forEach(product -> {
                ProductDto productDto = mapper.map(product, ProductDto.class);
                productDto.setStockId(product.getStock().getStockId());
                productDtos.add(productDto);
            });

            result.setProductDtoList(productDtos);
            result.setTotalPages(products.getTotalPages());
            result.setGetTotalElements(products.getTotalElements());
        }
        return result;
    }

}
