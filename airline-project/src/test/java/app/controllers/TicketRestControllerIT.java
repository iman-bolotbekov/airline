package app.controllers;

import app.dto.BookingDto;
import app.dto.BookingUpdateDto;
import app.dto.TicketDto;
import app.enums.BookingStatus;
import app.exceptions.EntityNotFoundException;
import app.mappers.TicketMapper;
import app.repositories.TicketRepository;
import app.services.BookingService;
import app.services.tickets.TicketService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import java.time.LocalDateTime;

import static app.enums.Airport.OMS;
import static app.enums.Airport.VKO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.equalTo;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-ticket-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TicketRestControllerIT extends IntegrationTestBase {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TicketMapper ticketMapper;

    @DisplayName("shouldGetAllTickets(), return all tickets")
    @Test
    void shouldGetAllTickets() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("shouldGetAllTicketsByNullPage(), returns all tickets with the given size")
    @Test
    void shouldGetAllTicketsByNullPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets?size=2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("shouldGetAllTicketsByNullSize(), returns all tickets, if size not specified")
    @Test
    void shouldGetAllTicketsByNullSize() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets?page=0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("shouldGetBadRequestByPage(), return bad request-400, if page is a negative number")
    @Test
    void shouldGetBadRequestByPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets?page=-1&size=2"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("shouldGetBadRequestBySize(), return bad request-400, if size zero")
    @Test
    void shouldGetBadRequestBySize() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets?page=0&size=0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("shouldGetPageTickets(), return page of tickets")
    @Test
    void shouldGetPageTickets() throws Exception {
        var pageable = PageRequest.of(0, 4);
        mockMvc.perform(get("http://localhost:8080/api/tickets?page=0&size=4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ticketService
                        .getAllTickets(pageable.getPageNumber(), pageable.getPageSize()))));
    }

    @DisplayName("shouldCreateTicket(), successfully creates ticket and checks automatic id and ticketNumber generation")
    @Test
    void shouldCreateTicket() throws Exception {
        var newTicket = new TicketDto();
        newTicket.setFlightSeatId(1L);
        newTicket.setPassengerId(1L);
        newTicket.setBookingId(1L);
        newTicket.setFirstName("John1");
        newTicket.setLastName("Simons1");
        newTicket.setFrom(VKO);
        newTicket.setTo(OMS);
        newTicket.setFlightCode("VKOOMS");
        newTicket.setSeatNumber("1A");
        newTicket.setArrivalDateTime(LocalDateTime.of(2023, 04, 01, 11, 20, 00));
        newTicket.setDepartureDateTime(LocalDateTime.of(2023, 04, 01, 17, 50, 00));
        newTicket.setId(null);
        mockMvc.perform(post("http://localhost:8080/api/tickets")
                        .content(objectMapper.writeValueAsString(newTicket))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ticketNumber").isNotEmpty());
    }

    @DisplayName("shouldCreatePaidTicket(), successfully creates ticket by booking id")
    @Test
    void shouldCreatePaidTicket() throws Exception {
        var bookingDto = bookingService.getBookingDto(2L).get();
        mockMvc.perform(post("http://localhost:8080/api/tickets/{id}", bookingDto.getId())
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ticketNumber").isNotEmpty());
    }

    @DisplayName("shouldGetOneTicketByTicketNumber() , return 1 ticket by ticket number")
    @Test
        // fixme тест написан неправильно
    void shouldGetOneTicketByTicketNumber() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/tickets/{ticketNumber}", "SD-2222"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ticketNumber").isNotEmpty())
                .andExpect(jsonPath("$.passengerId").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.flightCode").exists())
                .andExpect(jsonPath("$.from").exists())
                .andExpect(jsonPath("$.to").exists())
                .andExpect(jsonPath("$.departureDateTime").exists())
                .andExpect(jsonPath("$.arrivalDateTime").exists())
                .andExpect(jsonPath("$.flightSeatId").exists())
                .andExpect(jsonPath("$.seatNumber").exists())
                .andExpect(jsonPath("$.bookingId").exists())
                .andExpect(jsonPath("$.boardingStartTime").exists())
                .andExpect(jsonPath("$.boardingEndTime").exists());
    }

    @DisplayName("shouldUpdateTicketNumberInTicket(), update TicketNumber in ticket")
    @Test
    void shouldUpdateTicketNumberInTicket() throws Exception {
        var ticketDTO = ticketMapper.toDto(ticketService.getTicketByTicketNumber("ZX-3333").get());

        ticketDTO.setTicketNumber("ZX-2222");
        long numberOfTicket = ticketRepository.count();

        mockMvc.perform(patch("http://localhost:8080/api/tickets/{id}", ticketDTO.getId())
                        .content(
                                objectMapper.writeValueAsString(ticketDTO)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(result -> assertThat(ticketRepository.count(), equalTo(numberOfTicket)));

    }

    @DisplayName("shouldUpdatePassengerAndFlightSeatInTicket(), update field Passenger and flightSeat, that assigned to a booking" +
            "only after updating these fields in the booking")
    @Test
    void shouldUpdatePassengerAndFlightSeatInTicket() throws Exception {
        var updatedTicket = ticketMapper.toDto(ticketService.getTicketByTicketNumber("ZX-3333").get());

        BookingUpdateDto bookingUpdateDto = new BookingUpdateDto();
        bookingUpdateDto.setId(3L);
        bookingUpdateDto.setFlightSeatId(4L);
        bookingUpdateDto.setPassengerId(4L);
        bookingUpdateDto.setBookingStatus(BookingStatus.PAID);
        bookingUpdateDto.setBookingDate(LocalDateTime.of(2023, 3, 27, 11, 20, 00));
        bookingService.updateBooking(3L, bookingUpdateDto);

        updatedTicket.setFlightSeatId(4L);
        updatedTicket.setPassengerId(4L);
        updatedTicket.setBookingId(3L);
        updatedTicket.setFirstName("Merf");
        updatedTicket.setLastName("Merfov");
        updatedTicket.setFrom(OMS);
        updatedTicket.setTo(VKO);
        updatedTicket.setFlightCode("OMSVKO");
        updatedTicket.setSeatNumber("1D");
        updatedTicket.setTicketNumber("ZZ-8888");
        updatedTicket.setArrivalDateTime(LocalDateTime.of(2023, 4, 3, 7, 55, 00));
        updatedTicket.setDepartureDateTime(LocalDateTime.of(2023, 4, 3, 7, 5, 00));
        updatedTicket.setBoardingEndTime((LocalDateTime.of(2023, 4, 3, 6, 45, 00)));
        updatedTicket.setBoardingStartTime((LocalDateTime.of(2023, 4, 3, 6, 25, 00)));

        ResultActions result = mockMvc.perform(patch("http://localhost:8080/api/tickets/{id}", updatedTicket.getId())
                        .content(
                                objectMapper.writeValueAsString(updatedTicket)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(updatedTicket.getId(), JsonPath.parse(jsonResponse).read("$.id", Long.class));
        Assertions.assertEquals(updatedTicket.getTicketNumber(), JsonPath.parse(jsonResponse).read("$.ticketNumber", String.class));
        Assertions.assertEquals(updatedTicket.getPassengerId(), JsonPath.parse(jsonResponse).read("$.passengerId", Long.class));
        Assertions.assertEquals(updatedTicket.getFlightSeatId(), JsonPath.parse(jsonResponse).read("$.flightSeatId", Long.class));

    }

    @DisplayName("shouldDeleteTicket(), delete ticket by ticket id")
    @Test
    void shouldDeleteTicket() throws Exception {
        Long id = 2L;
        mockMvc.perform(delete("http://localhost:8080/api/tickets/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertThrows(EntityNotFoundException.class, () -> ticketService.checkIfTicketExist(id));
    }

    @DisplayName("shouldThrowEntityNotFoundException(), throws EntityNotFoundException when ticket with given id does not exist")
    @Test
    void shouldThrowEntityNotFoundException() throws Exception {
        long nonExistingId = 1000500L;

        mockMvc.perform(delete("http://localhost:8080/api/tickets/{id}", nonExistingId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    @DisplayName("shouldNotContainNullValuesInPatchRequest(), checking for the impossibility of setting field values to NULL during a PATCH request")
    void shouldNotContainNullValuesInPatchRequest() throws Exception {
        var ticketDTO = ticketMapper.toDto(ticketService.getTicketByTicketNumber("ZX-3333").get());
        ticketDTO.setTicketNumber("ZX-2222");

        ticketDTO.setFlightSeatId(3L);
        ticketDTO.setBookingId(3L);
        ticketDTO.setPassengerId(3L);
        ticketDTO.setSeatNumber(null);

        mockMvc.perform(patch("http://localhost:8080/api/tickets/{id}", ticketDTO.getId())
                        .content(
                                objectMapper.writeValueAsString(ticketDTO)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("shouldNotContainNullValuesInPostRequest(), checking for the impossibility of setting field values to NULL during a POST request")
    @Test
    void shouldNotContainNullValuesInPostRequest() throws Exception {
        var newTicket = new TicketDto();
        newTicket.setFlightSeatId(1L);
        newTicket.setPassengerId(1L);
        newTicket.setBookingId(1L);
        newTicket.setFirstName("John1");
        newTicket.setLastName("Simons1");
        newTicket.setFrom(VKO);
        newTicket.setTo(OMS);
        newTicket.setFlightCode(null);
        newTicket.setSeatNumber("1A");
        newTicket.setArrivalDateTime(LocalDateTime.of(2023, 04, 01, 11, 20, 00));
        newTicket.setDepartureDateTime(LocalDateTime.of(2023, 04, 01, 17, 50, 00));
        newTicket.setId(null);
        mockMvc.perform(post("http://localhost:8080/api/tickets")
                        .content(objectMapper.writeValueAsString(newTicket))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("shouldNotCreatePaidTicketIfBookingNotExist(), return 404 - Not found , when try creates ticket by not exist booking id")
    @Test
    void shouldNotCreatePaidTicketIfBookingNotExist() throws Exception {
        var bookingDto = new BookingDto();
        bookingDto.setId(10L);
        mockMvc.perform(post("http://localhost:8080/api/tickets/{id}", bookingDto.getId())
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("shouldNotCreateUnPaidTicket(), return 400 - bad request, when try creates ticket by booking id," +
            "that has status not paid")
    @Test
    void shouldNotCreateUnPaidTicket() throws Exception {
        var bookingDto = bookingService.getBookingDto(5L).get();
        mockMvc.perform(post("http://localhost:8080/api/tickets/{id}", bookingDto.getId())
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("shouldReturnCreatedPaidTicketIfAlreadyExists(), return ticket from db (in db ticket have already exist with such booking id)")
    @Test
    void shouldReturnCreatedPaidTicketIfAlreadyExists() throws Exception {
        var bookingDto = bookingService.getBookingDto(6L).get();
        ResultActions result = mockMvc.perform(post("http://localhost:8080/api/tickets/{id}", bookingDto.getId())
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6L))
                .andExpect(jsonPath("$.ticketNumber").value("BB-1111"));

        MvcResult mvcResult = result.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        var ticket = ticketService.getTicketByTicketNumber("BB-1111").get();

        Assertions.assertEquals(ticket.getId(), JsonPath.parse(jsonResponse).read("$.id", Long.class));
        Assertions.assertEquals(ticket.getTicketNumber(), JsonPath.parse(jsonResponse).read("$.ticketNumber", String.class));
        Assertions.assertEquals(ticket.getPassenger().getId(), JsonPath.parse(jsonResponse).read("$.passengerId", Long.class));
        Assertions.assertEquals(ticket.getFlightSeat().getId(), JsonPath.parse(jsonResponse).read("$.flightSeatId", Long.class));
    }

    @DisplayName("shouldIgnoreIdInPatchRequest(), ignore id in PATCH request")
    @Test
    void shouldIgnoreIdInPatchRequest() throws Exception {
        var updatedTicket = ticketMapper.toDto(ticketService.getTicketByTicketNumber("ZX-3333").get());

        BookingUpdateDto bookingUpdateDto = new BookingUpdateDto();
        bookingUpdateDto.setId(3L);
        bookingUpdateDto.setFlightSeatId(4L);
        bookingUpdateDto.setPassengerId(4L);
        bookingUpdateDto.setBookingStatus(BookingStatus.PAID);
        bookingUpdateDto.setBookingDate(LocalDateTime.of(2023, 3, 27, 11, 20, 00));
        bookingService.updateBooking(3L, bookingUpdateDto);

        updatedTicket.setId(100L);
        updatedTicket.setFlightSeatId(4L);
        updatedTicket.setPassengerId(4L);
        updatedTicket.setBookingId(3L);
        updatedTicket.setFirstName("Merf");
        updatedTicket.setLastName("Merfov");
        updatedTicket.setFrom(OMS);
        updatedTicket.setTo(VKO);
        updatedTicket.setFlightCode("OMSVKO");
        updatedTicket.setSeatNumber("1D");
        updatedTicket.setTicketNumber("ZZ-8888");
        updatedTicket.setArrivalDateTime(LocalDateTime.of(2023, 4, 3, 7, 55, 0));
        updatedTicket.setDepartureDateTime(LocalDateTime.of(2023, 4, 3, 7, 5, 0));
        updatedTicket.setBoardingEndTime((LocalDateTime.of(2023, 4, 3, 6, 45, 0)));
        updatedTicket.setBoardingStartTime((LocalDateTime.of(2023, 4, 3, 6, 25, 0)));

        ResultActions result = mockMvc.perform(patch("http://localhost:8080/api/tickets/{id}", 3L)
                        .content(
                                objectMapper.writeValueAsString(updatedTicket)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.ticketNumber").isNotEmpty())
                .andExpect(jsonPath("$.passengerId").value(updatedTicket.getPassengerId()))
                .andExpect(jsonPath("$.flightSeatId").value(updatedTicket.getFlightSeatId()))
                .andExpect(jsonPath("$.bookingId").value(updatedTicket.getBookingId()));

        MvcResult mvcResult = result.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Long idFromResponse = JsonPath.parse(jsonResponse).read("$.id", Long.class);

        Assertions.assertNotEquals(updatedTicket.getId(), idFromResponse);
    }

    @DisplayName("shouldIgnoreIdInPostRequest(), ignore id in POST request")
    @Test
    void shouldIgnoreIdInPostRequest() throws Exception {
        var newTicket = new TicketDto();
        newTicket.setId(100L);
        newTicket.setFlightSeatId(1L);
        newTicket.setPassengerId(1L);
        newTicket.setBookingId(1L);
        newTicket.setFirstName("John1");
        newTicket.setLastName("Simons1");
        newTicket.setFrom(VKO);
        newTicket.setTo(OMS);
        newTicket.setFlightCode("VKOOMS");
        newTicket.setSeatNumber("1A");
        newTicket.setArrivalDateTime(LocalDateTime.of(2023, 04, 01, 11, 20, 00));
        newTicket.setDepartureDateTime(LocalDateTime.of(2023, 04, 01, 17, 50, 00));

        ResultActions result = mockMvc.perform(post("http://localhost:8080/api/tickets")
                        .content(objectMapper.writeValueAsString(newTicket))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        MvcResult mvcResult = result.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Long idFromResponse = JsonPath.parse(jsonResponse).read("$.id", Long.class);

        Assertions.assertNotEquals(newTicket.getId(), idFromResponse);

    }

    @Test
    @DisplayName("should don't return the PDF of ticket, because ticket is null")
    void shouldDoNotGetTicketPdfByTicketNumber() throws Exception {
        String ticketNumber = "SD-2522";
        mockMvc.perform(get("http://localhost:8080/api/tickets/pdf/{ticketNumber}", ticketNumber)
                        .contentType(MediaType.APPLICATION_PDF_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}