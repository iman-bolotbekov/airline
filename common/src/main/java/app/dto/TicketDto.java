package app.dto;

import app.enums.Airport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TicketDto {

    private Long id;

    private String ticketNumber;

    @NotNull(message = "Passenger ID cannot be null")
    private Long passengerId;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Firstname size cannot be less than 2 and more than 128 characters")
    private String firstName;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Lastname size cannot be less than 2 and more than 128 characters")
    private String lastName;

    @NotBlank(message = "Flight code cannot be empty")
    @Size(min = 2, max = 15, message = "Length of flight code should be between 2 and 15 characters")
    private String flightCode;

    @NotNull(message = "Departure airport cannot be null")
    private Airport from;

    @NotNull(message = "Arrival airport cannot be null")
    private Airport to;

    @NotNull(message = "Departure date and time cannot be null")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time cannot be null")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Flight seat ID cannot be null")
    private Long flightSeatId;

    @NotBlank(message = "Field seat number cannot be null")
    @Size(min = 2, max = 5, message = "Seat number must be between 2 and 5 characters")
    private String seatNumber;

    @NotNull(message = "Booking ID cannot be null")
    private Long bookingId;

    private LocalDateTime boardingStartTime;

    private LocalDateTime boardingEndTime;
}