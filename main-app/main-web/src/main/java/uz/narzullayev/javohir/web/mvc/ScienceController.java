package uz.narzullayev.javohir.web.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.dto.ScienceDto;
import uz.narzullayev.javohir.service.ScienceService;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/science")
@RequiredArgsConstructor
public class ScienceController {
    private final ScienceService scienceService;
    private final UserService userService;

    @GetMapping("/list")
    public String science(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/user/list"));
        return "science/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<ScienceDto> listAjax(@Valid DataTablesInput input, String scienceName) {
        return scienceService.findAll(input, scienceName);
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UPDATE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @GetMapping(value = "/edit")
    public String edit(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "id", required = false) Long id
    ) {
        var science = scienceService.findById(id);
        if (science == null) {
            ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
            return "redirect:/science/list";
        }
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/science/edit?id=" + id));
        model.addAttribute("object", ScienceDto.builder()
                .science(science)
                .build());
        model.addAttribute("teachers", userService.findAllTeachers());
        model.addAttribute("back_action", "/science/list");
        model.addAttribute("post_action", "/science/update");
        return "science/edit";
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CREATE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @PostMapping(value = "/update")
    public String update(
            @Validated(value = PlanTeacherDto.OnUpdate.class) @ModelAttribute("object") ScienceDto scienceDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) return "science/edit";
        if (scienceDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else {
            Science science = scienceService.findById(scienceDto.getId());
            science.setName(new NameEntity(scienceDto.getNameUz(), scienceDto.getNameOz()));
            science.setDescription(new NameEntity(scienceDto.getDescriptionUz(), scienceDto.getDescriptionOz()));
            if (!CollectionUtils.isEmpty(scienceDto.getTeachers())) {
                science.setUserEntities(userService.findByIdIn(scienceDto.getTeachers()));
            }
            scienceService.crudScience(science);
        }
        return "redirect:/science/list";
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/science/edit"));
        model.addAttribute("object", new ScienceDto());
        model.addAttribute("back_action", "/science/list");
        model.addAttribute("post_action", "/science/create");
        model.addAttribute("teachers", userService.findAllTeachers());
        return "science/edit";
    }

    @PostMapping("/create")
    public String create(
            @Validated(value = PlanTeacherDto.OnUpdate.class) @ModelAttribute("object") ScienceDto scienceDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "science/edit";
        if (scienceDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else {
            Science science = new Science();
            science.setName(new NameEntity(scienceDto.getNameUz(), scienceDto.getNameOz()));
            science.setDescription(new NameEntity(scienceDto.getDescriptionUz(), scienceDto.getDescriptionOz()));
            science.setUserEntities(userService.findByIdIn(scienceDto.getTeachers()));
            scienceService.crudScience(science);
        }
        return "redirect:/science/list";
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>delete>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes redirectAttributes) {
        var science = scienceService.findById(id);
        if (science == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else scienceService.remove(id);
        return "redirect:/science/list";
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Фанлар", "/science/list");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }
}

