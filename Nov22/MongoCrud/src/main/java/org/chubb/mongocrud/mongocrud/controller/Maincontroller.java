package org.chubb.mongocrud.mongocrud.controller;


import org.chubb.mongocrud.mongocrud.model.Student;
import org.chubb.mongocrud.mongocrud.service.Studservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Maincontroller {
    @Autowired
    Studservice studservice;

    @PostMapping("/student")
    public ResponseEntity<Student> addStudent(@RequestBody  Student student){
            Student s=studservice.addStudent(student);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(s);
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getStudents(){
        List<Student> ls= studservice.getStudents();
        return ResponseEntity.status(HttpStatus.OK).body(ls);
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id){
            Student s=studservice.getStudnet(id);
            if(s!=null){
                return ResponseEntity.status(HttpStatus.OK).body(s);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("its not found");
            }

    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody  Student student){
        Student s=studservice.update(id,student);
        if(s==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("its not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStud(@PathVariable int id){
       boolean s= studservice.deleleStud(id);
       if(s==true){
         return ResponseEntity.status(HttpStatus.OK).body("deleted");
       }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("its not found");
    }

}
