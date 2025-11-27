package org.chubb.mongoreactivecrud.mongoreactivecrud.Repository;

import org.chubb.mongoreactivecrud.mongoreactivecrud.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PersonRepo extends ReactiveMongoRepository<Person, String> {
    Mono<Person> findByEmail(String email);
}
