package com.agendaacademica.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="subjects")
public class Subject {
 @Id private String id;
 private String name;
 private String code;
 private String teacher;
 public Subject() {}
 public Subject(String name,String code,String teacher) {this.name=name;this.code=code;this.teacher=teacher;}
 public String getId(){return id;}
 public String getName(){return name;} public void setName(String value){name=value;}
 public String getCode(){return code;} public void setCode(String value){code=value;}
 public String getTeacher(){return teacher;} public void setTeacher(String value){teacher=value;}
}
