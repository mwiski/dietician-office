package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByUser(User user);

    List<Visit> findAllByDietician(Dietician dietician);

    default List<Visit> findAllByAvailableAndAndDate(boolean available, LocalDate date) {
        return findAllByAvailable(available).stream()
                .filter(visit -> visit.getDateAndTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    List<Visit> findAllByAvailable(boolean available);
}
