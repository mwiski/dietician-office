package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.VisitMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.VisitRepository;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Slf4j
@Service
public class VisitService {

    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DieticianRepository dieticianRepository;

    public List<VisitDto> getAll() {
        log.info("Getting list of all visits");
        return visitMapper.toVisitDtoList(visitRepository.findAll());
    }

    public List<VisitDto> getAvailableVisits(final String date) {
        log.info("Getting list of available visits at date [{}]", date);
        return visitMapper.toVisitDtoList(visitRepository.findAllByAvailableAndDate(MapperUtils.stringToDay(date)));
    }

    public List<VisitDto> getUserVisits(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));
        log.info("Getting list of visits for user with ID [{}]", user.getId());
        return visitMapper.toVisitDtoList(visitRepository.findAllByUser(user));
    }

    public List<VisitDto> getDieticianVisits(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));
        log.info("Getting list of visits for dietician with ID [{}]", dietician.getId());
        return visitMapper.toVisitDtoList(visitRepository.findAllByDietician(dietician));
    }

    public VisitDto get(final long visitId) {
        log.info("Getting visit with ID [{}]", visitId);
        return visitMapper.toVisitDto(visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException(Visit.class, "ID", String.valueOf(visitId))));
    }

    public VisitDto add(final VisitDto visitDto) {
        Dietician dietician = dieticianRepository.findById(visitDto.getDietician().getId())
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(visitDto.getDietician().getId())));

        checkIfInGivenTimeIsVisitToDietician(visitDto, dietician);
        log.info("Adding new visit with ID [{}]", visitDto.getId());
        Visit visit = visitMapper.toVisit(visitDto);
        dietician.getVisits().add(visit);
        return visitMapper.toVisitDto(visitRepository.save(visit));
    }

    private void checkIfInGivenTimeIsVisitToDietician(VisitDto visitDto, Dietician dietician) {
        if (visitRepository.findAllByDateTime(MapperUtils.stringToDate(visitDto.getDateTime()))
                .stream().anyMatch(v -> v.getDietician().equals(dietician))) {
            throw new IllegalArgumentException("Given term is not available!");
        }
    }

    public VisitDto schedule(final long visitId, final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException(Visit.class, "ID", String.valueOf(visitId)));

        if (!visit.isAvailable()) throw new IllegalArgumentException("Given term is not available!");
        log.info("Scheduling visit with ID [{}] for user with ID [{}]", visitId, user.getId());
        visit.setUser(user);
        visit.setAvailable(false);
        user.getVisits().add(visit);
        return visitMapper.toVisitDto(visitRepository.save(visit));
    }

    public void cancel(final long visitId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException(Visit.class, "ID", String.valueOf(visitId)));
        log.info("Canceling visit with ID [{}]", visitId);
        visit.getDietician().getVisits().remove(visit);
        if (visit.getUser() != null) {
            visit.getUser().getVisits().remove(visit);
        }
        visitRepository.deleteById(visitId);
    }

    public List<VisitDto> getDieticianVisitsForTomorrow(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));
        log.info("Getting list of visits for dietician with ID [{}] for tomorrow", dietician.getId());
        return visitMapper.toVisitDtoList(visitRepository.findAllByDieticianAndDate(dietician, LocalDate.now().plusDays(1)));
    }
}
