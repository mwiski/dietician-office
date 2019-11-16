package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Login;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Login findByLogin(String login);
}
