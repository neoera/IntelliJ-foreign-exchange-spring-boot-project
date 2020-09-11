package com.openpayd.ots.ws;

import com.openpayd.ots.dto.page.TransactionPageResult;
import com.openpayd.ots.entity.Transaction;
import com.openpayd.ots.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "View a list of available transaction", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity listAccountTransactions(@PathVariable Integer page, @PathVariable Integer size) {
        logger.info("getting all account transactions");
        TransactionPageResult pageResult = transactionService.list(page, size);

        if (pageResult.getGetTotalElements() <= 0){
            logger.info("no account transaction found");
            return new ResponseEntity<TransactionPageResult>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @ApiOperation(value = "Transfer money between two accounts", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transfer successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/transfer/{debitAccountId}/{creditAccountId}/{amount}/{transferType}", method = RequestMethod.POST)
    public ResponseEntity transfer(@PathVariable Long debitAccountId, @PathVariable Long creditAccountId,
                                   @PathVariable Double amount, @PathVariable Integer transferType, HttpSession session){
        logger.info("account transaction is started");
        Transaction transaction = transactionService.transfer(creditAccountId, debitAccountId, amount, transferType, session);
        logger.info("Account transaction is ended");
        return ResponseEntity.accepted().body("Account transaction is successful. Transaction Id: " + transaction.getId());
    }


}
