package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.OpinionMapper;
import pl.mwiski.dieticianoffice.repository.OpinionRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OpinionService {

    @Autowired
    private OpinionMapper opinionMapper;
    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<OpinionDto> getAll() {
        log.info("Getting list of all opinions");
        return opinionMapper.toOpinionDtoList(opinionRepository.findAll());
    }

    public List<OpinionDto> getUserOpinions(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));
        log.info("Getting list of opinions for user with ID [{}]", user.getId());
        return opinionMapper.toOpinionDtoList(opinionRepository.findAllByUser(user));
    }

    public OpinionDto add(final OpinionDto opinionDto) {
        log.info("Adding new opinion with ID [{}] by user with ID [{}]", opinionDto.getId(), opinionDto.getUser().getId());
        Opinion opinion = opinionMapper.toOpinion(opinionDto);
        opinion.getUser().getOpinions().add(opinion);
        return opinionMapper.toOpinionDto(opinionRepository.save(opinion));
    }

    public OpinionDto edit(final long opinionId, final String content) {
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(() -> new EntityNotFoundException(Opinion.class, "ID", String.valueOf(opinionId)));
        log.info("Editing user's opinion with ID [{}]", opinionId);
        opinion.setOpinion(content);
        return opinionMapper.toOpinionDto(opinionRepository.save(opinion));
    }

    public void delete(final long opinionId) {
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(() -> new EntityNotFoundException(Opinion.class, "ID", String.valueOf(opinionId)));

        log.info("Deleting opinion with ID [{}]", opinionId);
        opinion.getUser().getOpinions().remove(opinion);
        opinionRepository.deleteById(opinionId);
    }
}
