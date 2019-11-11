package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Answer;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
