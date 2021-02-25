package com.gds.api.service;

import org.springframework.http.ResponseEntity;

/**
 * @author Sujith Ramanathan
 */
public interface RFService {

    public ResponseEntity<String> sendPayloadToGdsEngine(String payload);
}
