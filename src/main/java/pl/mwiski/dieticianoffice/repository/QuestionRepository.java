package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByUser(User user);

}
