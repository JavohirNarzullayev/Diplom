package uz.narzullayev.javohir.web.mvc;
/*
 @author: Javohir
  Date: 6/6/2022
  Time: 3:50 PM
  */


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        plansTeacher.sort((o1, o2) -> {
            Integer index1 = stripNonDigits(o1.getTheme());
            Integer index2 = stripNonDigits(o2.getTheme());
            return index1.compareTo(index2);
        });
        model.addAttribute("plans_teacher", plansTeacher);
        return "index";
    }

    public static Integer stripNonDigits(CharSequence input) {
        Pattern isInt = Pattern.compile("\\d+");

        Matcher intMatcher = isInt.matcher(input);
        if (intMatcher.find()) {
            return Integer.parseInt(intMatcher.group());
        }
        return 0;
    }
}
