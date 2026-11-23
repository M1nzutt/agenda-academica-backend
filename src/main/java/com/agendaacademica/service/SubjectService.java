package com.agendaacademica.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.agendaacademica.dto.SubjectRequest;
import com.agendaacademica.model.Subject;
import com.agendaacademica.repository.*;
import com.agendaacademica.exception.*;
@Service
public class SubjectService {
 private final SubjectRepository subjects;
 private final ActivityRepository activities;
 public SubjectService(SubjectRepository subjects,ActivityRepository activities){this.subjects=subjects;this.activities=activities;}
 public List<Subject> getAllSubjects(){return subjects.findAll();}
 public Subject getSubject(String id){return subjects.findById(id).orElseThrow(()->new NotFoundException("Materia no encontrada"));}
 public Subject createSubject(SubjectRequest request){return subjects.save(new Subject(request.name(),request.code(),request.teacher()));}
 public Subject updateSubject(String id,SubjectRequest request){
  Subject s=getSubject(id);s.setName(request.name());s.setCode(request.code());s.setTeacher(request.teacher());return subjects.save(s);
 }
 public void deleteSubject(String id){
  getSubject(id);
  if(activities.existsBySubjectId(id)) throw new ConflictException("No se puede eliminar una materia que tiene actividades");
  subjects.deleteById(id);
 }
}
