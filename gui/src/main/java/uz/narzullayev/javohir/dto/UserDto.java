package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:42 PM*/

import lombok.Data;
import uz.narzullayev.javohir.validation.FieldMatch;
import uz.narzullayev.javohir.validation.UserValidity;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",second = "confirmPassword",message = "Password and confirm password not match"
)
@Data
public class UserDto {
    @UserValidity
    @Size(min = 4,max = 12)
    private String username;

    @NotBlank(message = "Password is required!")
    private String password;

    @Size(min = 4,max = 12)
    private String fio;
    @NotNull
    @NotBlank
    private String phone;

    @NotBlank
    @Email
    @UserValidity(message = "This email already exist!")
    private String email;

    @Transient
    private String confirmPassword;
}

