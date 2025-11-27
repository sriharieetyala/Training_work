package org.chubb.mongoreactivecrud.mongoreactivecrud.controller;

import org.chubb.mongoreactivecrud.mongoreactivecrud.model.Person;
import org.chubb.mongoreactivecrud.mongoreactivecrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    PersonService service;

//        public PersonController(PersonService service) {
//            this.service = service;
//        }

        // CREATE
        @PostMapping
        public Mono<ResponseEntity<Person>> createPerson(@RequestBody Person requestBody) {
            // first assign the object (example of building/assigning)
            Person toSave = new Person();
            toSave.setName(requestBody.getName());
            toSave.setAge(requestBody.getAge());
            toSave.setEmail(requestBody.getEmail());

            // pass to service and wrap result in ResponseEntity
            return service.create(toSave)
                    .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved));
        }

        // READ ALL
        @GetMapping
        public ResponseEntity<Flux<Person>> getAll() {
            Flux<Person> all = service.findAll();
            return ResponseEntity.ok(all);
        }

        // READ by id
        @GetMapping("/{id}")
        public Mono<ResponseEntity<Person>> getById(@PathVariable String id) {
            return service.findById(id)
                    .map(person -> ResponseEntity.ok(person))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }

        // UPDATE (replace)
        @PutMapping("/{id}")
        public Mono<ResponseEntity<Person>> updatePerson(@PathVariable String id,
                                                         @RequestBody Person body) {
            // assign then pass
            Person toUpdate = new Person();
            toUpdate.setName(body.getName());
            toUpdate.setAge(body.getAge());
            toUpdate.setEmail(body.getEmail());

            return service.findById(id)
                    .flatMap(existing -> service.update(id, toUpdate))
                    .map(updated -> ResponseEntity.ok(updated))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }

        // DELETE
        @DeleteMapping("/{id}")
        public Mono<ResponseEntity<Void>> deletePerson(@PathVariable String id) {
            return service.findById(id)
                    .flatMap(existing ->
                            service.delete(id).then(Mono.just(ResponseEntity.noContent().<Void>build()))
                    )
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }
    }

