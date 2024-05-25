package app.dto;

import app.enums.BookingStatus;
import lombok.*;

import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingUpdateDto {

    private Long id;

    @Min(1)
    private Long passengerId;

    private LocalDateTime bookingDate;

    private BookingStatus bookingStatus;

    @Min(1)
    private Long flightSeatId;

    @Min(5)
    private Long flightId;
}