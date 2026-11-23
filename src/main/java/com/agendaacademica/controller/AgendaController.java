package com.agendaacademica.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.agendaacademica.dto.AgendaSummary;
import com.agendaacademica.model.Activity;
import com.agendaacademica.service.AgendaService;
@RestController @RequestMapping("/api/agenda")
public class AgendaController {
 private final AgendaService service;
 public AgendaController(AgendaService service){this.service=service;}
 @GetMapping public List<Activity> getAgenda(){return service.getOrderedActivities();}
 @GetMapping("/summary") public AgendaSummary getSummary(){return service.getSummary();}
}
