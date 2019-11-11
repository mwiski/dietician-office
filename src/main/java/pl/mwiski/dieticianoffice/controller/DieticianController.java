package pl.mwiski.dieticianoffice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.service.DieticianService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(DieticianController.BASE_API)
@Slf4j
public class DieticianController {

    static final String BASE_API = "v1/dieticians";

    @Autowired
    private DieticianService dieticianService;

    @GetMapping
    public List<DieticianDto> getDieticians() {
        log.info("Getting list of all dieticians");
        return dieticianService.getDieticians();
    }

    @GetMapping("{dieticianId}")
    public DieticianDto getDietician(@PathVariable Long dieticianId) {
        log.info("Getting dietician by ID = {}", dieticianId);
        return dieticianService.getDietician(dieticianId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public DieticianDto addDietician(@RequestBody DieticianDto dieticianDto) {
        log.info("Creating new dietician {}", dieticianDto.getLogin());
        return dieticianService.addDietician(dieticianDto);
    }

    @PutMapping
    public DieticianDto updateDietician(@RequestBody DieticianDto dieticianDto) {
        log.info("Updating dietician information {}", dieticianDto.getLogin());
        return dieticianService.updateDietician(dieticianDto);
    }

    @DeleteMapping("{dieticianId}")
    public void deleteDietician(@PathVariable Long dieticianId) {
        log.info("Deleting dietician with id {}", dieticianId);
        dieticianService.deleteDietician(dieticianId);
    }
}
