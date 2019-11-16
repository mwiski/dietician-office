package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.QuestionDto;
import pl.mwiski.dieticianoffice.service.QuestionService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(QuestionController.BASE_API)
public class QuestionController {

    static final String BASE_API = "v1/questions";

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionDto> getAll() {
        return questionService.getAll();
    }

    @GetMapping("users/{userId}")
    public List<QuestionDto> getUserQuestions(@PathVariable final long userId) {
        return questionService.getUserQuestions(userId);
    }

    @GetMapping("dieticians/{dieticianId}")
    public List<QuestionDto> getDieticianQuestions(@PathVariable final long dieticianId) {
        return questionService.getDieticianQuestions(dieticianId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public QuestionDto add(@RequestBody final QuestionDto questionDto) {
        return questionService.add(questionDto);
    }

    @PutMapping("{questionId}")
    public QuestionDto edit(@PathVariable final long questionId, @RequestParam final String content) {
        return questionService.edit(questionId, content);
    }

    @DeleteMapping("{questionId}")
    public void delete(@PathVariable final long questionId) {
        questionService.delete(questionId);
    }
}
