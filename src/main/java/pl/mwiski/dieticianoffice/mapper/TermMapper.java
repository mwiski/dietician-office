package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.dto.TermDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Term;
import pl.mwiski.dieticianoffice.entity.Visit;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TermMapper {

    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private VisitMapper visitMapper;

    private static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }

    public List<TermDto> toTermDtoList(final List<Term> terms) {
        return getConvertedList(terms, this::toTermDto);
    }

    public TermDto toTermDto(final Term term) {
        if (term == null) return null;
        return new TermDto(
                term.getId(),
                term.getDateAndTime(),
                toDieticiansDto(term.getDieticians()),
                term.isAvailable()
        );
    }

    public Term toTerm(final TermDto termDto) {
        if (termDto == null) return null;
        return new Term(
                termDto.getId(),
                termDto.getDateAndTime(),
                dieticianMapper.toDieticianList(termDto.getDieticians()),
                visitMapper.toVisits(termDto.getVisits()),
                termDto.isAvailable());
    }

    public List<DieticianDto> toDieticiansDto(List<Dietician> dieticians) {
        return getConvertedList(dieticians, dietician ->
                new DieticianDto(dietician.getName(), dietician.getLastName(), dietician.getPhoneNumber(), dietician.getMail()));
    }
}
