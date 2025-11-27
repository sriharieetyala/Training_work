package org.chubb.mongocrud.mongocrud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Student {
     private  String name;
     @Id
     private int id;
     private String email;

}
