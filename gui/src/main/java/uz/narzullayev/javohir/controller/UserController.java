package uz.narzullayev.javohir.controller;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:40 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.dto.UserFilterDto;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements MessageSourceAware {
    private MessageSource messageSource;

    private final UserService userService;

    @GetMapping(path = "/registration")
    public String registration(Model model){
        model.addAttribute("user",new UserDto());
        return "user/registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user/registration";
        userService.save(user);
        return "redirect:/login";
    };
    
    @RequestMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("filter",new UserFilterDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхати"));
        return "user/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<UserEntity> listAjax(@Valid DataTablesInput input, UserFilterDto filterDto){
        return userService.findAllBySpecific(input, filterDto);
    }

    @GetMapping(value = "/users/action/{id}")
    public String action(@PathVariable("id")Long id){
        return "redirect: /user/list";
    }

    @RequestMapping(value = "/view/{id}")
    public String view(
            Model model,
            RedirectAttributes redirectAttributes,
            @PathVariable(name = "id", required = false) Long id
    ){

        UserEntity userEntity = userService.findById(id);
        if (userEntity == null){
            ToastNotificationUtils.addWarning(redirectAttributes, messageSource.getMessage("Топилмади", new UserEntity[]{userEntity}, LocaleContextHolder.getLocale()));
            return "redirect:/user/list";
        }
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумот"));
        model.addAttribute("object", userEntity);
        return "user/view";
    }

    private Breadcrumb getBreadcrumb(String name) {
        Breadcrumb breadcrumb = new Breadcrumb();
        breadcrumb.addLink( "Фойдаланучилар","#");
        breadcrumb.addLink(name,"/user/list");
        return breadcrumb;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}