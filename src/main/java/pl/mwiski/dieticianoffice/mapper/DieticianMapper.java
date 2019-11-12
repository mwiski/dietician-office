package pl.mwiski.dieticianoffice.mapper;

import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DieticianMapper {

    private static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }

    public List<DieticianDto> toDieticianDtoList(final List<Dietician> dieticians) {
        return getConvertedList(dieticians, this::toDieticianDto);
    }

    public List<Dietician> toDieticianList(final List<DieticianDto> dieticians) {
        return getConvertedList(dieticians, this::toDietician);
    }

    public DieticianDto toDieticianDto(final Dietician dietician) {
        if (dietician == null) return null;
        return new DieticianDto(
                dietician.getId(),
                dietician.getLogin(),
                dietician.getPassword(),
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
                .login(dieticianDto.getLogin())
                .password(dieticianDto.getPassword())
                .name(dieticianDto.getName())
                .lastName(dieticianDto.getLastName())
                .phoneNumber(dieticianDto.getPhoneNumber())
                .mail(dieticianDto.getMail())
                .build();
    }

    public List<DieticianDto> toSimpleDieticianDtoList(final List<Dietician> dieticians) {
        if (dieticians == null) return null;
        return getConvertedList(dieticians, this::toSimpleDieticianDto);
    }

    public DieticianDto toSimpleDieticianDto(final Dietician dietician) {
        if (dietician == null) return null;
        return new DieticianDto(dietician.getName(), dietician.getLastName(), dietician.getPhoneNumber(), dietician.getMail());
    }
}
