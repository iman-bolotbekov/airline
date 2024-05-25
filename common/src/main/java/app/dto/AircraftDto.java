package app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AircraftDto {

    private Long id;

    @NotBlank(message = "field \"aircraftNumber\" should not be empty!")
    @Size(min = 4, max = 15, message = "Length of Aircraft Number should be between 4 and 15 characters")
    private String aircraftNumber;

    @NotBlank(message = "field \"model\" should not be empty!")
    private String model;

    @NotNull(message = "field \"modelYear\" should not be empty!")
    @Min(value = 2000, message = "modelYear should be later than 2000")
    private Integer modelYear;

    @NotNull(message = "field \"flightRange\" should not be empty!")
    @Min(value = 0, message = "flightRange should be a positive value")
    private Integer flightRange;
}