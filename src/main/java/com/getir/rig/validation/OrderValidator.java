package com.getir.rig.validation;

import com.getir.rig.dto.OrderDto;
import com.getir.rig.entity.Customer;
import com.getir.rig.entity.Order;
import com.getir.rig.entity.Product;
import com.getir.rig.entity.Stock;
import com.getir.rig.exception.RecordNotFoundException;
import com.getir.rig.logger.RigLogger;
import com.getir.rig.repository.CustomerRepository;
import com.getir.rig.repository.OrderRepository;
import com.getir.rig.repository.ProductRepository;
import com.getir.rig.util.RigConstants;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.util.Utils;
import com.getir.rig.validation.annotation.RigValidationComponent;
import com.getir.rig.viewobject.OrderRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.Map;
import java.util.Optional;

public class OrderValidator {

    private OrderValidator() { throw new IllegalStateException("OrderValidator IllegalStateException error "); }
    private static RigLogger logger = (RigLogger) LoggerFactory.getLogger(OrderValidator.class);

    @RigValidationComponent
    public static class CreateOrder implements Validator {

        private final ProductRepository productRepository;
        private final CustomerRepository customerRepository;

        @Autowired
        public CreateOrder(ProductRepository productRepository, CustomerRepository customerRepository) {
            this.productRepository = productRepository;
            this.customerRepository = customerRepository;
        }

        @Override
        public boolean supports(Class<?> clazz) {return OrderDto.class.equals(clazz);}

        @Override
        public void validate(Object target, Errors errors) {
            MapBindingResult mapBindingResult = (MapBindingResult) errors;

            //get method parameters
            Map<?, ?> parameters = mapBindingResult.getTargetMap();

            OrderRequest orderRequest = (OrderRequest) parameters.get("orderRequest");
            logger.info("order request object: {}", orderRequest);

            if (Utils.isLongNullOrZero(orderRequest.getCustomerId())){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getMessage());
            }

            Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
            if (!customer.isPresent()){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_NOT_FOUND.getKey(), RigValidationEnum.CUSTOMER_NOT_FOUND.getMessage());
            }

            if (orderRequest.getOrderItemVos() == null || orderRequest.getOrderItemVos().isEmpty()){
                mapBindingResult.reject(RigValidationEnum.ORDER_ITEM_LIST_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.ORDER_ITEM_LIST_CAN_NOT_BE_EMPTY.getMessage());
            }

            orderRequest.getOrderItemVos().forEach(orderItemVo -> {

                if (Utils.isLongNullOrZero(orderItemVo.getProductId())) {
                    mapBindingResult.reject(RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getMessage());
                    throw new RecordNotFoundException(RigConstants.PRODUCT);
                }

                Optional<Product> product = productRepository.findById(orderItemVo.getProductId());
                if (product.isPresent()){
                    Stock stock = product.get().getStock();

                    if (stock == null)
                        mapBindingResult.reject(RigValidationEnum.STOCK_NOT_FOUND.getKey(), RigValidationEnum.STOCK_NOT_FOUND.getMessage() + product.get().getProductId());

                    if (product.get().getStock().getQuantity() <= 0)
                        mapBindingResult.reject(RigValidationEnum.STOCK_IS_EMPTY.getKey(), RigValidationEnum.STOCK_IS_EMPTY.getMessage()+ product.get().getProductId());

                    if (product.get().getStock().getQuantity() < orderItemVo.getQuantity())
                        mapBindingResult.reject(RigValidationEnum.AVAILABLE_PRODUCT_QUANTITY.getKey(), RigValidationEnum.AVAILABLE_PRODUCT_QUANTITY.getMessage() + orderItemVo.getQuantity().toString());

                }else {
                    mapBindingResult.reject(RigValidationEnum.PRODUCT_NOT_FOUND.getKey(), RigValidationEnum.PRODUCT_NOT_FOUND.getMessage());
                }

            });

        }
    }

    @RigValidationComponent
    public static class ListOrder implements Validator {

        private final CustomerRepository customerRepository;

        public ListOrder(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

        @Override
        public boolean supports(Class<?> clazz) {return Long.class.equals(clazz);}

        @Override
        public void validate(Object target, Errors errors) {

            MapBindingResult mapBindingResult = (MapBindingResult) errors;

            //get method parameters
            Map<?, ?> parameters = mapBindingResult.getTargetMap();
            Long customerId = (Long) parameters.get("id");
            logger.info("order customer Id: {}", customerId);

            if (Utils.isLongNullOrZero(customerId)){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_ID_CAN_NOT_BE_EMPTY.getMessage());
            }

            Optional<Customer> customer = customerRepository.findById(customerId);
            if (!customer.isPresent()){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_NOT_FOUND.getKey(), RigValidationEnum.CUSTOMER_NOT_FOUND.getMessage());
            }

        }
    }

    @RigValidationComponent
    public static class GetOrderDetail implements Validator {

        private final OrderRepository orderRepository;

        public GetOrderDetail(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        @Override
        public boolean supports(Class<?> clazz) {return Long.class.equals(clazz);}

        @Override
        public void validate(Object target, Errors errors) {

            MapBindingResult mapBindingResult = (MapBindingResult) errors;

            //get method parameters
            Map<?, ?> parameters = mapBindingResult.getTargetMap();
            Long orderId = (Long) parameters.get("id");
            logger.info("order Id: {}", orderId);

            if (Utils.isLongNullOrZero(orderId)){
                mapBindingResult.reject(RigValidationEnum.ORDER_ID_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.ORDER_ID_CAN_NOT_BE_EMPTY.getMessage());
            }

            Optional<Order> order = orderRepository.findById(orderId);
            if (!order.isPresent()){
                mapBindingResult.reject(RigValidationEnum.ORDER_NOT_FOUND.getKey(), RigValidationEnum.ORDER_NOT_FOUND.getMessage());
            }

        }
    }

}
