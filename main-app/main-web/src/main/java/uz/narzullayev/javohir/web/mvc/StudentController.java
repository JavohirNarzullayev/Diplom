package uz.narzullayev.javohir.web.mvc;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:17 AM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.narzullayev.javohir.constant.QuizChoice;
import uz.narzullayev.javohir.domain.Quiz;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.service.QuizService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final QuizService quizService;

    //get subject for quiz list
    @GetMapping(value = "/subject/quiz")
    public String list(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/student/subject/quiz"));
        return "student/subjects_quiz";
    }

    @GetMapping(value = "/quiz_list/{subject_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<Quiz> listAjax(@Valid DataTablesInput input,
                                           @RequestParam("subject_id") Long subject_id) {
        return quizService.list(input, subject_id);
    }

    @GetMapping(value = "/play/{id}")
    public String play(@PathVariable("id") Long science_id, Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Тест", "/student/play/" + science_id));
        List<Quiz> list = quizService.list(science_id);
        list.forEach(quiz -> Collections.shuffle(quiz.getChoices()));
        model.addAttribute("quizzes", list);
        model.addAttribute("post_url", "/student/play/" + science_id);
        return "student/play";
    }

    @PostMapping(value = "/play/{id}")
    public String playPost(@PathVariable("id") Long science_id,
                           @RequestParam MultiValueMap<String, String> data,
                           Model model) {
        Long count = quizService.count(science_id);
        if (count == 0) return "student/play";
        AtomicLong correctAnswer = new AtomicLong(0);
        Map<String, String> formData = data.toSingleValueMap();
        formData.forEach((key, value) -> {
            if (key.startsWith("inputcheck_") && value != null) {
                String input_check = key.replace("inputcheck_", "");
                Assert.notNull(input_check, "input check is null cannot replace");
                Long quiz_id = Long.valueOf(input_check.trim());
                quizService.getById(quiz_id).ifPresent(quiz -> {
                    boolean isCorrect = quiz.getChoices().stream()
                            .filter(Objects::nonNull)
                            .filter(quizChoice -> quizChoice.getChoice().equals(value))
                            .anyMatch(QuizChoice::isCorrect_answer);
                    if (isCorrect) correctAnswer.incrementAndGet();
                });
            }
        });

        model.addAttribute("back_url", "/student/subject/quiz");
        long attributeValue = correctAnswer.get() * 100 / count;
        model.addAttribute("percent", attributeValue);
        return "student/play";
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Фан олимпиада", "/student/subject/quiz");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }
}
