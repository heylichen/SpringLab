package language.annotation.annotatedelement;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public class AnnotatedElementDemo {
    public static void main(String[] args) {
//        annotationApi();
        testAnnotatedElement();
    }

    // 省略部分代码......
    public static void annotationApi() {
        // 获取组合注解
        RepeatableContainerAnno myRepeatableAnno = SubRepeatableAnnoClass.class.getAnnotation(RepeatableContainerAnno.class);
        assert myRepeatableAnno != null;
        System.out.println(myRepeatableAnno);
        // 不能获取可重复注解
        RepeatableAnno myAnno1 = RepeatableAnnoClass.class.getAnnotation(RepeatableAnno.class);
        Assert.isNull(myAnno1);

        // 获取存在的注解，只能获取间接存在的MyRepeatableAnno注解
        Annotation[] annotations = SubRepeatableAnnoClass.class.getAnnotations();
        Assert.isTrue(annotations.length == 1);

        // 可以获取关联的注解，MyRepeatableAnno注解是从父类继承的
        RepeatableContainerAnno[] annotationsByType = SubRepeatableAnnoClass.class.getAnnotationsByType(RepeatableContainerAnno.class);
        Assert.isTrue(annotationsByType.length == 1);
        RepeatableAnno[] annotationsByType1 = SubRepeatableAnnoClass.class.getAnnotationsByType(RepeatableAnno.class);
        Assert.isTrue(annotationsByType1.length == 2);

        // 获取直接注解
        RepeatableContainerAnno declaredAnnotation = SubRepeatableAnnoClass.class.getDeclaredAnnotation(RepeatableContainerAnno.class);
        Assert.isNull(declaredAnnotation);
        // 获取直接存在的注解，该类上没有标注任何注解
        Annotation[] declaredAnnotations = SubRepeatableAnnoClass.class.getDeclaredAnnotations();
        Assert.isTrue(declaredAnnotations.length == 0);
        // 获取间接存在的注解
        RepeatableContainerAnno declaredAnnotation2 = RepeatableAnnoClass.class.getDeclaredAnnotation(RepeatableContainerAnno.class);
        Assert.notNull(declaredAnnotation2);
        // 不能获取重复注解
        RepeatableAnno myAnno11 = RepeatableAnnoClass.class.getDeclaredAnnotation(RepeatableAnno.class);
        Assert.isNull(myAnno11);
        // 忽略继承
        RepeatableContainerAnno[] declaredAnnotationsByType = SubRepeatableAnnoClass.class.getDeclaredAnnotationsByType(RepeatableContainerAnno.class);
        Assert.isTrue(declaredAnnotationsByType.length == 0);
        // 获取直接存在的重复注解
        RepeatableAnno[] myAnno1s = AnnotatedClass3.class.getDeclaredAnnotationsByType(RepeatableAnno.class);
        Assert.isTrue(myAnno1s.length == 2);
        // 获取直接存在的非重复注解
        Anno[] myAnnos = AnnotatedClass3.class.getDeclaredAnnotationsByType(Anno.class);
        Assert.isTrue(myAnnos.length == 1);
        // 获取简介存在的注解
        RepeatableContainerAnno[] myRepeatableAnnos = AnnotatedClass3.class.getDeclaredAnnotationsByType(RepeatableContainerAnno.class);
        Assert.isTrue(myRepeatableAnnos.length == 1);

        // 检查是否存在指定注解
        boolean annotationPresent = SubRepeatableAnnoClass.class.isAnnotationPresent(RepeatableContainerAnno.class);
        Assert.isTrue(annotationPresent);
        boolean annotationPresent1 = SubRepeatableAnnoClass.class.isAnnotationPresent(RepeatableAnno.class);
        Assert.isTrue(!annotationPresent1);
    }

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
     *                          present           on   SubRepeatableAnnoClass
     * RepeatableAnno          indirectly present on   RepeatableAnnoClass
     *                         associated         on   SubRepeatableAnnoClass
     */
    public static void testAnnotatedElement() {
        // 获取组合注解
//        System.out.println(determinePresent(RepeatableContainerAnno.class, RepeatableAnnoClass.class));
//        System.out.println(determinePresent(RepeatableContainerAnno.class, SubRepeatableAnnoClass.class));
//
//        System.out.println(determinePresent(RepeatableAnno.class, RepeatableAnnoClass.class));
//        System.out.println(determinePresent(RepeatableAnno.class, SubRepeatableAnnoClass.class));

        System.out.println(determinePresent(RepeatableContainerAnno.class, RepeatableAnnoInterface.class));
        // null
        System.out.println(determinePresent(RepeatableContainerAnno.class, SubRepeatableAnnoInterface.class));
        System.out.println(determinePresent(RepeatableAnno.class, RepeatableAnnoInterface.class));
        // null
        System.out.println(determinePresent(RepeatableAnno.class, SubRepeatableAnnoInterface.class));
    }

    private static String determinePresent(Class<? extends Annotation> annoClass, Class<?> clazz) {
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