package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import java.util.List;

@Component
public class OpinionMapper {

    @Autowired
    private UserMapper userMapper;

    public List<OpinionDto> toOpinionDtoList(final List<Opinion> opinions) {
        return MapperUtils.getConvertedList(opinions, this::toOpinionDto);
    }

    public OpinionDto toOpinionDto(final Opinion opinion) {
        if (opinion == null) return null;
        return new OpinionDto(
                opinion.getId(),
                opinion.getOpinion(),
                MapperUtils.dateToString(opinion.getAddedAt()),
                userMapper.toSimpleUserDto(opinion.getUser()));
    }

    public Opinion toOpinion(final OpinionDto opinionDto) {
        if (opinionDto == null) return null;
        return new Opinion(
                opinionDto.getId(),
                opinionDto.getOpinion(),
                userMapper.toUserFromSimpleUser(opinionDto.getUser()));
    }
}
