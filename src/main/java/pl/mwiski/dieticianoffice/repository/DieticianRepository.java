package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Dietician;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface DieticianRepository extends JpaRepository<Dietician, Long> {

    Optional<Dietician> findByLogin_Login(String login);
}
