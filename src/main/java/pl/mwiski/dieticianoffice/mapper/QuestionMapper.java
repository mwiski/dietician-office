package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.QuestionDto;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import java.util.List;

@Component
public class QuestionMapper {

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDto> toQuestionDtoList(final List<Question> questions) {
        return MapperUtils.getConvertedList(questions, this::toQuestionDto);
    }

    public QuestionDto toQuestionDto(final Question question) {
        if (question == null) return null;
        return new QuestionDto(
                question.getId(),
                question.getQuestion(),
                MapperUtils.dateToString(question.getAddedAt()),
                userMapper.toSimpleUserDto(question.getUser()));
    }

    public Question toQuestion(final QuestionDto questionDto) {
        if (questionDto == null) return null;
        return new Question(
                questionDto.getId(),
                questionDto.getQuestion(),
                userMapper.toUserFromSimpleUser(questionDto.getUser()));
    }
}
