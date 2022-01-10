package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:42 PM*/

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.validation.FieldMatch;
import uz.narzullayev.javohir.validation.UserValidity;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@FieldMatch(
        first = "password",second = "confirmPassword",message = "Password and confirm password not match"
)
@Data
public class UserDto {
    @Size(min = 4,max = 12)
    @UserValidity(message = "Бу фойдаланувчи руйхатдан утган")
    private String username;

    @NotBlank(message = "Парол ёзиш талаб етилади!")
    private String password;

    @Size(min = 4,max = 12)
    private String fio;
    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    private UserType userType;

    @NotBlank
    @Email
    @UserValidity(message = "Бу почта аллакочон мавжуд!")
    private String email;

    @Transient
    private String confirmPassword;



    public UserEntity merge(){
        UserEntity user=new UserEntity();
        user.setUsername(this.username);
        user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        user.setEnabled(Boolean.TRUE);
        user.setRole(List.of(this.userType));
        user.setFio(this.fio);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        return user;
    }
}

