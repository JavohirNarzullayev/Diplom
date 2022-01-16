package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:42 PM*/

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.validation.anontation.EmailValidity;
import uz.narzullayev.javohir.validation.anontation.FieldMatch;
import uz.narzullayev.javohir.validation.anontation.UserValidity;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",second = "confirmPassword",message = "Пароллар мос емас",
        groups = UserDto.Create.class
)
@EmailValidity(
        message = "Бу почта аллакочон мавжуд!",
        groups = {UserDto.Create.class, UserDto.Update.class})
@UserValidity(
        message = "Бу фойдаланувчи руйхатдан утган",
        groups = {UserDto.Create.class, UserDto.Update.class})

@Data
@NoArgsConstructor
public class UserDto {
    public interface Create{};
    public interface Update{};

    private Long id;
    @Size(min = 4,max = 12)
    private String username;

    @NotBlank(message = "Парол ёзиш талаб етилади!",groups = {Create.class})
    private String password;

    @Size(min = 4,max = 12,groups = {Create.class,Update.class})
    private String fio;

    @NotNull(groups = Create.class)
    @NotBlank(groups = Create.class)
    private String phone;

    @NotNull(groups = {Create.class,Update.class})
    private UserType userType;

    @NotBlank(groups = Create.class)
    @Email(message = "Илтимо почтани тугри киритинг",groups = {Create.class,Update.class})
    private String email;

    @Transient
    private String confirmPassword;

    private Boolean enabled;



    public UserEntity merge(UserEntity user){
        user.setId(this.id);
        user.setUsername(this.username);
        if (StringUtils.hasText(this.password)) {
            user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        }

        if (this.enabled == null) {
            user.setEnabled(Boolean.TRUE);
        } else {
            user.setEnabled(this.enabled);
        }
        user.setRole(this.userType);
        user.setFio(this.fio);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        return user;
    }
    public UserDto(UserEntity user){
        this.email=user.getEmail();
        this.fio=user.getFio();
        this.userType=user.getRole();
        this.username=user.getUsername();
        this.phone=user.getPhone();
        this.id=user.getId();
        this.enabled=user.getEnabled();
    }

    public Boolean emptyId(){
        return this.getId()==null;
    }
}

