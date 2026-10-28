package com.example.platform;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of("service", "enterprise-demo", "status", "running");
    }

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello from the Enterprise Multi-Tool CI/CD Platform");
    }
}
