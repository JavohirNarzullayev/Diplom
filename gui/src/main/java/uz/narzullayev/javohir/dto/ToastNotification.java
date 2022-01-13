package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/12/2022
  Time: 4:16 PM*/

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.narzullayev.javohir.constant.ToastNotificationType;

@Data
@AllArgsConstructor
public class ToastNotification {
    private ToastNotificationType type;
    private String message;



}
