package uz.narzullayev.javohir.web.mvc;/* 
 @author: Javohir
  Date: 6/6/2022
  Time: 3:50 PM*/


import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.service.LiteratureService;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.service.ScienceService;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final ScienceService scienceService;
    private final PlanTeacherService planTeacherService;
    private final LiteratureService literatureService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("sciences", scienceService.findAll());
        model.addAttribute("literatures", literatureService.findAll());
        return "index";
    }

    @GetMapping("/index/science/{id}")
    public String science(@PathVariable("id") Long science_id, Model model) {
        Science science = scienceService.findById(science_id);
        Set<UserEntity> teachers = science.getUserEntities();
        model.addAttribute("teachers", Pair.of(teachers, science));
        return "index";
    }

    @GetMapping("/index/teacher/{id}")
    public String planTeacher(@PathVariable("id") Long teacher_id, Model model) {
        List<PlanTeacher> plansTeacher = planTeacherService.findByCreatedId(teacher_id);
        model.addAttribute("plans_teacher", plansTeacher);
        return "index";
    }
}
