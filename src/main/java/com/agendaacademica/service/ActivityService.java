package com.agendaacademica.service;
import java.util.*;
import org.springframework.stereotype.Service;
import com.agendaacademica.dto.ActivityRequest;
import com.agendaacademica.model.*;
import com.agendaacademica.repository.*;
import com.agendaacademica.exception.*;
@Service
public class ActivityService {
 private final ActivityRepository activities;
 private final SubjectRepository subjects;
 public ActivityService(ActivityRepository activities,SubjectRepository subjects){this.activities=activities;this.subjects=subjects;}
 public Activity getActivity(String id){return activities.findById(id).orElseThrow(()->new NotFoundException("Actividad no encontrada"));}
 public List<Activity> getActivities(String subjectId,ActivityStatus status,ActivityType type){
  return activities.findAll().stream()
   .filter(a->subjectId==null || subjectId.equals(a.getSubjectId()))
   .filter(a->status==null || status==a.getStatus())
   .filter(a->type==null || type==a.getType())
   .sorted(Comparator.comparing(Activity::getDueDate).thenComparing(Activity::getTitle))
   .toList();
 }
 private void validateSubject(String id){if(!subjects.existsById(id))throw new NotFoundException("Materia asociada no encontrada");}
 private void apply(Activity a,ActivityRequest request){
  validateSubject(request.subjectId());
  a.setSubjectId(request.subjectId());a.setTitle(request.title());a.setDescription(request.description());
  a.setType(request.type());a.setPriority(request.priority());a.setDueDate(request.dueDate());
 }
 public Activity createActivity(ActivityRequest request){
  Activity a=new Activity();apply(a,request);a.setStatus(ActivityStatus.PENDING);return activities.save(a);
 }
 public Activity updateActivity(String id,ActivityRequest request){
  Activity a=getActivity(id);apply(a,request);return activities.save(a);
 }
 public Activity changeStatus(String id,ActivityStatus status){
  Activity a=getActivity(id);a.setStatus(status);return activities.save(a);
 }
 public void deleteActivity(String id){getActivity(id);activities.deleteById(id);}
}
