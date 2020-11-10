package com.getir.rig.service;

import com.getir.rig.dto.CustomerDto;
import com.getir.rig.dto.OrderDto;
import com.getir.rig.dto.OrderItemDto;
import com.getir.rig.dto.page.CustomerPageResult;
import com.getir.rig.dto.page.OrderPageResult;
import com.getir.rig.entity.Customer;
import com.getir.rig.entity.Order;
import com.getir.rig.exception.type.RecordNotFoundException;
import com.getir.rig.repository.CustomerRepository;
import com.getir.rig.repository.OrderRepository;
import com.getir.rig.util.RigCommon;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.viewobject.CustomerRequest;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private Mapper mapper = new DozerBeanMapper();
    private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Customer register(CustomerRequest customerRequest) {
        logger.info("creating new customer: {}", customerRequest);
        Customer customer = mapper.map(customerRequest, Customer.class);
        customer.setCreateDate(new Date());
        customer = customerRepository.save(customer);
        logger.info("new order created successfully with Id: {}", customer.getCustomerId());
        return customer;
    }

    public CustomerPageResult list(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        CustomerPageResult result = new CustomerPageResult();

        if (!customers.isEmpty()){
            List<CustomerDto> customerDtoList = new ArrayList<>();
            customers.forEach(customer -> {
                CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
                customerDtoList.add(customerDto);
            });

            result.setCustomerDtoList(customerDtoList);
            result.setTotalPages(customers.getTotalPages());
            result.setGetTotalElements(customers.getTotalElements());
        }
        return result;
    }

    public OrderPageResult customerOrderList(Long id, Pageable pageable) {
        logger.info("getting customer's order list");
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()){
            throw new RecordNotFoundException(RigValidationEnum.CUSTOMER_NOT_FOUND.getMessage());
        }
        Page<Order> orders = orderRepository.findByCustomer(customer.get(), pageable);
        OrderPageResult result = new OrderPageResult();

        List<OrderDto> orderDtoList = new ArrayList<>();
        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getOrderId());
            orderDto.setCustomerId(customer.get().getCustomerId());

            List<OrderItemDto> orderItemDtos = RigCommon.getOrderItemDtos(order);
            orderDto.setOrderItemDtos(orderItemDtos);

            orderDtoList.add(orderDto);
        });

        result.setOrderDtoList(orderDtoList);
        result.setTotalPages(orders.getTotalPages());
        result.setGetTotalElements(orders.getTotalElements());
        logger.info("customer's order list retrieved successfully");
        return result;
    }
}
