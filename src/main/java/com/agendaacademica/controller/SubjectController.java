package com.agendaacademica.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.agendaacademica.dto.SubjectRequest;
import com.agendaacademica.model.Subject;
import com.agendaacademica.service.SubjectService;
@RestController @RequestMapping("/api/subjects")
public class SubjectController {
 private final SubjectService service;
 public SubjectController(SubjectService service){this.service=service;}
 @GetMapping public List<Subject> getSubjects(){return service.getAllSubjects();}
 @GetMapping("/{id}") public Subject getSubject(@PathVariable String id){return service.getSubject(id);}
 @PostMapping public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.createSubject(request));}
 @PutMapping("/{id}") public Subject updateSubject(@PathVariable String id,@Valid @RequestBody SubjectRequest request){return service.updateSubject(id,request);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteSubject(@PathVariable String id){service.deleteSubject(id);}
}
