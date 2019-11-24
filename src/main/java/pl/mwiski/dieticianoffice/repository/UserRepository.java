package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.User;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin_Login(String login);
}
