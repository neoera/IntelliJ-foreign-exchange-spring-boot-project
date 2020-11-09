package com.getir.rig.ws;

import com.getir.rig.dto.OrderDto;
import com.getir.rig.entity.Order;
import com.getir.rig.service.OrderService;
import com.getir.rig.util.BaseResponse;
import com.getir.rig.validation.OrderValidator;
import com.getir.rig.validation.annotation.RigRestLogger;
import com.getir.rig.validation.annotation.RigValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Order Controller
 */

@RestController
@RequestMapping("/order")
@Api(value = "/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('order_create') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "create", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping
    @RigValidator(validator = OrderValidator.CreateOrder.class)
    public ResponseEntity<BaseResponse<Order>> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderService.create(orderDto);
        return ResponseEntity.ok(new BaseResponse<>(order));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('order_read') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "orderDetail", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "{id}")
    @RigValidator(validator = OrderValidator.GetOrderDetail.class)
    public ResponseEntity<BaseResponse<OrderDto>> getOrderDetail(@PathVariable long id) {
        OrderDto orderDto = orderService.get(id);
        return ResponseEntity.ok(new BaseResponse<>(orderDto));
    }

}