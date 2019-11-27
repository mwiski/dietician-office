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

    public VisitDto toVisitDto(final Visit visit) {
        if (visit == null) return null;
        return new VisitDto(
                visit.getId(),
                MapperUtils.dateToString(visit.getDateTime()),
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
                MapperUtils.stringToDate(visitDto.getDateTime()),
                userMapper.toUserFromSimpleUser(visitDto.getUser()),
                dieticianMapper.toDieticianFromSimpleDietician(visitDto.getDietician()),
                visitDto.isAvailable(),
                visitDto.isCompleted());
    }
}
