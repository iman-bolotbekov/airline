package app.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class FlightSeatDto {

    private Long id;

    @PositiveOrZero(message = "Fare must be positive")
    private Integer fare;

    @NotNull(message = "isRegistered shouldn't be null")
    private Boolean isRegistered;

    @NotNull(message = "isSold shouldn't be null")
    private Boolean isSold;

    @NotNull(message = "isBooked shouldn't be null")
    private Boolean isBooked;

    @NotNull(message = "flightId shouldn't be null")
    private Long flightId;

    @NotNull(message = "seatNumber shouldn't be null")
    private SeatDto seat;

}