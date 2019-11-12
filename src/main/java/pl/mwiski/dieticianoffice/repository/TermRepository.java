package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Term;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    default List<Term> findAllByAvailableAndDate(boolean available, LocalDate date) {
        return findAllByAvailable(available).stream()
                .filter(term -> term.getDateAndTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    List<Term> findAllByAvailable(boolean available);
}
