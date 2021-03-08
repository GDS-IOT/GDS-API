package com.gds.api.service.impl;

import com.gds.api.constants.Constants;
import com.gds.api.service.RFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Sujith Ramanathan
 */
@Service
public class RFServiceImpl implements RFService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RFServiceImpl.class);

    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Value("${" + Constants.GDS_ENGINE_HOST + "}")
    private String gdsEngineHost;

    @Value("${" + Constants.GDS_ENGINE_PORT + "}")
    private int gdsEnginePort;

    @Override
    public ResponseEntity<String> sendPayloadToGdsEngine(String payload) {
        if(Constants.SUCCESS.equalsIgnoreCase(sendToEngine(payload))) {
            return ResponseEntity.ok("Successfully sent to GDS-Engine");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sent device");
    }

    private String sendToEngine(String payload) {
        Socket serverSocket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            serverSocket = new Socket(gdsEngineHost, gdsEnginePort);
            serverSocket.setSoTimeout(10000);
            dos = new DataOutputStream(serverSocket.getOutputStream());
            baos.write(-1);
            baos.write(payload.getBytes());
            dos.write(baos.toByteArray());
            baos.reset();
            dis = new DataInputStream(serverSocket.getInputStream());
            byte[] resp = new byte[100];
            dis.read(resp);
            String response = new String(resp);
            LOGGER.debug("Respnose received from GDS-Engine : ", response);
            closeSocket(serverSocket);
            if (Constants.SUCCESS.equalsIgnoreCase(response)) {
                return Constants.SUCCESS;
            }
        } catch (IOException ie) {
            LOGGER.error("IOException occcurred while sending to GDS-Engine", ie);
            closeSocket(serverSocket);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while sending to GDS-Engine ", e);
            closeSocket(serverSocket);
        }
        return Constants.FAILED;
    }

    private void closeSocket(Socket serverSocket){
        if(null!= serverSocket && !serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
