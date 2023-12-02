package language.annotation.annotatedelement;

import org.junit.Test;

import java.lang.annotation.Annotation;

public class AnnotatedElementTest {

  /**
   * directly present  indirectly present    present    associated
   * T getAnnotation(Class<T>)                                                              X
   * Annotation[] getAnnotations()                                                          X
   * T[] getAnnotationsByType(Class<T>)                                                                   X
   * T getDeclaredAnnotation(Class<T>)               X
   * Annotation[] getDeclaredAnnotations()           X
   * T[] getDeclaredAnnotationsByType(Class<T>)                       X
   * <p>
   * <p>
   * RepeatableContainerAnno directly present   on   RepeatableAnnoClass
   * present           on   SubRepeatableAnnoClass
   * RepeatableAnno          indirectly present on   RepeatableAnnoClass
   * associated         on   SubRepeatableAnnoClass
   */
  @Test
  public void testPresent() {
    // 获取组合注解
    System.out.println(determinePresent(RepeatableContainerAnno.class, RepeatableAnnoClass.class));
    System.out.println(determinePresent(RepeatableContainerAnno.class, SubRepeatableAnnoClass.class));

    System.out.println(determinePresent(RepeatableAnno.class, RepeatableAnnoClass.class));
    System.out.println(determinePresent(RepeatableAnno.class, SubRepeatableAnnoClass.class));
  }

  @Test
  public void testSubInterface() {
    System.out.println(determinePresent(RepeatableContainerAnno.class, RepeatableAnnoInterface.class));
    // null
    System.out.println(determinePresent(RepeatableContainerAnno.class, SubRepeatableAnnoInterface.class));
    System.out.println(determinePresent(RepeatableAnno.class, RepeatableAnnoInterface.class));
    // null
    System.out.println(determinePresent(RepeatableAnno.class, SubRepeatableAnnoInterface.class));
  }

  public static String determinePresent(Class<? extends Annotation> annoClass, Class<?> clazz) {
    boolean hasDeclared = clazz.getDeclaredAnnotation(annoClass) != null;
    boolean hasDeclaredArr = clazz.getDeclaredAnnotations().length > 0;
    boolean hasDeclaredByTypeArr = clazz.getDeclaredAnnotationsByType(annoClass).length > 0;

    boolean hasAnno = clazz.getAnnotation(annoClass) != null;
    boolean hasAnnoArr = clazz.getAnnotations().length > 0;
    boolean hasAnnoByTypeArr = clazz.getAnnotationsByType(annoClass).length > 0;

    if (hasDeclared && hasDeclaredArr) {
      return "Directly Present";
    } else if (hasDeclaredByTypeArr) {
      return "Indirectly Present";
    } else if (hasAnno && hasAnnoArr) {
      return "Present";
    } else if (hasAnnoByTypeArr) {
      return "Associated";
    }
    return null;
  }
}