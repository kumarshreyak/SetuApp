package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.service.ConsentService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consent/")
public class ConsentController {

    @Autowired
    ConsentService consentService;

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<String> createConsent(@NotBlank @PathVariable String mobileNumber) throws Exception {
        try {
            return consentService.createConsent(mobileNumber);
        } catch(Exception e) {
            log.error("Error creating consent" + e.getMessage());
            throw e;
        }
    }

//    @GetMapping("/get-data")
//    public ResponseEntity<String> getData() {
//        // todo
//    }
}
