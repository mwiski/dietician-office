package pl.mwiski.dieticianoffice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.dto.SimpleDieticianDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.mapper.UserMapper;
import pl.mwiski.dieticianoffice.mapper.VisitMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.VisitRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;
    private static final boolean COMPLETED = false;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Visit visit;
    private VisitDto visitDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        User user = userFactory.newInstance();
        userRepository.save(user);
        SimpleUserDto userDto = userMapper.toSimpleUserDto(user);

        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        Dietician dietician = dieticianFactory.newInstance();
        dieticianRepository.save(dietician);
        SimpleDieticianDto dieticianDto = dieticianMapper.toSimpleDieticianDto(dietician);

        visit = new Visit(1, DATE_AND_TIME, user, dietician, AVAILABLE, COMPLETED);
        visitDto = new VisitDto(1, MapperUtils.dateToString(DATE_AND_TIME), userDto, dieticianDto, AVAILABLE, COMPLETED);
    }

    @Test
    public void shouldGetAllVisits() {
        //Given & When
        visitRepository.save(visit);
        List<VisitDto> expected = visitService.getAll();

        //Then
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto, "dateTime", "user", "dietician", "available", "completed");
    }

    @Test
    public void shouldAddVisit() {
        //Given & When
        VisitDto expected = visitService.add(visitDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(visitDto, "dateTime", "user", "dietician", "available", "completed");
    }

    @Test
    public void shouldGetAvailableVisit() {
        //Given & When
        visitRepository.save(visit);
        System.out.println( visit.getDateTime().toLocalDate());

        List<VisitDto> expected = visitService.getAvailableVisits("2019-11-12");

        //Then
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto, "dateTime", "user", "dietician", "available", "completed");
    }
}
