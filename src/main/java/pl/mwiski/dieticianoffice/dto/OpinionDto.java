package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OpinionDto {

    private final long id;
    private final String opinion;
    private final String addedAt;
    private final SimpleUserDto user;
}
