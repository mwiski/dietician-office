package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.LoginRepository;
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
    @Autowired
    private LoginRepository loginRepository;

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
        log.info("Creating new dietician with ID [{}]", dieticianDto.getId());
        return dieticianMapper.toDieticianDto(dieticianRepository.save(dieticianMapper.toDietician(dieticianDto)));
    }

    public DieticianDto update(final DieticianDto dieticianDto) {
        Dietician dietician = dieticianRepository.findById(dieticianDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianDto.getId())));

        log.info("Updating dietician with ID [{}]", dieticianDto.getId());
        Dietician updatedDietician = dieticianMapper.toDietician(dieticianDto);
        checkLogin(dieticianDto, dietician, updatedDietician);

        updatedDietician.setVisits(dietician.getVisits());
        updatedDietician.setAnswers(dietician.getAnswers());
        return dieticianMapper.toDieticianDto(dieticianRepository.save(updatedDietician));
    }

    private void checkLogin(DieticianDto dieticianDto, Dietician dietician, Dietician updatedDietician) {
        if (!dietician.getLogin().getLogin().equals(dieticianDto.getLogin())) {
            loginRepository.delete(dietician.getLogin());
        } else {
            updatedDietician.setLogin(loginRepository.findByLogin(dieticianDto.getLogin()));
        }
    }

    public void delete(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));

        log.info("Deleting dietician with ID [{}]", dieticianId);

        dietician.getAnswers().forEach(answer -> answer.setDietician(null));
        dieticianRepository.deleteById(dieticianId);
    }
}
