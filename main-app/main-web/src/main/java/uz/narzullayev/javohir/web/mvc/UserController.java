package uz.narzullayev.javohir.web.mvc;/*
 @author: Javohir
  Date: 1/7/2022
  Time: 9:40 PM*/

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.dto.UserFilterDto;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController  {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("filter",new UserFilterDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат","/user/list"));
        return "user/list";
    }

    @GetMapping(path = "/registration")
    public String registration(Model model){
        model.addAttribute("user",new UserDto());
        return "user/registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@Validated(UserDto.Create.class) @ModelAttribute("user") UserDto user,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user/registration";
        userService.save(user);
        return "redirect:/login";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<UserEntity> listAjax(@Valid DataTablesInput input, UserFilterDto filterDto){
        return userService.findAllBySpecific(input, filterDto);
    }

    @GetMapping(value = "/action/{id}")
    public String action(@PathVariable("id")Long id){
        userService.userBlockOrUnblockById(id);
        return "redirect:/user/list";
    }

    @SneakyThrows
    @GetMapping(value = "/view")
    public String view(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "id", required = false) Long id
    ){

        var userEntity = userService.findById(id);
        if (userEntity == null){
            ToastNotificationUtils.addWarning(redirectAttributes,"Топилмади");
            return "redirect:/user/list";
        }
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни куриш","/user/view?id="+id));
        model.addAttribute("object", userEntity);
        return "user/view";
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/edit")
    public String edit(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "id", required = false) Long id

    ) {
        var userEntity = userService.findById(id);
        if (userEntity == null) {
            ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
            return "redirect:/user/list";
        }

        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/user/edit?id=" + id));
        model.addAttribute("object", new UserDto(userEntity));
        model.addAttribute("roles", UserType.getUserTypes());
        model.addAttribute("back_action", "/user/list");
        return "user/edit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/edit")
    public String edit(
            @Validated(value = UserDto.Update.class)  @ModelAttribute("object") UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes

    ){
        if (bindingResult.hasErrors()) return "user/edit";
        if (userDto == null){
            ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
            return "redirect:/user/list";
        }
        userService.update(userDto);
        return "redirect:/user/list";
    }


    private Breadcrumb getBreadcrumb(String name,String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink( "Фойдаланучилар","/user/list");
        breadcrumb.addLink(name,url);
        return breadcrumb;
    }


}