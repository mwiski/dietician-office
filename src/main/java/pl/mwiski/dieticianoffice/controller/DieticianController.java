package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.service.DieticianService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(DieticianController.BASE_API)
public class DieticianController {

    static final String BASE_API = "v1/dieticians";

    @Autowired
    private DieticianService dieticianService;

    @GetMapping("${api.key}")
    public List<DieticianDto> getAll() {
        return dieticianService.getAll();
    }

    @GetMapping("{dieticianId}/${api.key}")
    public DieticianDto get(@PathVariable final long dieticianId) {
        return dieticianService.get(dieticianId);
    }

    @GetMapping("name/{username}/${api.key}")
    public Optional<DieticianDto> getDieticianByLogin(@PathVariable final String username) {
        return dieticianService.getDieticianByLogin(username);
    }

    @PostMapping(value = "${api.key}", consumes = APPLICATION_JSON_VALUE)
    public DieticianDto add(@RequestBody @Valid final DieticianDto dieticianDto) {
        return dieticianService.add(dieticianDto);
    }

    @PutMapping("${api.key}")
    public DieticianDto update(@RequestBody @Valid final DieticianDto dieticianDto) {
        return dieticianService.update(dieticianDto);
    }

    @DeleteMapping("{dieticianId}/${api.key}")
    public void delete(@PathVariable final long dieticianId) {
        dieticianService.delete(dieticianId);
    }
}
