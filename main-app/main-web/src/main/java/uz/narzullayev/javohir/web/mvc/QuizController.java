package uz.narzullayev.javohir.web.mvc;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:50 AM*/

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.constant.QuizChoice;
import uz.narzullayev.javohir.domain.Quiz;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.dto.QuizDto;
import uz.narzullayev.javohir.service.QuizService;
import uz.narzullayev.javohir.service.ScienceService;
import uz.narzullayev.javohir.util.ToastNotificationUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final ScienceService scienceService;
    private final QuizService quizService;
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>quiz>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    @GetMapping(value = "/{id}")
    public String quiz(@PathVariable(value = "id") Long id,
                       RedirectAttributes redirectAttributes,
                       Model model
    ) {
        var science = scienceService.findById(id);
        if (science == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        Assert.notNull(science, "Science object is null");
        model.addAttribute("object", science);
        model.addAttribute("breadcrumb", getBreadcrumb("Tест", "/quiz/" + id));
        model.addAttribute("quizzes", quizService.list(science.getId()));
        model.addAttribute("back_action", "/science/list");
        return "quiz/list";
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>create>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    @GetMapping(value = "/create/{id}")
    public String create(@PathVariable(value = "id") Long id,
                         RedirectAttributes redirectAttributes,
                         Model model
    ) {
        var science = scienceService.findById(id);
        if (science == null) ToastNotificationUtils.addWarning(redirectAttributes, "Топилмади");
        Assert.notNull(science, "Science object is null");
        model.addAttribute("object", new QuizDto());
        model.addAttribute("breadcrumb", getBreadcrumb("Tест яратиш", "/quiz/" + id));
        model.addAttribute("quizzes", quizService.list(science.getId()));
        model.addAttribute("back_action", "/quiz/" + id);
        model.addAttribute("post_action", "/quiz/create/" + id);
        return "quiz/create";
    }

    @PostMapping(value = "/create/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Void> updateOrCreate(@PathVariable(value = "id") Long science_id,
                                                             @RequestParam MultiValueMap data

    ) {
        Map map = data.toSingleValueMap();
        String question = Optional.ofNullable(map.get("question"))
                .map(String.class::cast)
                .orElse("");
        Assert.notNull(map, "Result map is null");

        Quiz quiz = new Quiz();
        quiz.setScience(scienceService.findById(science_id));
        quiz.setQuestion(question);
        List<QuizChoice> quizChoices = new LinkedList<>();
        IntStream.rangeClosed(1, 4).forEach(value -> {
            String choice = Optional.ofNullable(map.get("choice_" + value + "[]"))
                    .map(String.class::cast)
                    .orElse("");
            Boolean answer = Optional.ofNullable(map.get("answers_" + value + "[]"))
                    .map(String.class::isInstance)
                    .orElse(false);
            if (!choice.isEmpty()) quizChoices.add(new QuizChoice(answer, choice));

        });
        quiz.setChoices(quizChoices);
        quizService.save(quiz);
        return ResponseEntity
                .noContent()
                .build();

    }
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>remove>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    @GetMapping(value = "/remove/{id}")
    public String remove(@PathVariable(value = "id") Long id,
                         RedirectAttributes redirectAttributes,
                         Model model
    ) {
        Optional<Quiz> quiz = quizService.getById(id);
        Assert.notNull(quiz.get(), "Quiz object is null");
        Long science_id = quiz.get().getScience().getId();
        quizService.remove(id);
        return "redirect:/quiz/" + science_id;
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Фанлар", "/science/list");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }


    @GetMapping(value = "/play/{id}")
    public String play(
            @PathVariable("id") Integer science_id) {
        return "quiz/play";
    }

}
