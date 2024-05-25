package app.controllers.rest;

import app.controllers.api.rest.BookingRestApi;
import app.dto.BookingDto;
import app.dto.BookingUpdateDto;
import app.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class BookingRestController implements BookingRestApi {

    private final BookingService bookingService;

    @Override
    public ResponseEntity<Page<BookingDto>> getAllBookings(Integer page, Integer size) {
        log.info("getAllBookings:");
        if (page == null || size == null) {
            return createUnPagedResponse();
        }

        var examples = bookingService.getAllBookings(page, size);
        if (examples.isEmpty()) {
            return ResponseEntity.ok(Page.empty());
        } else {
            return ResponseEntity.ok(examples);
        }
    }

    private ResponseEntity<Page<BookingDto>> createUnPagedResponse() {
        var bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(bookings)));
        } else {
            log.info("getAllBookings: count: {}", bookings.size());
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(bookings)));
        }
    }

    @Override
    public ResponseEntity<BookingDto> getBooking(Long id) {
        log.info("getBooking: id = {}", id);
        var booking = bookingService.getBookingDto(id);
        return booking.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<BookingDto> createBooking(BookingDto bookingDto) {
        log.info("createBooking:");
        return new ResponseEntity<>(bookingService.saveBooking(bookingDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BookingDto> updateBooking(Long id, BookingUpdateDto bookingDto) {
        log.info("update: edit Booking with id = {}", id);
        return new ResponseEntity<>(bookingService.updateBooking(id, bookingDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteBooking(Long id) {
        log.info("deleteBooking: by id = {}", id);
        bookingService.deleteBookingById(id);
        return ResponseEntity.ok().build();
    }
}