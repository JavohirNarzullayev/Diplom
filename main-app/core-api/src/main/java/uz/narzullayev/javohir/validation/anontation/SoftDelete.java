package uz.narzullayev.javohir.validation.anontation;/* 
 @author: Javohir
  Date: 3/24/2022
  Time: 12:09 AM*/

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
public @interface SoftDelete {
    String value() default "";
}