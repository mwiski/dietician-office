package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.*;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VisitMapperTest {

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;

    @InjectMocks
    private VisitMapper visitMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private DieticianMapper dieticianMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Visit visit;
    private VisitDto visitDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        User user = userFactory.newInstance();
        SimpleUserDto userDto = userMapper.toSimpleUserDto(user);

        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        Dietician dietician = dieticianFactory.newInstance();
        SimpleDieticianDto dieticianDto = dieticianMapper.toSimpleDieticianDto(dietician);

        visit = new Visit(1, DATE_AND_TIME, user, dietician, AVAILABLE);
        visitDto = new VisitDto(1, MapperUtils.dateToString(DATE_AND_TIME), userDto, dieticianDto, AVAILABLE);
    }

    @Test
    public void shouldMapToVisitDtoList() {
        //Given
        List<Visit> visits = Arrays.asList(visit);

        // When
        List<VisitDto> visitDtos = visitMapper.toVisitDtoList(visits);

        //Then
        assertThat(visitDtos).containsExactly(visitDto);
    }

    @Test
    public void shouldMapToVisitDto() {
        //Given & When
        VisitDto expected = visitMapper.toVisitDto(visit);

        //Then
        assertThat(expected).isEqualTo(visitDto);
    }

    @Test
    public void shouldMapToVisit() {
        //Given & When
        Visit expected = visitMapper.toVisit(visitDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(visit);
    }
}
