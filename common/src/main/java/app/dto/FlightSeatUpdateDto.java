package app.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class FlightSeatUpdateDto {

    private Long id;

    @PositiveOrZero(message = "Fare must be positive")
    private Integer fare;

    private Boolean isRegistered;

    private Boolean isSold;

    private Boolean isBooked;

    private Long flightId;

    private SeatDto seat;
}