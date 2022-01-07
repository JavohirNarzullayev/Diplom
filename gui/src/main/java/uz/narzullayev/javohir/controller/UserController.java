package uz.narzullayev.javohir.controller;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:40 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("user",new UserDto());
        return "user/registration";
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user/registration";
        userService.save(user);
        return "redirect:/login";
    }}