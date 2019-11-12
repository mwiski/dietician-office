package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Visit;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    @Autowired
    private TermMapper termMapper;
    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private UserMapper userMapper;

    private static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }

    public List<VisitDto> toVisitDtoList(final List<Visit> visits) {
        return getConvertedList(visits, this::toVisitDto);
    }

    public List<Visit> toVisits(final List<VisitDto> visitDtoList) {
        return getConvertedList(visitDtoList, this::toVisit);
    }

    public VisitDto toVisitDto(final Visit visit) {
        if (visit == null) return null;
        return new VisitDto(
                visit.getId(),
                termMapper.toTermDto(visit.getTerm()),
                userMapper.toSimpleUserDto(visit.getUser()),
                dieticianMapper.toSimpleDieticianDto(visit.getDietician()),
                visit.isHappened()
        );
    }

    public Visit toVisit(final VisitDto visitDto) {
        if (visitDto == null) return null;
        return new Visit(
                visitDto.getId(),
                termMapper.toTerm(visitDto.getTerm()),
                userMapper.toUser(visitDto.getUser()),
                dieticianMapper.toDietician(visitDto.getDietician()),
                visitDto.isHappened());
    }
}
