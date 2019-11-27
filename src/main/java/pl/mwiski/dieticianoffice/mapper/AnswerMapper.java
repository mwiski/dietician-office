package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AnswerMapper {

    @Autowired
    private DieticianMapper dieticianMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<AnswerDto> toAnswerDtoList(final List<Answer> answers) {
        return MapperUtils.getConvertedList(answers, this::toAnswerDto);
    }

    public AnswerDto toAnswerDto(final Answer answer) {
        if (answer == null) return null;
        return new AnswerDto(
                answer.getId(),
                answer.getAnswer(),
                questionMapper.toQuestionDto(answer.getQuestion()),
                MapperUtils.dateToString(answer.getAddedAt()),
                dieticianMapper.toSimpleDieticianDto(answer.getDietician()));
    }

    public Answer toAnswer(final AnswerDto answerDto) {
        if (answerDto == null) return null;
        return new Answer(
                answerDto.getId(),
                answerDto.getAnswer(),
                questionMapper.toQuestion(answerDto.getQuestion()),
                LocalDateTime.now(),
                dieticianMapper.toDieticianFromSimpleDietician(answerDto.getDietician()));
    }
}
