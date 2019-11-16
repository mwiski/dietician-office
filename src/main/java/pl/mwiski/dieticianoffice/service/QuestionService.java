package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.QuestionDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.QuestionMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.QuestionRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private UserRepository userRepository;

    public List<QuestionDto> getAll() {
        log.info("Getting list of all questions");
        return questionMapper.toQuestionDtoList(questionRepository.findAll());
    }

    public List<QuestionDto> getUserQuestions(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));
        log.info("Getting list of questions for user with ID [{}]", user.getId());
        return questionMapper.toQuestionDtoList(questionRepository.findAllByUser(user));
    }

    public List<QuestionDto> getDieticianQuestions(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));
        log.info("Getting list of questions for dietician with ID [{}]", dietician.getId());
        return questionMapper.toQuestionDtoList(questionRepository.findAllByDieticiansContains(dietician));
    }

    public QuestionDto add(final QuestionDto questionDto) {
        log.info("Adding new question by user with id [{}]", questionDto.getUser().getId());
        Question question = questionMapper.toQuestion(questionDto);
        question.getUser().getQuestions().add(question);
        question.getDieticians().forEach(dietician -> dietician.getQuestions().add(question));
        return questionMapper.toQuestionDto(questionRepository.save(question));
    }

    public QuestionDto edit(final long questionId, final String content) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(Question.class, "ID", String.valueOf(questionId)));
        log.info("Editing user's question with ID [{}]", questionId);
        question.setQuestion(content);
        return questionMapper.toQuestionDto(questionRepository.save(question));
    }

    public void delete(final long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(Question.class, "ID", String.valueOf(questionId)));

        log.info("Deleting question with ID [{}]", questionId);
        question.getUser().getQuestions().remove(question);
        question.getDieticians().forEach(dietician -> dietician.getQuestions().remove(question));
        questionRepository.deleteById(questionId);
    }
}
