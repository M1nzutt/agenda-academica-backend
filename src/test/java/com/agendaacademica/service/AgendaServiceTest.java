package com.agendaacademica.service;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.agendaacademica.model.*;
import com.agendaacademica.repository.ActivityRepository;
class AgendaServiceTest {
 @Test void summaryCountsOverdueWithoutDoubleCountingCompleted(){
  ActivityRepository repository=mock(ActivityRepository.class);
  Activity pending=new Activity();pending.setStatus(ActivityStatus.PENDING);pending.setDueDate(LocalDate.now().minusDays(1));
  Activity completed=new Activity();completed.setStatus(ActivityStatus.COMPLETED);completed.setDueDate(LocalDate.now().minusDays(2));
  when(repository.findAll()).thenReturn(List.of(pending,completed));
  var summary=new AgendaService(repository).getSummary();
  assertEquals(2,summary.total());assertEquals(1,summary.pending());assertEquals(1,summary.completed());assertEquals(1,summary.overdue());
 }
}
