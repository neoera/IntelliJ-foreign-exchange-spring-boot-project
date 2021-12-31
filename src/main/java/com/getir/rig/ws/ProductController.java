package com.getir.rig.ws;

import com.getir.rig.dto.ProductDto;
import com.getir.rig.dto.page.OrderPageResult;
import com.getir.rig.dto.page.ProductPageResult;
import com.getir.rig.entity.Product;
import com.getir.rig.service.ProductService;
import com.getir.rig.util.BaseResponse;
import com.getir.rig.validation.annotation.RigRestLogger;
import com.getir.rig.viewobject.ProductRequest;
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
 * Product Controller
 */

@RestController
@RequestMapping("product")
@Api(value = "product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('product_create') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "create", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = "application/json;encoding=utf-8")
    public ResponseEntity<BaseResponse<Product>> create(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.create(productRequest);
        return ResponseEntity.ok(new BaseResponse<>(product));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('product_read') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "productDetail", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "{id}")
    public ResponseEntity<BaseResponse<ProductDto>> getProductDetail(@PathVariable long id) {
        ProductDto productDto = productService.get(id);
        return ResponseEntity.ok(new BaseResponse<>(productDto));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('product_list') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "View a list of product", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/list")
    public ResponseEntity<BaseResponse<ProductPageResult>> list(Pageable pageable) {
        ProductPageResult pageResult = productService.list(pageable);
        return ResponseEntity.ok(new BaseResponse<>(pageResult));
    }

}