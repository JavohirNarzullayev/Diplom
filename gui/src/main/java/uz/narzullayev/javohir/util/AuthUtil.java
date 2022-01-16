package uz.narzullayev.javohir.util;/* 
 @author: Javohir
  Date: 1/10/2022
  Time: 1:52 PM*/

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.narzullayev.javohir.dto.ProjectUserDetails;
import uz.narzullayev.javohir.entity.UserEntity;

import java.util.Optional;

@UtilityClass
public class AuthUtil {

    public static Optional<Long> getUserId(){
        ProjectUserDetails projectUserDetails = getUserDetails();
        if (projectUserDetails!=null){
            return Optional.ofNullable(projectUserDetails.getUserId());
        }
        return Optional.empty();
    }
    public static Optional<UserEntity> getUserEntity(){
        ProjectUserDetails projectUserDetails = getUserDetails();
        if (projectUserDetails!=null){
            return Optional.ofNullable(projectUserDetails.getUserEntity());
        }
        return Optional.empty();
    }


    private static ProjectUserDetails getUserDetails(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof ProjectUserDetails){
                return (ProjectUserDetails) authentication.getPrincipal();
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
