package com.ceos.vote.global.healthCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

    @Value("${server.env}")
    private String env;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.serverAddress}")
    private String serverAddress;

    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("serverAddress", serverAddress);
        response.put("serverPort", serverPort);
        response.put("env", env);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {

        return ResponseEntity.ok(env);
    }
}