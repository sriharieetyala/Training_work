package org.chubb.mongocrud.mongocrud.repository;

import org.chubb.mongocrud.mongocrud.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Studentrepo extends MongoRepository<Student,Integer> {


}
