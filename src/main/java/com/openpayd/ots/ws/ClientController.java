package com.openpayd.ots.ws;

import com.openpayd.ots.dto.ClientDto;
import com.openpayd.ots.dto.page.ClientPageResult;
import com.openpayd.ots.entity.Client;
import com.openpayd.ots.exception.DuplicateDataFoundException;
import com.openpayd.ots.exception.NoDataFoundException;
import com.openpayd.ots.service.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Transfer System Controller
 */

@RestController
@RequestMapping("/client")
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Create a client", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createClient(@RequestBody ClientDto clientDto) {
        logger.info("creating new client: {}", clientDto);

        if (clientService.exists(clientDto)){
            logger.info("a client with name and surname " + clientDto.getName() + " " + clientDto.getSurname() + " already exists");
            throw new DuplicateDataFoundException(clientDto.getName() + " " + clientDto.getSurname());
        }
        Client client = clientService.create(clientDto);
        logger.info("new client create successful with Id: {}", client.getId());
        return ResponseEntity.accepted().body("new client created with id: " + client.getId());
    }

    @ApiOperation(value = "Get client", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getClient(@PathVariable long id) {
        logger.info("getting client with id: {}", id);
        ClientDto clientDto = clientService.get(id);

        if (clientDto == null){
            logger.info("client with id {} not found", id);
            throw new NoDataFoundException();
        }
        logger.info("client get successful with id: {}", id);
        return ResponseEntity.accepted().body(clientDto);
    }

    @ApiOperation(value = "View a list of available client", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity listClient(@PathVariable Integer page, @PathVariable Integer size) {
        logger.info("getting client list");
        ClientPageResult pageResult = clientService.list(page, size);
        logger.info("getting client list");
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


}