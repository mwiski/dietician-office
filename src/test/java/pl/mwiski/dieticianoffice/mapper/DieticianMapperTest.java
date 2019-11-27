package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.dto.SimpleDieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DieticianMapperTest {

    @InjectMocks
    private DieticianMapper dieticianMapper;
    @Mock
    private DieticianRepository dieticianRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Dietician dietician;
    private DieticianDto dieticianDto;
    private SimpleDieticianDto simpleDieticianDto;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        dieticianDto = new DieticianDto(
                dietician.getId(),
                dietician.getLogin().getLogin(),
                dietician.getLogin().getPassword(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getPhoneNumber(),
                dietician.getMail()
        );
        simpleDieticianDto =  new SimpleDieticianDto(
                dietician.getId(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getPhoneNumber(),
                dietician.getMail());
    }

    @Test
    public void shouldMapToDieticianDtoList() {
        //Given
        List<Dietician> dieticians = Arrays.asList(dietician);

        // When
        List<DieticianDto> dieticianDtos = dieticianMapper.toDieticianDtoList(dieticians);

        //Then
        assertThat(dieticianDtos).containsExactly(dieticianDto);
    }

    @Test
    public void shouldMapToDieticianDto() {
        //Given & When
        DieticianDto dieticianDto = dieticianMapper.toDieticianDto(dietician);

        //Then
        assertThat(dieticianDto).isEqualToComparingOnlyGivenFields(dietician);
    }

    @Test
    public void shouldMapToDietician() {
        //Given & When
        Dietician dietician = dieticianMapper.toDietician(dieticianDto);

        //Then
        assertThat(dietician).isEqualToComparingOnlyGivenFields(dieticianDto);
    }

    @Test
    public void shouldMapToDieticianFromSimpleDietician() {
        //Given & When
        when(dieticianRepository.findById(simpleDieticianDto.getId())).thenReturn(Optional.ofNullable(dietician));
        Dietician dietician = dieticianMapper.toDieticianFromSimpleDietician(simpleDieticianDto);

        //Then
        assertThat(dietician).isEqualToComparingOnlyGivenFields(simpleDieticianDto);
    }

    @Test
    public void shouldMapToSimpleDieticianDtoList() {
        //Given
        List<Dietician> dieticians = Arrays.asList(dietician);

        // When
        List<SimpleDieticianDto> simpleDieticianDtos = dieticianMapper.toSimpleDieticianDtoList(dieticians);

        //Then
        assertThat(simpleDieticianDtos).containsExactly(simpleDieticianDto);
    }

    @Test
    public void shouldMapToDieticianListFromSimpleDieticianDtoList() {
        //Given
        List<SimpleDieticianDto> simpleDieticianDtoList = Arrays.asList(simpleDieticianDto);
        when(dieticianRepository.findById(simpleDieticianDto.getId())).thenReturn(Optional.ofNullable(dietician));

        // When
        List<Dietician> dieticians = dieticianMapper.toDieticianListFromSimpleDieticianDtoList(simpleDieticianDtoList);

        //Then
        assertThat(dieticians).containsExactly(dietician);
    }

    @Test
    public void shouldMapToSimpleDieticianDto() {
        //Given & When
        SimpleDieticianDto simpleDieticianDto = dieticianMapper.toSimpleDieticianDto(dietician);

        //Then
        assertThat(simpleDieticianDto).isEqualToComparingOnlyGivenFields(dietician);
    }
}
