package app.dto;

import app.enums.Airport;
import app.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class FlightDto {

    private Long id;

    @NotBlank(message = "Code cannot be empty")
    @Size(min = 2, max = 15, message = "Length of Flight code should be between 2 and 15 characters")
    private String code;

    @JsonIgnore
    private List<FlightSeatDto> seats;

    private Airport airportFrom;

    private Airport airportTo;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private Long aircraftId;

    private FlightStatus flightStatus;
}