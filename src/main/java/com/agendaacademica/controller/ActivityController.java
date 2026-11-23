package com.agendaacademica.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.agendaacademica.dto.*;
import com.agendaacademica.model.*;
import com.agendaacademica.service.ActivityService;
@RestController @RequestMapping("/api/activities")
public class ActivityController {
 private final ActivityService service;
 public ActivityController(ActivityService service){this.service=service;}
 @GetMapping public List<Activity> getActivities(@RequestParam(required=false) String subjectId,@RequestParam(required=false) ActivityStatus status,@RequestParam(required=false) ActivityType type){return service.getActivities(subjectId,status,type);}
 @GetMapping("/{id}") public Activity getActivity(@PathVariable String id){return service.getActivity(id);}
 @PostMapping public ResponseEntity<Activity> createActivity(@Valid @RequestBody ActivityRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.createActivity(request));}
 @PutMapping("/{id}") public Activity updateActivity(@PathVariable String id,@Valid @RequestBody ActivityRequest request){return service.updateActivity(id,request);}
 @PatchMapping("/{id}/status") public Activity updateStatus(@PathVariable String id,@Valid @RequestBody StatusRequest request){return service.changeStatus(id,request.status());}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteActivity(@PathVariable String id){service.deleteActivity(id);}
}
