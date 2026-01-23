package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.SystemApi;
import com.jasons.coffeewiki.model.SystemPingResponse;
import com.jasons.coffeewiki.model.SystemPingResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class SystemController implements SystemApi {

    @Autowired
    RestClient restClient;

    @Override
    public ResponseEntity<SystemPingResponseWrapper> systemPing() {
        Date curr = new Date();
        System.out.println(curr + " | System being called");
        SystemPingResponseWrapper wrapper = new SystemPingResponseWrapper();
        SystemPingResponse response = new SystemPingResponse();

        response.setMessage("System up and running ...");
        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

    @Scheduled(cron = "0 */5 * ? * *")
    public void callUserService(){
//        System.out.println("Cron job is running");
        restClient.get()
                .uri("/v1/system/ping")
                .retrieve()
                .body(SystemPingResponseWrapper.class);
    }
}
