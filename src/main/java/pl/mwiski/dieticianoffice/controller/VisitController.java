package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.service.VisitService;

import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(VisitController.BASE_API)
public class VisitController {

    static final String BASE_API = "v1/visits";

    @Autowired
    private VisitService visitService;

    @GetMapping("${api.key}")
    public List<VisitDto> getAll() {
        return visitService.getAll();
    }

    @GetMapping("date/${api.key}")
    public List<VisitDto> getAvailableVisits(@RequestParam final String date) {
        return visitService.getAvailableVisits(date);
    }

    @GetMapping("users/{userId}/${api.key}")
    public List<VisitDto> getUserVisits(@PathVariable final long userId) {
        return visitService.getUserVisits(userId);
    }

    @GetMapping("dieticians/{dieticianId}/${api.key}")
    public List<VisitDto> getDieticianVisits(@PathVariable final long dieticianId) {
        return visitService.getDieticianVisits(dieticianId);
    }

    @GetMapping("{visitId}/${api.key}")
    public VisitDto get(@PathVariable final long visitId) {
        return visitService.get(visitId);
    }

    @PostMapping(value = "${api.key}", consumes = APPLICATION_JSON_VALUE)
    public VisitDto add(@RequestBody @Valid final VisitDto visitDto) {
        return visitService.add(visitDto);
    }

    @PutMapping("{visitId}/users/{userId}/${api.key}")
    public VisitDto schedule(@PathVariable final long visitId, @PathVariable long userId) {
        return visitService.schedule(visitId, userId);
    }

    @DeleteMapping("{visitId}/${api.key}")
    public void cancel(@PathVariable final long visitId) {
        visitService.cancel(visitId);
    }
}
