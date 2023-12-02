package language.annotation.annotatedelement;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface RepeatableContainerAnno {
    RepeatableAnno[] value() default {};
}