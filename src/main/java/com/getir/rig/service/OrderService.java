package com.getir.rig.service;

import com.getir.rig.dto.OrderDto;
import com.getir.rig.dto.OrderItemDto;
import com.getir.rig.entity.*;
import com.getir.rig.exception.type.AvailableProductQuantityException;
import com.getir.rig.exception.type.RecordNotFoundException;
import com.getir.rig.exception.type.StockEmptyException;
import com.getir.rig.repository.*;
import com.getir.rig.util.RigCommon;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.viewobject.OrderRequest;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private Mapper mapper = new DozerBeanMapper();
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, StockRepository stockRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Order create(OrderRequest orderRequest) {
        logger.info("creating new order: {}", orderRequest);

        Order order = new Order();
        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
        if (!customer.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.CUSTOMER_NOT_FOUND.getMessage());
        }
        order.setCustomer(customer.get());
        order.setCreateDate(new Date());
        order = orderRepository.save(order);

        Order finalOrder = order;
        orderRequest.getOrderItemList().forEach(orderItemDto -> {
            Optional<Product> product = productRepository.findById(orderItemDto.getProductId());
            if (!product.isPresent()){
                throw new RecordNotFoundException(RigValidationEnum.PRODUCT_NOT_FOUND.getMessage());
            }

            if (product.get().getStock().getQuantity() < orderItemDto.getQuantity()){
                throw new AvailableProductQuantityException(RigValidationEnum.AVAILABLE_PRODUCT_QUANTITY.getMessage() + orderItemDto.getQuantity());
            }

            if (product.get().getStock().getQuantity() <= 0){
                throw new StockEmptyException(RigValidationEnum.STOCK_IS_EMPTY.getMessage() + product.get().getProductId());
            }

            Stock stock = product.get().getStock();
            stock.setQuantity(product.get().getStock().getQuantity() - orderItemDto.getQuantity());
            if (product.get().getStock().getQuantity() - orderItemDto.getQuantity() == 0){
                stock.setIsActive(false);
            }
            stockRepository.save(stock);

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setProduct(product.get());
            orderItem.setOrder(finalOrder);
            orderItemRepository.save(orderItem);
        });

        logger.info("new order created successfully with Id: {}", order.getOrderId());
        return order;
    }

    public OrderDto get(long id) {
        logger.info("getting order detail with order id: {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.ORDER_NOT_FOUND.getMessage());
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.get().getOrderId());
        orderDto.setCustomerId(order.get().getCustomer().getCustomerId());

        List<OrderItemDto> orderItemDtos = RigCommon.getOrderItemDtos(order.get());
        orderDto.setOrderItemDtos(orderItemDtos);

        logger.info("order get successful with id: {}", id);
        return orderDto;
    }


}
