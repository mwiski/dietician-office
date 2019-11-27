package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.entity.Dietician;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByDietician(Dietician dietician);
}
