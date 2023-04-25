package dev.shreyak.spinTheWheel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shreyak.spinTheWheel.model.Detail;
import dev.shreyak.spinTheWheel.repository.ConsentDao;
import dev.shreyak.spinTheWheel.util.Constants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ConsentServiceImpl implements ConsentService {


    private ConsentDao consentDao;

    private OkHttpClient httpClient;

    private ObjectMapper objectMapper;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ConsentServiceImpl(ConsentDao consentDao) {
        this.consentDao = consentDao;
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public String getConsent(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> createConsent(String mobileNumber) {
        Detail detail = Detail.create(mobileNumber, "redirect-url");
        String body = "";
        try {
            body = objectMapper.writeValueAsString(detail);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in creating consent detail");
        }

        Request request = new Request.Builder()
                .url(Constants.SETUP_API_URL + "/consents")
                .post(RequestBody.create(body, JSON))
                .addHeader("Content-Type", "application/json")
                .addHeader("x-client-id", "")
                .addHeader("x-client-secret", "")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonNode jsonResponse = objectMapper.readTree(response.body().string());
                String url = jsonResponse.get("url").asText();
                return ResponseEntity.ok(url);
            } else {
                return ResponseEntity.status(response.code()).body(response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in creating consent");
        }
    }
}

