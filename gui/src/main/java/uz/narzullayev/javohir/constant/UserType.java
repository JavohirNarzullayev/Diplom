package uz.narzullayev.javohir.constant;/* 
 @author: Javohir
  Date: 1/9/2022
  Time: 8:30 PM*/

import lombok.Getter;

import java.util.List;

@Getter
public enum UserType {
    ADMIN,
    STUDENT,
    TEACHER;



    public static List<UserType> getUserTypes(){
        return List.of(STUDENT,TEACHER);
    }
}
