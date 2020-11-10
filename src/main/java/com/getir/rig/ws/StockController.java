package com.getir.rig.ws;

import com.getir.rig.dto.StockDto;
import com.getir.rig.dto.page.StockPageResult;
import com.getir.rig.entity.Stock;
import com.getir.rig.service.StockService;
import com.getir.rig.util.BaseResponse;
import com.getir.rig.validation.annotation.RigRestLogger;
import com.getir.rig.viewobject.StockRequest;
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
 * Stock Controller
 */

@RestController
@RequestMapping("/stock")
@Api(value = "/stock")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('stock_create') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "create", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = "application/json;encoding=utf-8")
    public ResponseEntity<BaseResponse<Stock>> createStock(@Valid @RequestBody StockRequest stockRequest) {
        Stock stock = stockService.create(stockRequest);
        return ResponseEntity.ok(new BaseResponse<>(stock));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('stock_read') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "stockDetail", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "{id}")
    public ResponseEntity<BaseResponse<StockDto>> getDetail(@PathVariable long id) {
        StockDto stockDto = stockService.get(id);
        return ResponseEntity.ok(new BaseResponse<>(stockDto));
    }

    @RigRestLogger
    @PreAuthorize("#oauth2.hasScope('stock_list') and hasAuthority('ROLE_OPERATOR')")
    @ApiOperation(value = "View a list of stock", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/list")
    public ResponseEntity<BaseResponse<StockPageResult>> list(Pageable pageable) {
        StockPageResult pageResult = stockService.list(pageable);
        return ResponseEntity.ok(new BaseResponse<>(pageResult));
    }

}