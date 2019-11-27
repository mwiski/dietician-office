package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OpinionMapperTest {

    private static final String OPINION = "opinion";
    private Opinion opinion;
    private OpinionDto opinionDto;
    private User user;

    @InjectMocks
    private OpinionMapper opinionMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @Before
    public void before() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        opinion = new Opinion(1L, OPINION, user);
        opinionDto = new OpinionDto(1L, OPINION, MapperUtils.dateToString(opinion.getAddedAt()), userMapper.toSimpleUserDto(user));
    }

    @Test
    public void shouldMapToOpinionDtoList() {
        //Given
        List<Opinion> opinions = Arrays.asList(opinion);

        // When
        List<OpinionDto> opinionDtos = opinionMapper.toOpinionDtoList(opinions);

        //Then
        assertThat(opinionDtos).containsExactly(opinionDto);
    }

    @Test
    public void shouldMapToOpinionDto() {
        //Given & When
        OpinionDto opinionDto = opinionMapper.toOpinionDto(opinion);

        //Then
        assertThat(opinionDto).isEqualToComparingOnlyGivenFields(opinion);
        assertThat(opinionDto.getAddedAt()).isEqualTo(MapperUtils.dateToString(opinion.getAddedAt()));
    }

    @Test
    public void shouldMapToOpinion() {
        //Given & When
        Opinion opinion = opinionMapper.toOpinion(opinionDto);

        //Then
        assertThat(opinion).isEqualToComparingOnlyGivenFields(opinionDto);
        assertThat(opinion.getAddedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).isEqualTo(opinionDto.getAddedAt());
    }
}
