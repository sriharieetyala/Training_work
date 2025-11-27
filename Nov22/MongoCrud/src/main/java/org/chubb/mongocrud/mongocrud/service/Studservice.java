package org.chubb.mongocrud.mongocrud.service;

import org.chubb.mongocrud.mongocrud.model.Student;
import org.chubb.mongocrud.mongocrud.repository.Studentrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Studservice {
    @Autowired
    Studentrepo studentrepo;

    public Student addStudent(Student student) {
        return studentrepo.save(student);
    }

    public List<Student> getStudents() {
        return studentrepo.findAll();
    }

    public Student getStudnet(int id) {
        return studentrepo.findById(id).orElse(null);
    }

    public Student update(int id, Student student) {
        return studentrepo.save(student);

    }

    public boolean deleleStud(int id) {
       if(!studentrepo.existsById(id)){
           return false;
       }
       studentrepo.deleteById(id);
       return true;
    }
}
