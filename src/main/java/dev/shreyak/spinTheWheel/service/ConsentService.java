package dev.shreyak.spinTheWheel.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ConsentService {

    public String getConsent(String id);
    public ResponseEntity<String> createConsent(String mobileNumber);

    }
