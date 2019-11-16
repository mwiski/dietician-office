package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.service.AnswerService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AnswerController.BASE_API)
public class AnswerController {

    static final String BASE_API = "v1/answers";

    @Autowired
    private AnswerService answerService;

    @GetMapping
    public List<AnswerDto> getAll() {
        return answerService.getAll();
    }

    @GetMapping("users/{userId}")
    public List<AnswerDto> getUserAnswers(@PathVariable final long userId) {
        return answerService.getUserAnswers(userId);
    }

    @GetMapping("dieticians/{dieticianId}")
    public List<AnswerDto> getDieticianAnswers(@PathVariable final long dieticianId) {
        return answerService.getDieticianAnswers(dieticianId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public AnswerDto add(@RequestBody final AnswerDto answerDto) {
        return answerService.add(answerDto);
    }

    @PutMapping("{answerId}")
    public AnswerDto edit(@PathVariable final long answerId, @RequestParam final String content) {
        return answerService.edit(answerId, content);
    }

    @DeleteMapping("{answerId}")
    public void delete(@PathVariable final long answerId) {
        answerService.delete(answerId);
    }
}
