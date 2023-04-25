package dev.shreyak.spinTheWheel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.shreyak.spinTheWheel.model.ConsentRequest;
import dev.shreyak.spinTheWheel.model.Detail;
import dev.shreyak.spinTheWheel.model.SetuContext;
import dev.shreyak.spinTheWheel.repository.ConsentDao;
import dev.shreyak.spinTheWheel.util.Constants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ConsentServiceImpl implements ConsentService {


    private ConsentDao consentDao;

    private OkHttpClient httpClient;

    private ObjectMapper objectMapper;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ConsentServiceImpl(ConsentDao consentDao) {
        this.consentDao = consentDao;
        this.httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();

        this.objectMapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .defaultLeniency(true)
                .build();
    }


    @Override
    public String getConsent(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> createConsent(String mobileNumber) {
        Detail detail = Detail.create(mobileNumber, "redirect-url");
        ConsentRequest req = new ConsentRequest();
        req.setDetail(detail);
        ArrayList<SetuContext> arr = new ArrayList<>();
        arr.add(new SetuContext("accounttype", "CURRENT"));
        req.setContext(arr);
        req.setRedirectUrl("www.google.com");
        String body = "";
        try {
            body = objectMapper.writeValueAsString(req);
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
                return ResponseEntity.status(response.code()).body(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in creating consent");
        }
    }
}

