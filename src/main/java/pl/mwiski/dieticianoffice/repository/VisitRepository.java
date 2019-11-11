package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Visit;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
