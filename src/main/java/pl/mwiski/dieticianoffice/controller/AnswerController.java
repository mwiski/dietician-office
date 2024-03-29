package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.service.AnswerService;

import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AnswerController.BASE_API)
public class AnswerController {

    static final String BASE_API = "v1/answers";

    @Autowired
    private AnswerService answerService;

    @GetMapping("${api.key}")
    public List<AnswerDto> getAll() {
        return answerService.getAll();
    }

    @GetMapping("dieticians/{dieticianId}/${api.key}")
    public List<AnswerDto> getDieticianAnswers(@PathVariable final long dieticianId) {
        return answerService.getDieticianAnswers(dieticianId);
    }

    @PostMapping(value = "${api.key}", consumes = APPLICATION_JSON_VALUE)
    public AnswerDto add(@RequestBody @Valid final AnswerDto answerDto) {
        return answerService.add(answerDto);
    }

    @PutMapping("{answerId}/${api.key}")
    public AnswerDto edit(@PathVariable final long answerId, @RequestParam final String content) {
        return answerService.edit(answerId, content);
    }

    @DeleteMapping("{answerId}/${api.key}")
    public void delete(@PathVariable final long answerId) {
        answerService.delete(answerId);
    }
}
