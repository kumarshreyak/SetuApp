package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.ConsentRequest;
import dev.shreyak.spinTheWheel.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class ConsentDaoImpl implements ConsentDao {

    ConsentRepository consentRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void createConsent(ConsentRequest consentRequest) {
        consentRepository.save(consentRequest);
    }

    @Override
    public ConsentRequest getConsent(String consentId) {
        List<ConsentRequest> reqs = mongoTemplate.find(Query.query(Criteria.where("{Detail.Customer.id}").is(consentId)), ConsentRequest.class);
        if(reqs != null && !reqs.isEmpty()) {
            return reqs.stream().filter(new Predicate<ConsentRequest>() {
                @Override
                public boolean test(ConsentRequest consentRequest) {
                    return DateUtils.isConsentExpiryValid(consentRequest.getDetail().getConsentExpiry());
                }
            }).findFirst().orElse(null);
        } else {
            return null;
        }
    }
}
