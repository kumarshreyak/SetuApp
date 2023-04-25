package dev.shreyak.spinTheWheel.repository;


import dev.shreyak.spinTheWheel.model.ConsentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsentRepository extends MongoRepository<ConsentRequest, String> {



}
