package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.dto.*;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitMapperTest {

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;
    private static final boolean COMPLETED = false;

    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DieticianMapper dieticianMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
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
    public void shouldMapToVisitDto() {
        //Given

        //When
        VisitDto expected = visitMapper.toVisitDto(visit);

        //Then
        assertThat(expected).isEqualTo(visitDto);
    }

    @Test
    public void shouldMapToVisit() {
        //Given

        //When
        Visit expected = visitMapper.toVisit(visitDto);

        //Then
        assertThat(expected).isEqualTo(visit);
    }
}
