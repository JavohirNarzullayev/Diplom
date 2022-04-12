package uz.narzullayev.javohir.web.mvc;

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
import uz.narzullayev.javohir.config.CurrentUser;
import uz.narzullayev.javohir.config.auth.ProjectUserDetails;
import uz.narzullayev.javohir.config.security.SecurityUtils;
import uz.narzullayev.javohir.domain.Literature;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.LiteratureDto;
import uz.narzullayev.javohir.service.LiteratureService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/literature")
public class LiteratureController {

    private final LiteratureService literatureService;

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("filter", new LiteratureDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/user/list"));
        return "literature/list";
    }

    @GetMapping(value = "/list_ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<Literature> listAjax(@Valid DataTablesInput input, LiteratureDto filterDto) {
        return literatureService.findAll(input, filterDto, SecurityUtils.getCurrentUserId());
    }

    @GetMapping(value = "/edit")
    public String edit(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "id", required = false) Long id
    ) {
        var literature = literatureService.findById(id);
        if (literature == null) {
            ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
            return "redirect:/literature/list";
        }
        model.addAttribute("breadcrumb", getBreadcrumb("Маьлумотни узгартириш", "/literature/edit?id=" + id));
        model.addAttribute("object", new LiteratureDto(literature));
        model.addAttribute("back_action", "/literature/list");
        model.addAttribute("post_action", "/literature/update");
        return "literature/edit";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Яратиш", "/literature/create"));
        model.addAttribute("object", new LiteratureDto());
        model.addAttribute("back_action", "/literature/list");
        model.addAttribute("post_action", "/literature/save");
        return "literature/edit";
    }

    @PostMapping(value = "/update")
    public String create(
            @Validated(value = LiteratureDto.OnUpdate.class) @ModelAttribute("object") LiteratureDto literatureDto,
            BindingResult bindingResult,
            @CurrentUser ProjectUserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "literature/edit";
        if (literatureDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else literatureService.update(literatureDto, userDetails.getUserId());
        return "redirect:/literature/list";
    }

    @PostMapping(value = "/save")
    public String save(
            @Validated(value = LiteratureDto.OnCreate.class) @ModelAttribute("object") LiteratureDto literatureDto,
            BindingResult bindingResult,
            @CurrentUser ProjectUserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "literature/edit";
        if (literatureDto == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else literatureService.save(literatureDto, userDetails.getUserId());
        return "redirect:/literature/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes redirectAttributes) {
        var literature = literatureService.findById(id);
        if (literature == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        else literatureService.remove(id);
        return "redirect:/literature/list";
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Укув адабиётлар", "/literature/list");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }
}
