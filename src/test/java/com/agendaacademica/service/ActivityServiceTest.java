package com.agendaacademica.service;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.agendaacademica.dto.ActivityRequest;
import com.agendaacademica.model.*;
import com.agendaacademica.repository.*;
import com.agendaacademica.exception.NotFoundException;
class ActivityServiceTest {
 @Test void cannotCreateActivityForMissingSubject(){
  var activities=mock(ActivityRepository.class);var subjects=mock(SubjectRepository.class);
  when(subjects.existsById("not-found")).thenReturn(false);
  var service=new ActivityService(activities,subjects);
  var request=new ActivityRequest("not-found","Parcial",null,ActivityType.EXAM,Priority.HIGH,LocalDate.now().plusDays(1));
  assertThrows(NotFoundException.class,()->service.createActivity(request));
  verify(activities,never()).save(any());
 }
}
