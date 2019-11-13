package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.service.VisitService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(VisitController.BASE_API)
public class VisitController {

    static final String BASE_API = "v1/visits";

    @Autowired
    private VisitService visitService;

    @GetMapping
    public List<VisitDto> getAll() {
        return visitService.getAll();
    }

    @GetMapping("users/{userId}")
    public List<VisitDto> getUserVisits(@PathVariable long userId) {
        return visitService.getUserVisits(userId);
    }

    @GetMapping("dieticians/{dieticianId}")
    public List<VisitDto> getDieticianVisits(@PathVariable long dieticianId) {
        return visitService.getDieticianVisits(dieticianId);
    }

    @GetMapping("{visitId}")
    public VisitDto get(@PathVariable long visitId) {
        return visitService.get(visitId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public VisitDto add(@RequestBody VisitDto visitDto) {
        return visitService.add(visitDto);
    }

    @PutMapping("{visitId}/users/{userId}")
    public VisitDto schedule(@PathVariable long visitId, @PathVariable long userId) {
        return visitService.schedule(visitId, userId);
    }

    @DeleteMapping("{visitId}")
    public void cancel(@PathVariable long visitId) {
        visitService.cancel(visitId);
    }
}
