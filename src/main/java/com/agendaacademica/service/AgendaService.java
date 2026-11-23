package com.agendaacademica.service;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Service;
import com.agendaacademica.dto.AgendaSummary;
import com.agendaacademica.model.*;
import com.agendaacademica.repository.ActivityRepository;
@Service
public class AgendaService {
 private final ActivityRepository activities;
 public AgendaService(ActivityRepository activities){this.activities=activities;}
 public List<Activity> getOrderedActivities(){return activities.findAll().stream().sorted(Comparator.comparing(Activity::getDueDate).thenComparing(Activity::getTitle)).toList();}
 public AgendaSummary getSummary(){
  List<Activity> all=activities.findAll();LocalDate today=LocalDate.now();
  long pending=all.stream().filter(a->a.getStatus()==ActivityStatus.PENDING).count();
  long inProgress=all.stream().filter(a->a.getStatus()==ActivityStatus.IN_PROGRESS).count();
  long completed=all.stream().filter(a->a.getStatus()==ActivityStatus.COMPLETED).count();
  long overdue=all.stream().filter(a->a.getStatus()!=ActivityStatus.COMPLETED && a.getDueDate().isBefore(today)).count();
  return new AgendaSummary(all.size(),pending,inProgress,completed,overdue);
 }
}
