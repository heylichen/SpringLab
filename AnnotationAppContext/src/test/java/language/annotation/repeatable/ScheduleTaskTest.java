package language.annotation.repeatable;

import language.annotation.annotatedelement.AnnotatedElementTest;
import org.junit.Test;

public class ScheduleTaskTest {

  @Test
  public void name() {
    System.out.println(AnnotatedElementTest.determinePresent(Schedules.class, ScheduleTask.class));
    System.out.println(AnnotatedElementTest.determinePresent(Schedule.class, ScheduleTask.class));
  }
}