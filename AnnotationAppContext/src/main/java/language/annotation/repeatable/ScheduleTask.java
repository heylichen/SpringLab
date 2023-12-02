package language.annotation.repeatable;

import org.springframework.core.annotation.RepeatableContainers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

@Schedule(dayOfMonth = "last")
@Schedule(dayOfWeek = "Fri")
public class ScheduleTask {
  public static void main(String[] args) throws Exception {
    Schedules containerInstance = ScheduleTask.class.getDeclaredAnnotation(Schedules.class);

    Method m = RepeatableContainers.class.getDeclaredMethod("findRepeatedAnnotations", Annotation.class);
    m.setAccessible(true);

    Annotation[] repeatableArr = (Annotation[]) m.invoke(RepeatableContainers.standardRepeatables(), containerInstance);

    Arrays.stream(repeatableArr).forEach(System.out::println);
  }
}
