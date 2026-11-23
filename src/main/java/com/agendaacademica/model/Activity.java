package com.agendaacademica.model;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="activities")
public class Activity {
 @Id private String id;
 private String subjectId;
 private String title;
 private String description;
 private ActivityType type;
 private Priority priority;
 private LocalDate dueDate;
 private ActivityStatus status;
 public Activity() {}
 public String getId(){return id;}
 public String getSubjectId(){return subjectId;} public void setSubjectId(String value){subjectId=value;}
 public String getTitle(){return title;} public void setTitle(String value){title=value;}
 public String getDescription(){return description;} public void setDescription(String value){description=value;}
 public ActivityType getType(){return type;} public void setType(ActivityType value){type=value;}
 public Priority getPriority(){return priority;} public void setPriority(Priority value){priority=value;}
 public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate value){dueDate=value;}
 public ActivityStatus getStatus(){return status;} public void setStatus(ActivityStatus value){status=value;}
}
