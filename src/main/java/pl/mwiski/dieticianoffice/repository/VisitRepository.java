package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByUser(User user);
}
