package com.openpayd.ots.ws;

import com.openpayd.ots.dto.AccountDto;
import com.openpayd.ots.dto.page.AccountPageResult;
import com.openpayd.ots.entity.Account;
import com.openpayd.ots.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create a account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody AccountDto accountDto, UriComponentsBuilder uriComponentsBuilder) {
        logger.info("creating new account: {}", accountDto);
        Account account = accountService.create(accountDto);
        logger.info("new account created with id: {}", account.getId());
        return ResponseEntity.accepted().body("new account created with id: " + account.getId());
    }

    @ApiOperation(value = "View a list of available client account", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity listClientAccount(@PathVariable Integer page, @PathVariable Integer size){
        logger.info("getting all client accounts");
        AccountPageResult pageResult = accountService.list(page, size);
        logger.info("all client accounts retrieved");
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }
}
