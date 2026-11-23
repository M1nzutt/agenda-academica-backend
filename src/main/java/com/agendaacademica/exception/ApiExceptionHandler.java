package com.agendaacademica.exception;
import java.time.Instant;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
@RestControllerAdvice
public class ApiExceptionHandler {
 private ResponseEntity<Map<String,Object>> response(HttpStatus status,String message){
  return ResponseEntity.status(status).body(Map.of("timestamp",Instant.now().toString(),"status",status.value(),"message",message));
 }
 @ExceptionHandler(NotFoundException.class)
 public ResponseEntity<Map<String,Object>> notFound(NotFoundException ex){return response(HttpStatus.NOT_FOUND,ex.getMessage());}
 @ExceptionHandler(ConflictException.class)
 public ResponseEntity<Map<String,Object>> conflict(ConflictException ex){return response(HttpStatus.CONFLICT,ex.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){
  String msg=ex.getBindingResult().getFieldErrors().stream().findFirst().map(e->e.getField()+": "+e.getDefaultMessage()).orElse("Datos inválidos");
  return response(HttpStatus.BAD_REQUEST,msg);
 }
 @ExceptionHandler(HttpMessageNotReadableException.class)
 public ResponseEntity<Map<String,Object>> badJson(HttpMessageNotReadableException ex){return response(HttpStatus.BAD_REQUEST,"JSON inválido o valor no permitido");}
 @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
 public ResponseEntity<Map<String,Object>> badParameter(Exception ex){return response(HttpStatus.BAD_REQUEST,"Parámetro de consulta inválido");}
}
