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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.narzullayev.javohir.domain.Quiz;
import uz.narzullayev.javohir.dto.Breadcrumb;
import uz.narzullayev.javohir.service.QuizService;

import javax.validation.Valid;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final QuizService studentService;

    //get subject for quiz list
    @GetMapping(value = "/subject/quiz")
    public String list(Model model) {
        model.addAttribute("breadcrumb", getBreadcrumb("Руйхат", "/user/list"));
        return "student/subjects_quiz";
    }

    @GetMapping(value = "/quiz_list/{subject_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataTablesOutput<Quiz> listAjax(@Valid DataTablesInput input,
                                           @RequestParam("subject_id") Long subject_id) {
        return studentService.list(input, subject_id);
    }

    private Breadcrumb getBreadcrumb(String name, String url) {
        var breadcrumb = new Breadcrumb();
        breadcrumb.addLink("Фан олимпиада", "/student/quiz");
        breadcrumb.addLink(name, url);
        return breadcrumb;
    }
}
