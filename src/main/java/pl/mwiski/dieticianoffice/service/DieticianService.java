package pl.mwiski.dieticianoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DieticianService {

    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private DieticianRepository dieticianRepository;

    public List<DieticianDto> getDieticians() {
        return dieticianMapper.toDieticianDtoList(dieticianRepository.findAll());
    }

    public DieticianDto getDietician(final Long dieticianId) {
        return dieticianMapper.toDieticianDto(dieticianRepository.findById(dieticianId).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", dieticianId.toString())));
    }

    public DieticianDto addDietician(final DieticianDto dieticianDto) {
        return dieticianMapper.toDieticianDto(dieticianRepository.save(dieticianMapper.toDietician(dieticianDto)));
    }

    public DieticianDto updateDietician(final DieticianDto dieticianDto) {
        Dietician dietician = dieticianRepository.findById(dieticianDto.getId()).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", String.valueOf(dieticianDto.getId())));
        Dietician updatedDietician = dieticianMapper.toDietician(dieticianDto);
        updatedDietician.setVisits(dietician.getVisits());
        updatedDietician.setQuestions(dietician.getQuestions());
        updatedDietician.setAnswers(dietician.getAnswers());
        updatedDietician.setTerms(dietician.getTerms());
        return dieticianMapper.toDieticianDto(dieticianRepository.save(updatedDietician));
    }

    public void deleteDietician(final Long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", dieticianId.toString()));
        dieticianRepository.deleteById(dieticianId);
    }
}
