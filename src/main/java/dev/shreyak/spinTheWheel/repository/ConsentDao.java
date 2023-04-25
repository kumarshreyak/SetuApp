package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.ConsentRequest;

import java.util.List;

public interface ConsentDao {

    public void createConsent(ConsentRequest consentRequest);
    public ConsentRequest getConsent(String consentId);

}
