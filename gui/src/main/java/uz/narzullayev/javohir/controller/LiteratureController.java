package uz.narzullayev.javohir.controller;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:49 AM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.entity.Literature;
import uz.narzullayev.javohir.service.LiteratureService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/literature")
public class LiteratureController {

    private final LiteratureService literatureService;

    @GetMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("filter",new PlanTeacherDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат","/user/list"));
        return "teacher_plan/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<Literature> listAjax(@Valid DataTablesInput input, PlanTeacherDto filterDto){
        return literatureService.findAll(input, filterDto);
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink( "Укув адабиётлар","/literature/list");
        breadcrumb.addLink(name,url);
        return breadcrumb;
    }
}
