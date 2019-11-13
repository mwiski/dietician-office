package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DieticianService {

    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private DieticianRepository dieticianRepository;

    public List<DieticianDto> getAll() {
        log.info("Getting list of all dieticians");
        return dieticianMapper.toDieticianDtoList(dieticianRepository.findAll());
    }

    public DieticianDto get(final long dieticianId) {
        log.info("Getting dietician by ID = [{}]", dieticianId);
        return dieticianMapper.toDieticianDto(dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId))));
    }

    public DieticianDto add(final DieticianDto dieticianDto) {
        log.info("Creating new dietician [{}]", dieticianDto.getLogin());
        return dieticianMapper.toDieticianDto(dieticianRepository.save(dieticianMapper.toDietician(dieticianDto)));
    }

    public DieticianDto update(final DieticianDto dieticianDto) {
        Dietician dietician = dieticianRepository.findById(dieticianDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianDto.getId())));

        log.info("Updating dietician information [{}]", dieticianDto.getLogin());
        Dietician updatedDietician = dieticianMapper.toDietician(dieticianDto);
        updatedDietician.setVisits(dietician.getVisits());
        updatedDietician.setQuestions(dietician.getQuestions());
        updatedDietician.setAnswers(dietician.getAnswers());
        return dieticianMapper.toDieticianDto(dieticianRepository.save(updatedDietician));
    }

    @PrePersist
    public void delete(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));

        log.info("Deleting dietician with ID [{}]", dieticianId);
        dietician.getAnswers().forEach(answer -> answer.setDietician(null));
        dietician.getQuestions().forEach(question -> question.getDieticians().remove(dietician));
        dieticianRepository.deleteById(dieticianId);
    }
}
