package app.dto;

import app.enums.CategoryType;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Setter
public class SeatDto {

    private Long id;

    @Size(min = 2, max = 5, message = "Seat number must be between 2 and 5 characters")
    @NotBlank(message = "Field seat number cannot be null")
    private String seatNumber;

    @NotNull(message = "Field isNearEmergencyExit cannot be null")
    private Boolean isNearEmergencyExit;

    @NotNull(message = "Field isLockedBack cannot be null")
    private Boolean isLockedBack;

    @NotNull(message = "Category cannot be null")
    private CategoryType category;

//    @NotNull(message = "Field aircraft cannot be null")
// эта проверка осталась в сущности, т.к. при сохранении многих Seat по aircraftID это поле сеттится отдельно.
    private Long aircraftId;
}