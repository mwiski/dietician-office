package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.dto.SimpleDieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import java.util.List;

@Component
public class DieticianMapper {

    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<DieticianDto> toDieticianDtoList(final List<Dietician> dieticians) {
        return MapperUtils.getConvertedList(dieticians, this::toDieticianDto);
    }

    public DieticianDto toDieticianDto(final Dietician dietician) {
        if (dietician == null) return null;
        return new DieticianDto(
                dietician.getId(),
                dietician.getLogin().getLogin(),
                dietician.getLogin().getPassword(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getPhoneNumber(),
                dietician.getMail()
        );
    }

    public Dietician toDietician(final DieticianDto dieticianDto) {
        if (dieticianDto == null) return null;
        return Dietician.builder()
                .id(dieticianDto.getId())
                .login(new Login(dieticianDto.getLogin(), passwordEncoder.encode(dieticianDto.getPassword()), RoleType.DIETICIAN))
                .name(dieticianDto.getName())
                .lastName(dieticianDto.getLastName())
                .phoneNumber(dieticianDto.getPhoneNumber())
                .mail(dieticianDto.getMail())
                .build();
    }

    public Dietician toDieticianFromSimpleDietician(final SimpleDieticianDto simpleDieticianDto) {
        if (simpleDieticianDto == null) return null;
        return dieticianRepository.findById(simpleDieticianDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(Dietician.class, "ID", String.valueOf(simpleDieticianDto.getId())));
    }

    public List<SimpleDieticianDto> toSimpleDieticianDtoList(final List<Dietician> dieticians) {
        if (dieticians == null) return null;
        return MapperUtils.getConvertedList(dieticians, this::toSimpleDieticianDto);
    }

    public List<Dietician> toDieticianListFromSimpleDieticianDtoList(final List<SimpleDieticianDto> simpleDieticianDtos) {
        if (simpleDieticianDtos == null) return null;
        return MapperUtils.getConvertedList(simpleDieticianDtos, this::toDieticianFromSimpleDietician);
    }

    public SimpleDieticianDto toSimpleDieticianDto(final Dietician dietician) {
        if (dietician == null) return null;
        return new SimpleDieticianDto(
                dietician.getId(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getPhoneNumber(),
                dietician.getMail());
    }
}
