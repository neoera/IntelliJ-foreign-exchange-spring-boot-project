package com.getir.rig.service;

import com.getir.rig.dto.OrderDto;
import com.getir.rig.dto.OrderItemDto;
import com.getir.rig.dto.page.OrderPageResult;
import com.getir.rig.entity.*;
import com.getir.rig.exception.RecordNotFoundException;
import com.getir.rig.repository.CustomerRepository;
import com.getir.rig.repository.OrderRepository;
import com.getir.rig.repository.ProductRepository;
import com.getir.rig.repository.StockRepository;
import com.getir.rig.util.RigValidationEnum;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private Mapper mapper = new DozerBeanMapper();
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Order create(OrderDto orderDto) {
        logger.info("creating new order: {}", orderDto);

        Order order = new Order();
        Optional<Customer> customer = customerRepository.findById(orderDto.getCustomerId());
        if (!customer.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.CUSTOMER_NOT_FOUND.getMessage());
        }
        order.setCustomer(customer.get());

        List<OrderItem> orderItems = new ArrayList<>();
        orderDto.getOrderItemDtos().forEach(orderItemDto -> {
            Product product = productRepository.getOne(orderItemDto.getProductId());

            Stock stock = product.getStock();
            stock.setQuantity(product.getStock().getQuantity() - orderItemDto.getQuantity());
            if (product.getStock().getQuantity() - orderItemDto.getQuantity() == 0){
                stock.setIsActive(false);
            }
            stockRepository.save(stock);

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setProduct(product);
            orderItems.add(orderItem);
        });

        order.setOrderItems(orderItems);
        order.setCreateDate(new Date());
        order = orderRepository.save(order);
        logger.info("new order created successfully with Id: {}", order.getOrderId());
        return order;
    }

    public OrderDto get(long id) {
        logger.info("getting order detail with order id: {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.ORDER_NOT_FOUND.getMessage());
        }
        logger.info("order get successful with id: {}", id);
        return mapper.map(order, OrderDto.class);
    }

}
