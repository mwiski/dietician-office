package pl.mwiski.dieticianoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.TermDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Term;
import pl.mwiski.dieticianoffice.exception.AlreadyInDatabaseException;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.TermMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.TermRepository;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TermService {

    @Autowired
    private TermMapper termMapper;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private DieticianRepository dieticianRepository;

    public List<TermDto> getTerms() {
        return termMapper.toTermDtoList(termRepository.findAll());
    }

    public List<TermDto> getAvailableTermsInGivenDay(final String date) {
        LocalDate localDate = LocalDate.parse(date);
        return termMapper.toTermDtoList(termRepository.findAllByAvailableAndDate(true, localDate));
    }

    public TermDto getTerm(final Long termId) {
        return termMapper.toTermDto(termRepository.findById(termId).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", termId.toString())));
    }

    public TermDto addTerm(final TermDto termDto) {
        return termMapper.toTermDto(termRepository.save(termMapper.toTerm(termDto)));
    }

    public TermDto addDieticianToTerm(final Long termId, final Long dieticianId) {
        Term term = termRepository.findById(termId).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", termId.toString()));
        Dietician dietician = dieticianRepository.findById(dieticianId).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", dieticianId.toString()));
        if (term.getDieticians().stream().anyMatch(d -> d.equals(dietician))) throw new AlreadyInDatabaseException("This term already contains given dietician!");
        term.getDieticians().add(dietician);
        dietician.getTerms().add(term);
        dieticianRepository.save(dietician);
        if (!term.isAvailable()) term.setAvailable(true);
        return termMapper.toTermDto(termRepository.save(term));
    }

    public TermDto removeDieticianFromTerm(final Long termId, final Long dieticianId) {
        Term term = termRepository.findById(termId).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", termId.toString()));
        Dietician dietician = dieticianRepository.findById(dieticianId).orElseThrow(() -> new EntityNotFoundException(Dietician.class, "id", dieticianId.toString()));
        if (term.getDieticians().stream().noneMatch(d -> d.equals(dietician))) throw new IllegalArgumentException("This term doesn't contain given dietician!");
        term.getDieticians().remove(dietician);
        dietician.getTerms().remove(term);
        dieticianRepository.save(dietician);
        if (term.getDieticians().isEmpty()) term.setAvailable(false);
        return termMapper.toTermDto(termRepository.save(term));
    }

    public void deleteTerm(final Long termId) {
        Term term = termRepository.findById(termId).orElseThrow(() -> new EntityNotFoundException(Term.class, "id", termId.toString()));
        term.getDieticians().forEach(dietician -> dietician.getTerms().remove(term));
        termRepository.deleteById(termId);
    }
}
