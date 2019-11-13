package pl.mwiski.dieticianoffice.mapper.utils;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MapperUtils {

    public static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }
}
