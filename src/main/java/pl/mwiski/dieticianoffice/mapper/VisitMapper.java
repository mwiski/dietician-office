package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;

import java.util.List;

@Component
public class VisitMapper {

    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private UserMapper userMapper;

    public List<VisitDto> toVisitDtoList(final List<Visit> visits) {
        return MapperUtils.getConvertedList(visits, this::toVisitDto);
    }

    public List<Visit> toVisits(final List<VisitDto> visitDtoList) {
        return MapperUtils.getConvertedList(visitDtoList, this::toVisit);
    }

    public VisitDto toVisitDto(final Visit visit) {
        if (visit == null) return null;
        return new VisitDto(
                visit.getId(),
                visit.getDateAndTime(),
                userMapper.toSimpleUserDto(visit.getUser()),
                dieticianMapper.toSimpleDieticianDto(visit.getDietician()),
                visit.isAvailable(),
                visit.isCompleted()
        );
    }

    public Visit toVisit(final VisitDto visitDto) {
        if (visitDto == null) return null;
        return new Visit(
                visitDto.getId(),
                visitDto.getDateAndTime(),
                userMapper.toUserFromSimpleUser(visitDto.getUser()),
                dieticianMapper.toDieticianFromSimpleDietician(visitDto.getDietician()),
                visitDto.isAvailable(),
                visitDto.isCompleted());
    }
}
