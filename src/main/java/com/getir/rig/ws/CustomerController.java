package com.getir.rig.ws;

import com.getir.rig.dto.page.CustomerPageResult;
import com.getir.rig.dto.page.OrderPageResult;
import com.getir.rig.entity.Customer;
import com.getir.rig.service.CustomerService;
import com.getir.rig.util.BaseResponse;
import com.getir.rig.validation.CustomerValidator;
import com.getir.rig.validation.OrderValidator;
import com.getir.rig.validation.annotation.RigRestLogger;
import com.getir.rig.validation.annotation.RigValidator;
import com.getir.rig.viewobject.CustomerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

/**
 * Customer Controller
 */

@RestController
@RequestMapping("/customer")
@Api(value = "/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*@InitBinder
    protected void initBinder(final WebDataBinder binder){
        binder.addValidators(customerValidator);
    }*/

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('customer_register') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "register", notes = "Register a customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer successfully registered"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = "application/json;encoding=utf-8")
    public ResponseEntity<BaseResponse<Customer>> register(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.register(customerRequest);
        return ResponseEntity.ok(new BaseResponse<>(customer));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('customer_read') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "list", notes = "List customers", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/list", consumes = MediaType.APPLICATION_JSON, produces = "application/json;encoding=utf-8")
    @RigValidator(validator = CustomerValidator.ListCustomer.class)
    public ResponseEntity<BaseResponse<CustomerPageResult>> listCustomer(Pageable pageable){
        CustomerPageResult pageResult = customerService.list(pageable);
        return ResponseEntity.ok(new BaseResponse<>(pageResult));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('order_list') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "View a list of customer's order", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{id}/order")
    @RigValidator(validator = OrderValidator.ListOrder.class)
    public ResponseEntity<BaseResponse<OrderPageResult>> listCustomerOrder(@PathVariable Long id, Pageable pageable) {
        OrderPageResult pageResult = customerService.customerOrderList(id, pageable);
        return ResponseEntity.ok(new BaseResponse<>(pageResult));
    }
}
