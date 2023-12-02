package language.annotation.annotatedelement;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Repeatable(RepeatableContainerAnno.class)
@interface RepeatableAnno {
    String value() default "";
}