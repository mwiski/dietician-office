package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class OpinionDto {

    private final long id;
    @NotEmpty
    private final String opinion;
    private final String addedAt;
    @NotNull
    private final SimpleUserDto user;
}
