package app.dto;

import app.enums.Airport;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDto {

    private Long id;

    @NotNull(message = "Airport cannot be null")
    private Airport airportCode;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 9, message = "Timezone must be between 2 and 9 characters")
    private String timezone;

    @NotNull
    private String countryName;

    @NotNull
    private String cityName;

    @NotNull
    private String airportName;
}