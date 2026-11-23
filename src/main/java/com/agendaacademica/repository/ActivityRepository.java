package com.agendaacademica.repository;
import com.agendaacademica.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ActivityRepository extends MongoRepository<Activity,String> {
 boolean existsBySubjectId(String subjectId);
}
