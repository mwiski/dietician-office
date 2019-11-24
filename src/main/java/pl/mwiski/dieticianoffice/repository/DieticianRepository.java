package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Dietician;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface DieticianRepository extends JpaRepository<Dietician, Long> {

    Dietician findByLogin_Login(String login);
}
