package org.chubb.mongoreactivecrud.mongoreactivecrud.service;

import org.chubb.mongoreactivecrud.mongoreactivecrud.Repository.PersonRepo;
import org.chubb.mongoreactivecrud.mongoreactivecrud.model.Person;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final PersonRepo repo;

    public PersonService(PersonRepo repo) {
        this.repo = repo;
    }

    public Mono<Person> create(Person p) {
        // raw save
        return repo.save(p);
    }

    public Flux<Person> findAll() {
        return repo.findAll();
    }

    public Mono<Person> findById(String id) {
        return repo.findById(id);
    }

    public Mono<Person> update(String id, Person updated) {
        // ensure id is set on the object, then save (replace)
        updated.setId(id);
        return repo.save(updated);
    }

    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}