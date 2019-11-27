package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.entity.*;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.AnswerMapper;
import pl.mwiski.dieticianoffice.repository.AnswerRepository;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private DieticianRepository dieticianRepository;

    public List<AnswerDto> getAll() {
        log.info("Getting list of all answers");
        return answerMapper.toAnswerDtoList(answerRepository.findAll());
    }

    public List<AnswerDto> getDieticianAnswers(final long dieticianId) {
        Dietician dietician = dieticianRepository.findById(dieticianId)
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(dieticianId)));
        log.info("Getting list of answers for dietician with ID [{}]", dietician.getId());
        return answerMapper.toAnswerDtoList(answerRepository.findAllByDietician(dietician));
    }

    public AnswerDto add(final AnswerDto answerDto) {
        log.info("Adding new answer with id [{}]", answerDto.getId());
        Answer answer = answerMapper.toAnswer(answerDto);
        if (answer.getDietician() != null) {
            answer.getDietician().getAnswers().add(answer);
        }
        return answerMapper.toAnswerDto(answerRepository.save(answer));
    }

    public AnswerDto edit(final long answerId, final String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException(Answer.class, "ID", String.valueOf(answerId)));
        log.info("Editing answer with ID [{}]", answer);
        answer.setAnswer(content);
        return answerMapper.toAnswerDto(answerRepository.save(answer));
    }

    public void delete(final long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException(Answer.class, "ID", String.valueOf(answerId)));
        log.info("Deleting answer with ID [{}]", answerId);
        if (answer.getDietician() != null) {
            answer.getDietician().getAnswers().remove(answer);
        }
        answerRepository.deleteById(answerId);
    }
}
