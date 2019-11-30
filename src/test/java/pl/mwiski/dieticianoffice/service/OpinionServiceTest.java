package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.OpinionMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.OpinionRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpinionServiceTest {

    private static final String OPINION = "opinion";
    private Opinion opinion;
    private OpinionDto opinionDto;
    private User user;
    private SimpleUserDto simpleUserDto;

    @InjectMocks
    private OpinionService opinionService;
    @Mock
    private OpinionMapper opinionMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OpinionRepository opinionRepository;
    @Mock
    private UserRepository userRepository;

    @Before
    public void before() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        user.setId(1L);
        simpleUserDto = new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getLogin().getLogin(),
                user.getLogin().getRole(),
                user.getPhoneNumber(),
                user.getMail());

        opinion = new Opinion(1L, OPINION, user);
        opinionDto = new OpinionDto(1L, OPINION, MapperUtils.dateToString(opinion.getAddedAt()), simpleUserDto);
        user.getOpinions().add(opinion);
    }

    @Test
    public void shouldGetAllOpinions() {
        //Given & When
        when(opinionRepository.findAll()).thenReturn(Arrays.asList(opinion));
        when(opinionMapper.toOpinionDtoList(Arrays.asList(opinion))).thenReturn(Arrays.asList(opinionDto));

        List<OpinionDto> expected = opinionService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(opinionDto);
    }

    @Test
    public void shouldGetUserOpinions() {
        //Given & When
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(opinionRepository.findAllByUser(user)).thenReturn(Arrays.asList(opinion));
        when(opinionMapper.toOpinionDtoList(Arrays.asList(opinion))).thenReturn(Arrays.asList(opinionDto));

        List<OpinionDto> expected = opinionService.getUserOpinions(user.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(opinionDto);
    }

    @Test
    public void shouldAddOpinion() {
        //Given & When
        when(opinionMapper.toOpinion(opinionDto)).thenReturn(opinion);
        when(opinionRepository.save(opinion)).thenReturn(opinion);
        when(opinionMapper.toOpinionDto(opinion)).thenReturn(opinionDto);

        OpinionDto expected = opinionService.add(opinionDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(opinionDto);
    }

    @Test
    public void shouldEditOpinion() {
        //Given & When
        when(opinionRepository.findById(opinionDto.getId())).thenReturn(Optional.ofNullable(opinion));
        when(opinionRepository.save(opinion)).thenReturn(opinion);
        when(opinionMapper.toOpinionDto(opinion)).thenReturn(opinionDto);

        OpinionDto expected = opinionService.edit(opinionDto.getId(), "edited");

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(opinionDto);
        assertThat(opinion.getOpinion()).isEqualTo("edited");
    }

    @Test
    public void shouldDeleteOpinion() {
        //Given & When
        when(opinionRepository.findById(opinionDto.getId())).thenReturn(Optional.ofNullable(opinion));
        opinionService.delete(opinionDto.getId());

        //Then
        assertThat(user.getOpinions().size()).isEqualTo(0);
    }
}
