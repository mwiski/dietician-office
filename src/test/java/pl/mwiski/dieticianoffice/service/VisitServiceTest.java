package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.SimpleDieticianDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.UserMapper;
import pl.mwiski.dieticianoffice.mapper.VisitMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.VisitRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VisitServiceTest {

    @InjectMocks
    private VisitService visitService;

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DieticianRepository dieticianRepository;
    @Mock
    private VisitRepository visitRepository;
    @Mock
    private VisitMapper visitMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Visit visit;
    private VisitDto visitDto;
    private User user;
    private Dietician dietician;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        SimpleUserDto userDto = userMapper.toSimpleUserDto(user);

        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        SimpleDieticianDto simpleDieticianDto =  new SimpleDieticianDto(
                dietician.getId(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getLogin().getLogin(),
                dietician.getLogin().getRole(),
                dietician.getPhoneNumber(),
                dietician.getMail());

        visit = new Visit(1, DATE_AND_TIME, user, dietician, AVAILABLE);
        dietician.getVisits().add(visit);
        user.getVisits().add(visit);
        visitDto = new VisitDto(1, MapperUtils.dateToString(DATE_AND_TIME), userDto, simpleDieticianDto, AVAILABLE);
    }

    @Test
    public void shouldGetAllVisits() {
        //Given & When
        when(visitRepository.findAll()).thenReturn(Arrays.asList(visit));
        when(visitMapper.toVisitDtoList(Arrays.asList(visit))).thenReturn(Arrays.asList(visitDto));

        List<VisitDto> expected = visitService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldGetAvailableVisits() {
        //Given & When
        when(visitRepository.findAllByAvailableAndDate(MapperUtils.stringToDay(DATE_AND_TIME.toLocalDate().toString()))).thenReturn(Arrays.asList(visit));
        when(visitMapper.toVisitDtoList(Arrays.asList(visit))).thenReturn(Arrays.asList(visitDto));

        List<VisitDto> expected = visitService.getAvailableVisits(DATE_AND_TIME.toLocalDate().toString());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldGetUserVisits() {
        //Given & When
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(visitRepository.findAllByUser(user)).thenReturn(Arrays.asList(visit));
        when(visitMapper.toVisitDtoList(Arrays.asList(visit))).thenReturn(Arrays.asList(visitDto));

        List<VisitDto> expected = visitService.getUserVisits(user.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldGetDieticianVisits() {
        //Given & When
        when(dieticianRepository.findById(dietician.getId())).thenReturn(Optional.ofNullable(dietician));
        when(visitRepository.findAllByDietician(dietician)).thenReturn(Arrays.asList(visit));
        when(visitMapper.toVisitDtoList(Arrays.asList(visit))).thenReturn(Arrays.asList(visitDto));

        List<VisitDto> expected = visitService.getDieticianVisits(user.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldGetVisit() {
        //Given & When
        when(visitRepository.findById(visitDto.getId())).thenReturn(Optional.ofNullable(visit));
        when(visitMapper.toVisitDto(visit)).thenReturn(visitDto);

        VisitDto expected = visitService.get(visitDto.getId());

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldAddVisit() {
        //Given & When
        when(dieticianRepository.findById(visitDto.getDietician().getId())).thenReturn(Optional.ofNullable(dietician));
        when(visitRepository.findAllByDateTime(MapperUtils.stringToDate(visitDto.getDateTime()))).thenReturn(new ArrayList<>());
        when(visitMapper.toVisit(visitDto)).thenReturn(visit);
        when(visitRepository.save(visit)).thenReturn(visit);
        when(visitMapper.toVisitDto(visit)).thenReturn(visitDto);

        VisitDto expected = visitService.add(visitDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(visitDto);
    }

    @Test
    public void shouldScheduleVisit() {
        //Given & When
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(visitRepository.findById(visitDto.getId())).thenReturn(Optional.ofNullable(visit));
        when(visitRepository.save(visit)).thenReturn(visit);
        when(visitMapper.toVisitDto(visit)).thenReturn(visitDto);

        VisitDto expected = visitService.schedule(visitDto.getId(), user.getId());

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(visitDto);
        assertThat(visit.isAvailable()).isFalse();
    }

    @Test
    public void shouldCancelVisit() {
        //Given & When
        when(visitRepository.findById(visitDto.getId())).thenReturn(Optional.ofNullable(visit));
        visitService.cancel(visitDto.getId());

        //Then
        assertThat(dietician.getVisits()).isEmpty();
        assertThat(user.getVisits()).isEmpty();
    }

    @Test
    public void shouldGetDieticianVisitsForTomorrow() {
        //Given & When
        when(dieticianRepository.findById(visitDto.getDietician().getId())).thenReturn(Optional.ofNullable(dietician));
        when(visitRepository.findAllByDieticianAndDate(dietician, LocalDate.now().plusDays(1))).thenReturn(Arrays.asList(visit));
        when(visitMapper.toVisitDtoList(Arrays.asList(visit))).thenReturn(Arrays.asList(visitDto));

        List<VisitDto> expected = visitService.getDieticianVisitsForTomorrow(dietician.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(visitDto);
    }
}
