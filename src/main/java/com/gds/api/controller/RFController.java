package com.gds.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sujith Ramanathan
 */
@RestController
@RequestMapping(value = "/backend")
public class RFController {

    private static Logger LOGGER = LoggerFactory.getLogger(RFController.class);

    @PostMapping(value="/rf")
    public ResponseEntity<String> sendToRf(@RequestBody String jsonPayload){
        LOGGER.debug("Coming here {}", jsonPayload);
        return ResponseEntity.ok("Success");
    }

}
