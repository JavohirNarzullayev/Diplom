package uz.narzullayev.javohir.controller;/* 
 @author: Javohir
  Date: 1/11/2022
  Time: 3:33 PM*/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.narzullayev.javohir.dto.Breadcrumb;

@Controller
@RequestMapping("/dashboard")
public class CabinetController {

    @GetMapping
    public String list(Model model){
        model.addAttribute("breadcrumb", getBreadcrumb(""));
        return "dashboard";
    }

    private Breadcrumb getBreadcrumb(String name) {
        Breadcrumb breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Асосий ойна", "/dashboard");
        breadcrumb.addLink(name, "#");
        return breadcrumb;
    }

}
