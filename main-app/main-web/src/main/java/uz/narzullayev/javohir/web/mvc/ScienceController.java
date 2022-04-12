package uz.narzullayev.javohir.web.mvc;/* 
 @author: Javohir
  Date: 4/12/2022
  Time: 4:08 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.service.ScienceService;
import uz.narzullayev.javohir.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/science")
@RequiredArgsConstructor
public class ScienceController {
    private final ScienceService scienceService;
    private final UserService userService;

    @GetMapping("/list")
    public String science(Model model) {
        var name = new NameEntity();
        name.setOz("JavaOz");
        name.setUz("JavaUz");
        var science = new Science(name, name, Set.of(userService.findById(1L)));
        scienceService.crudScience(science);
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/user/list"));
        return "science/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<Science> listAjax(@Valid DataTablesInput input, String scienceName) {
        return scienceService.findAll(input, scienceName);
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/science/edit"));
        model.addAttribute("object", new Science());//TODO
        return "science/edit";
    }

    @PostMapping("/create")
    public String create(Science science) {
        scienceService.crudScience(science);
        return "redirect:science/list";
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Фанлар", "/science/list");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }
}

