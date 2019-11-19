package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByUser(User user);

    List<Visit> findAllByDietician(Dietician dietician);

    default List<Visit> findAllByAvailableAndDate(LocalDate date) {
        return findAllByAvailable(true).stream()
                .filter(visit -> visit.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    default List<Visit> findAllByDieticianAndDate(Dietician dietician, LocalDate localDate) {
        return findAllByDietician(dietician).stream()
                .filter(visit -> visit.getDateTime().toLocalDate().equals(localDate))
                .collect(Collectors.toList());
    }

    List<Visit> findAllByAvailable(boolean available);

    List<Visit> findAllByDateTime(LocalDateTime dateTime);
}
