package pl.mwiski.dieticianoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.TermDto;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Dietician;

import pl.mwiski.dieticianoffice.entity.Term;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.exception.AlreadyInDatabaseException;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.VisitMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.TermRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.VisitRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VisitService {

    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private TermService termService;

    public List<VisitDto> getVisits() {
        return visitMapper.toVisitDtoList(visitRepository.findAll());
    }

    public List<VisitDto> getUserVisits(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
        return visitMapper.toVisitDtoList(visitRepository.findAllByUser(user));
    }

    public VisitDto getVisit(final Long visitId) {
        return visitMapper.toVisitDto(visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException(Visit.class, "id", visitId.toString())));
    }

    public VisitDto addVisit(final VisitDto visitDto) {
        User user = userRepository.findById(visitDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(visitDto.getUser().getId())));
        Term term = termRepository.findById(visitDto.getTerm().getId()).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", String.valueOf(visitDto.getTerm().getId())));
        Dietician dietician = dieticianRepository.findById(visitDto.getDietician().getId()).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", String.valueOf(visitDto.getDietician().getId())));

        Visit visit = visitMapper.toVisit(visitDto);
        if (!term.isAvailable()) throw new IllegalArgumentException("Given term is not available!");
        term.getVisits().add(visit);
        termService.removeDieticianFromTerm(term.getId(), dietician.getId());
        dietician.getVisits().add(visit);
        user.getVisits().add(visit);
        return visitMapper.toVisitDto(visitRepository.save(visit));
    }

    public VisitDto updateVisit(final VisitDto visitDto) {
        Visit visit = visitRepository.findById(visitDto.getId()).orElseThrow(() -> new EntityNotFoundException(Visit.class, "id", String.valueOf(visitDto.getId())));
        User user = userRepository.findById(visitDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(visitDto.getUser().getId())));
        Term term = termRepository.findById(visitDto.getTerm().getId()).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", String.valueOf(visitDto.getTerm().getId())));
        Dietician dietician = dieticianRepository.findById(visitDto.getDietician().getId()).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", String.valueOf(visitDto.getDietician().getId())));

        Visit updatedVisit = visitMapper.toVisit(visitDto);
        if (!updatedVisit.getDietician().equals(visit.getDietician()) && updatedVisit.getTerm().equals(visit.getTerm())) {
            visit.getDietician().getVisits().remove(visit);
            dietician.getVisits().add(updatedVisit);
            termService.removeDieticianFromTerm(term.getId(), dietician.getId());
        }
        if (!updatedVisit.getTerm().equals(visit.getTerm()) && updatedVisit.getDietician().equals(visit.getDietician())) {
            visit.getTerm().getVisits().remove(visit);
            term.getVisits().add(updatedVisit);
            termService.removeDieticianFromTerm(term.getId(), dietician.getId());
        }
        if (!updatedVisit.getTerm().equals(visit.getTerm()) && !updatedVisit.getDietician().equals(visit.getDietician())) {
            visit.getDietician().getVisits().remove(visit);
            dietician.getVisits().add(updatedVisit);
            visit.getTerm().getVisits().remove(visit);
            term.getVisits().add(updatedVisit);
            termService.removeDieticianFromTerm(term.getId(), dietician.getId());
        }
        if (!updatedVisit.getUser().equals(visit.getUser())) user.getVisits().add(updatedVisit);

        if (!term.isAvailable()) term.setAvailable(true);
        return visitMapper.toVisitDto(visitRepository.save(updatedVisit));
    }

    public void deleteVisit(final Long visitId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException(Visit.class, "id", visitId.toString()));
        visit.getTerm().getVisits().remove(visit);
        visit.getDietician().getVisits().remove(visit);
        visit.getUser().getVisits().remove(visit);
        visitRepository.deleteById(visitId);
    }
}
