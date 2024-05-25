package app.dto;

import app.enums.BookingStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BookingDto {

    private Long id;

    @NotNull
    @Min(1)
    private Long passengerId;

    private LocalDateTime bookingDate;

    private BookingStatus bookingStatus;

    @NotNull
    @Min(1)
    private Long flightSeatId;

    @NotNull
    @Min(5)
    private Long flightId;
}