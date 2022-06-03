package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 1:45 AM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.narzullayev.javohir.constant.QuizChoice;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuizDto {
    public interface OnCreate {
    }

    public interface OnUpdate {
    }

    private Long id;
    private String question;
    private List<QuizChoice> choices;
}
