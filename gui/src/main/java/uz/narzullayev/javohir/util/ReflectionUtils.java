package uz.narzullayev.javohir.util;/* 
 @author: Javohir
  Date: 1/17/2022
  Time: 12:32 AM*/

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectionUtils {

    public static <T> T extractFieldValue(Object classObj, String fieldName, Class<T> castObject) throws NoSuchFieldException, IllegalAccessException {
        Field field = classObj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return castObject.cast(field.get(classObj));
    }

}
