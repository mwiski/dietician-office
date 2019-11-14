package pl.mwiski.dieticianoffice.mapper.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String dateToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Date of visit cannot be empty!");
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDateTime stringToDate(String dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Date of visit cannot be empty!");
        }
        return LocalDateTime.parse(dateTime.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDate stringToDay(String date) {
        if (date == null) {
            throw new IllegalArgumentException("Date of visit cannot be empty!");
        }
        return LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
