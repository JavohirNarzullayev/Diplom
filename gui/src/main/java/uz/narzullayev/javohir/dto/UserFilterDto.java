package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/11/2022
  Time: 5:08 PM*/

import lombok.Data;
import uz.narzullayev.javohir.constant.UserType;

@Data
public class UserFilterDto {
    private String username;
    private UserType role;
    private String fio;
    private Boolean status;
}
