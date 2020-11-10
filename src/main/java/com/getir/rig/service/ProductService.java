package com.getir.rig.service;

import com.getir.rig.dto.ProductDto;
import com.getir.rig.dto.page.ProductPageResult;
import com.getir.rig.entity.Product;
import com.getir.rig.exception.type.RecordNotFoundException;
import com.getir.rig.repository.ProductRepository;
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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(ProductRequest productRequest) {
        logger.info("creating new customer: {}", productRequest);
        Product product = mapper.map(productRequest, Product.class);
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
        return mapper.map(product, ProductDto.class);
    }

    public ProductPageResult list(Pageable pageable) {
        Page<Product> customers = productRepository.findAll(pageable);
        ProductPageResult result = new ProductPageResult();

        if (customers.getSize() > 0){
            List<ProductDto> customerDtoList = new ArrayList<>();
            customers.forEach(customer -> {
                ProductDto customerDto = mapper.map(customer, ProductDto.class);
                customerDtoList.add(customerDto);
            });

            result.setProductDtoList(customerDtoList);
            result.setTotalPages(customers.getTotalPages());
            result.setGetTotalElements(customers.getTotalElements());
        }
        return result;
    }

}
