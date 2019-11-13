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
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.VisitRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
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

    public List<VisitDto> getUserVisits(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));
        log.info("Getting list of visits for user [{}]", user.getLogin().getLogin());
        return visitMapper.toVisitDtoList(visitRepository.findAllByUser(user));
    }

    public List<VisitDto> getDieticianVisits(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));
        log.info("Getting list of visits for dietician [{}]", dietician.getLogin().getLogin());
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

        log.info("Adding new visit");
        Visit visit = visitMapper.toVisit(visitDto);
        dietician.getVisits().add(visit);
        return visitMapper.toVisitDto(visitRepository.save(visit));
    }

    public VisitDto schedule(final long visitId, final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException(Visit.class, "ID", String.valueOf(visitId)));

        log.info("Scheduling visit for user [{}]", user.getLogin().getLogin());
        if (!visit.isAvailable()) throw new IllegalArgumentException("Given term is not available!");
        visit.setUser(user);
        visit.setAvailable(false);
        user.getVisits().add(visit);
        return visitMapper.toVisitDto(visitRepository.save(visit));
    }

    public void cancel(final long visitId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException(Visit.class, "ID", String.valueOf(visitId)));
        log.info("Canceling visit with id [{}]", visitId);
        visit.getDietician().getVisits().remove(visit);
        visit.getUser().getVisits().remove(visit);
        visitRepository.deleteById(visitId);
    }
}
