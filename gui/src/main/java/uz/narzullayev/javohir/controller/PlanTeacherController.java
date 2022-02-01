package uz.narzullayev.javohir.controller;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 3:35 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.entity.PlanTeacher;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher_plan")
@RequiredArgsConstructor
public class PlanTeacherController {
    private final PlanTeacherService planTeacherService;


    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("filter", new PlanTeacherDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/user/list"));
        return "teacher_plan/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<PlanTeacher> listAjax(@Valid DataTablesInput input, PlanTeacherDto filterDto) {
        return planTeacherService.findAll(input, filterDto);
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UPDATE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @GetMapping(value = "/edit")
    public String edit(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "id", required = false) Long id
    ) {
        var planTeacher = planTeacherService.findById(id);
        if (planTeacher == null) {
            ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
            return "redirect:/teacher_plan/list";
        }
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/user/edit?id=" + id));
        model.addAttribute("object", new PlanTeacherDto(planTeacher));
        model.addAttribute("back_action", "/teacher_plan/list");
        model.addAttribute("post_action", "/teacher_plan/update");
        return "teacher_plan/edit";
    }

    @PostMapping(value = "/update")
    public String update(
            @Validated(value = PlanTeacherDto.OnUpdate.class) @ModelAttribute("object") PlanTeacherDto planTeacherDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "teacher_plan/edit";
        if (planTeacherDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else planTeacherService.update(planTeacherDto);
        return "redirect:/teacher_plan/list";
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Create>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Яратиш", "/teacher_plan/create"));
        model.addAttribute("object", new PlanTeacherDto());
        model.addAttribute("back_action", "/teacher_plan/list");
        model.addAttribute("post_action", "/teacher_plan/create");
        return "teacher_plan/edit";
    }

    @PostMapping(value = "/create")
    public String create(
            @Validated(value = PlanTeacherDto.OnCreate.class) @ModelAttribute("object") PlanTeacherDto planTeacherDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "teacher_plan/edit";
        if (planTeacherDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else planTeacherService.save(planTeacherDto);
        return "redirect:/teacher_plan/list";
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>delete>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes redirectAttributes) {
        var planTeacher = planTeacherService.findById(id);
        if (planTeacher == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else planTeacherService.remove(id);
        return "redirect:/teacher_plan/list";
    }


    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Укув режа", "/teacher/list");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }

}
