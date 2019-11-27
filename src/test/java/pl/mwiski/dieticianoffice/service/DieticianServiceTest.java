package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.LoginRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DieticianServiceTest {
    
    @InjectMocks
    private DieticianService dieticianService;

    @Mock
    private DieticianMapper dieticianMapper;
    @Mock
    private DieticianRepository dieticianRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    
    private Dietician dietician;
    private DieticianDto dieticianDto;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        dieticianDto = new DieticianDto(
                dietician.getId(),
                "newLogin",
                dietician.getLogin().getPassword(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getPhoneNumber(),
                dietician.getMail()
        );
    }

    @Test
    public void shouldGetAllDieticians() {
        //Given & When
        when(dieticianRepository.findAll()).thenReturn(Arrays.asList(dietician));
        when(dieticianMapper.toDieticianDtoList(Arrays.asList(dietician))).thenReturn(Arrays.asList(dieticianDto));

        List<DieticianDto> expected = dieticianService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(dieticianDto);
    }

    @Test
    public void shouldGetDietician() {
        //Given & When
        when(dieticianRepository.findById(dieticianDto.getId())).thenReturn(Optional.ofNullable(dietician));
        when(dieticianMapper.toDieticianDto(dietician)).thenReturn(dieticianDto);

        DieticianDto expected = dieticianService.get(dieticianDto.getId());

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(dieticianDto);
    }

    @Test
    public void shouldAddDietician() {
        //Given & When
        when(dieticianMapper.toDietician(dieticianDto)).thenReturn(dietician);
        when(dieticianRepository.save(dietician)).thenReturn(dietician);
        when(dieticianMapper.toDieticianDto(dietician)).thenReturn(dieticianDto);

        DieticianDto expected = dieticianService.add(dieticianDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(dieticianDto);
    }
}
