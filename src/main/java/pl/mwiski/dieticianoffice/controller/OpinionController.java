package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.service.OpinionService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(OpinionController.BASE_API)
public class OpinionController {

    static final String BASE_API = "v1/opinions";

    @Autowired
    private OpinionService opinionService;

    @GetMapping("${api.key}")
    public List<OpinionDto> getAll() {
        return opinionService.getAll();
    }

    @GetMapping("users/{userId}/${api.key}")
    public List<OpinionDto> getUserOpinions(@PathVariable final long userId) {
        return opinionService.getUserOpinions(userId);
    }

    @PostMapping(value = "${api.key}", consumes = APPLICATION_JSON_VALUE)
    public OpinionDto add(@RequestBody final OpinionDto opinionDto) {
        return opinionService.add(opinionDto);
    }

    @PutMapping("{opinionId}/${api.key}")
    public OpinionDto edit(@PathVariable final long opinionId, @RequestParam final String content) {
        return opinionService.edit(opinionId, content);
    }

    @DeleteMapping("{opinionId}/${api.key}")
    public void delete(@PathVariable final long opinionId) {
        opinionService.delete(opinionId);
    }
}
