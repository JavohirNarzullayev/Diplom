package uz.narzullayev.javohir.constant;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:45 AM*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizChoice {
    private boolean correct_answer;//if quiz choice is right this is assign true
    private String choice;//question choice

}
